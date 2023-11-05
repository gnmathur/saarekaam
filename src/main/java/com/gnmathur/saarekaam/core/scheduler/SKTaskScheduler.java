/*
MIT License

Copyright (c) 2023 Gaurav Mathur

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.gnmathur.saarekaam.core.scheduler;

import com.gnmathur.saarekaam.core.SKManager;
import com.gnmathur.saarekaam.core.mgmt.SKMgmt;
import com.gnmathur.saarekaam.core.task.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.concurrent.*;

import static com.gnmathur.saarekaam.core.scheduler.SKSchedulerPolicy.*;

/**
 * <p>
 *     This abstract class is the base class for all task schedulers. It provides the basic functionality for scheduling
 *     tasks. The actual scheduling is done by the concrete implementations of this class. Different Task schedulers can
 *     be used for different types of tasks. Task schedulers are registered with the dispatcher
 *     <code>{@link SKManager}</code> at startup.
 *
 *     The scheduler uses <code>{@link ScheduledExecutorService}</code> to schedule tasks. The actual task
 *     implementation creates an underlying thread. That thread lifecycles is managed by a second executor
 *     <code>{@link Executors}</code>, that uses a cached thread pool. This approach of wrapping the task in two layers
 *     makes it possible to cancel the (inner) task if it is taking too long to complete.
 *
 *      The scheduler uses a <code>{@link LinkedHashMap}</code> to keep track of the running jobs. The scheduler
 *      will not schedule a job if it is already running. This prevents the scheduler from scheduling the same job
 *      multiple times.
 *
 *      The implementation chose the Cached Thread Pool over the Fixed Thread Pool because the cached thread pool
 *      is suitable for short-lived asynchronous tasks, which is what would typically need for tasks. Since the
 *      Cached Thread Pool expires threads after 60s of inactivity, the implementation will result in threads being
 *      created and destroyed, though its expected to be minimal.
 *
 * </p>
 *
 * @see SKManager
 * @see Executors#newScheduledThreadPool(int, ThreadFactory)
 * @see Executors#newCachedThreadPool(ThreadFactory)
 *
 * @author Gaurav Mathur
 */
public abstract class SKTaskScheduler implements SKTaskSchedulerMBean {
    protected static final Logger logger = LogManager.getLogger(SKTaskScheduler.class);
    // Running jobs in the scheduler type
    protected LinkedHashMap<String, ScheduledFuture<?>> runningJob = new LinkedHashMap<>();
    private final long startTime;
    protected final ScheduledExecutorService ste;
    protected final ExecutorService cte;

    /**
     * Create and initialize a scheduler.
     */
    protected SKTaskScheduler(ScheduledExecutorService ste, ExecutorService cte) {
        logger.info("Initializing task scheduler " + this.getClass().getSimpleName() + "...");
        startTime = System.currentTimeMillis();
        SKMgmt.registerMBean(this, Optional.of("scheduler"), Optional.of(this.getClass().getSimpleName()));
        this.ste = ste;
        this.cte = cte;
    }

    /**
     * Check if the job is already running. If not, return a runnable for the job.
     *
     * @param wrappedTask The job wrapper
     * @return A runnable for the job
     */
    protected Runnable createTaskRunnable(SKTaskWrapper wrappedTask) {
        final SKThreadFactory oneShotThreadFactory = new SKThreadFactory("task-thread");

        /* Create a runnable for the scheduled task. This runnable will be submitted to the
         * scheduler. The scheduler will create a thread for this runnable and execute it.
         * The runnable will create a single thread executor and submit the job to it. This
         * will ensure that the job is executed in a separate thread. The runnable will wait
         * for the job to complete or timeout (30s). If the job does not complete in 30s, it
         * will be cancelled. The advantage of this approach is that the scheduler now has
         * the ability to cancel the job if it is taking too long to complete. This will
         * prevent the scheduler from getting blocked.
         *
         */
        Runnable taskRunnable = () -> {
            // A runnable for the wrapper task
            final SKTaskRunnable wtr = new SKTaskRunnable(wrappedTask);
            // Submit the job
            final Future<?> f = cte.submit(wtr);

            wrappedTask.setState(SKTaskRunState.RUNNING);

            try {
                f.get(JOB_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);
                wrappedTask.setState(SKTaskRunState.COMPLETED);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                f.cancel(true);
                wrappedTask.setState(SKTaskRunState.CANCELLED);
                logger.error(String.format("Task %s cancelled because it timed out", wrappedTask.getIdent()));
            }
        };
        return taskRunnable;
    }


    /**
     * Add a task to the scheduler
     *
     * @param SKTaskWrapper The task wrapper
     */
    public void scheduleIt(SKTaskWrapper SKTaskWrapper) {
        var taskIdent = SKTaskWrapper.getIdent();

        logger.debug(String.format("Scheduling task %s", SKTaskWrapper.getIdent()));

        // check if the job is already running
        if (runningJob.containsKey(SKTaskWrapper.getClass().getName())) {
            logger.info(String.format("Task %s is already running", SKTaskWrapper.getIdent()));
            return;
        }

        var f = schedule(SKTaskWrapper);
        runningJob.put(taskIdent, f);
    }

    @Override
    public long getActiveCount() { return runningJob.size(); }

    @Override
    public long getStartTime() { return startTime; }

    public SKTaskSchedulerMBean getSchedulerMBean() { return this; }

    /**
     * There are different types of schedulers. Each scheduler will have its own implementation of this method.
     *
     * @param taskWrapper
     */
    public abstract ScheduledFuture schedule(SKTaskWrapper taskWrapper);

    /**
     * Shutdown the scheduler. This will wait for all tasks to complete or timeout (30s).
     */
    public abstract void shutdown();
}
