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
package com.gnmathur.saarekaam.core.schedulers;

import com.gnmathur.saarekaam.core.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.concurrent.*;

import static com.gnmathur.saarekaam.core.SKSchedulerPolicy.*;

/**
 * <p>
 *     This abstract class is the base class for all task schedulers. It provides the
 *     basic functionality for scheduling tasks. The actual scheduling is done by the
 *     concrete implementations of this class. Different Task schedulers can be used
 *     for different types of tasks. Task schedulers are registered with the dispatcher
 *     <code>{@link SKTaskDispatcher}</code> at startup.
 *
 *     The scheduler uses a <code>{@link ScheduledExecutorService}</code> to schedule
 *     tasks. The actual task implementation is wrapped in an implementation that creates
 *     another underlying thread. That thread lifecycles is managed by the second executor
 *     that this module defines. This approach of wrapping the task in two layers makes it
 *     possible to cancel the task if it is taking too long to complete.
 * @see SKTaskDispatcher
 * @author Gaurav Mathur
 */
public abstract class SKTaskScheduler {
    protected static final Logger logger = LogManager.getLogger(SKTaskScheduler.class);
    protected LinkedHashMap<String, ScheduledFuture<?>> runningJob = new LinkedHashMap<>();
    protected final ScheduledExecutorService ste;
    protected final ExecutorService cte;

    /**
     * Create and initialize a scheduler.
     * @return The scheduler
     */
    public SKTaskScheduler() {
        logger.info("Initializing task schedulers");

        // Chose a scheduled thread pool for the scheduler. This is the core executor for managing the feature
        // of periodic tasks.
        ste = Executors.newScheduledThreadPool(MAX_CONCURRENT_JOBS, new SKThreadFactory("sch-thread"));
        // Chose a cached thread pool for the cancelable task executor. This executor will be used to create another
        // thread when the task gets scheduled to run. The advantage of this thread-in-a-thread approach is that
        // the scheduler can now cancel the task if it is taking too long to complete. This will prevent the scheduler
        // from getting blocked
        cte = Executors.newCachedThreadPool(new SKThreadFactory("cancelable-task"));
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

            try {
                f.get(JOB_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);
                wrappedTask.setState(SKTaskRunState.COMPLETED);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                f.cancel(true);
                wrappedTask.setState(SKTaskRunState.FAILED);
                logger.error(String.format("Task %s failed (err: %s)", wrappedTask.getIdent(), e.getMessage()));
            }
        };
        return taskRunnable;
    }

    /**
     * Shutdown the scheduler. This will wait for all tasks to complete or timeout (30s).
     * If the tasks do not complete in 30s, they will be cancelled.
     * If the scheduler is already shutdown, this method will do nothing.
     */
    public void shutdown() {
        logger.info("Shutting down task scheduler. Will wait for all tasks to complete or timeout (30s)");

        ste.shutdown();
        cte.shutdown();

        try {
            if (!ste.awaitTermination(SHUTDOWN_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)) {
                logger.info("Forcing shutdown...");
                ste.shutdownNow();
            }
        } catch (InterruptedException e) {
            // Try to shut down again
            ste.shutdownNow();
            // Preserve interrupt status so that calling code can also see it
            Thread.currentThread().interrupt();
        }
    }

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

    /**
     * Schedule a job.
     *
     * @param taskWrapper
     */
    public abstract ScheduledFuture schedule(SKTaskWrapper taskWrapper);
}