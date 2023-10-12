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
package com.gnmathur.saarekaam.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.concurrent.*;

import static com.gnmathur.saarekaam.core.SKSchedulerPolicy.*;

public class SKTaskScheduler {
    private static final Logger logger = LogManager.getLogger(SKTaskScheduler.class);

    private final ScheduledExecutorService es;
    private LinkedHashMap<String, ScheduledFuture<?>> runningJob = new LinkedHashMap<>();

    public static SKTaskScheduler getInstance() {
        return new SKTaskScheduler();
    }

    private SKTaskScheduler() {
        es = Executors.newScheduledThreadPool(10, new SKThreadFactory("sch-thread"));
    }

    public void schedule(SKTaskWrapper SKTaskWrapper) {
        final var taskIdent = SKTaskWrapper.getIdent();

        logger.debug(String.format("Scheduling task %s", SKTaskWrapper.getIdent()));

        // check if the job is already running
        if (runningJob.containsKey(SKTaskWrapper.getClass().getName())) {
            logger.info(String.format("Task %s is already running", taskIdent));
            return;
        }

        final SKThreadFactory oneShotThreadFactory = new SKThreadFactory("task-thread");

        /* Create a runnable for the scheduled task */
        Runnable taskRunnable = () -> {
            ExecutorService es = Executors.newSingleThreadExecutor(oneShotThreadFactory);
            // Create a job wrapper
            SKTaskRunnable SKTaskRunnable = new SKTaskRunnable(SKTaskWrapper);
            // Submit the job
            Future<?> f = es.submit(SKTaskRunnable);

            try {
                f.get(JOB_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);
                SKTaskWrapper.setState(SKTaskRunState.COMPLETED);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                f.cancel(true);
                SKTaskWrapper.setState(SKTaskRunState.FAILED);
                logger.error(String.format("Task %s failed (err: %s)", SKTaskWrapper.getIdent(), e.getStackTrace()));
            } finally {
                es.shutdown();
            }
        };

        logger.info(String.format("Scheduling task %s", SKTaskWrapper.getIdent()));

        // Schedule the job
        ScheduledFuture<?> f = es.scheduleAtFixedRate(
                taskRunnable,
                0,
                SKTaskWrapper.getPeriodInMs(),
                TimeUnit.MILLISECONDS);
        runningJob.put(taskIdent, f);
    }

    public void shutdown() {
        es.shutdown();
        logger.info("Shutting down task scheduler. Will wait for all tasks to complete or timeout (60s)");
        try {
            if (!es.awaitTermination(SHUTDOWN_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)) {
                logger.info("Forcing shutdown...");
                es.shutdownNow();
            }
        } catch (InterruptedException e) {
            // Try to shut down again
            es.shutdownNow();
            // Preserve interrupt status so that calling code can also see it
            Thread.currentThread().interrupt();
        }

    }
}
