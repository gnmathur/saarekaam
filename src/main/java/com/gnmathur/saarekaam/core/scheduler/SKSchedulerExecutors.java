package com.gnmathur.saarekaam.core.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.gnmathur.saarekaam.core.scheduler.SKSchedulerPolicy.MAX_CONCURRENT_JOBS;
import static com.gnmathur.saarekaam.core.scheduler.SKSchedulerPolicy.SHUTDOWN_TIMEOUT_IN_MS;

public class SKSchedulerExecutors {
    private static final Logger logger = LogManager.getLogger(SKSchedulerExecutors.class);
    private final ScheduledExecutorService ste;
    private final ExecutorService cte;

    public SKSchedulerExecutors() {
        // Chose a scheduled thread pool for the scheduler. This is the core executor for managing the feature
        // of periodic tasks.
        ste = Executors.newScheduledThreadPool(MAX_CONCURRENT_JOBS, new SKThreadFactory("sthread"));

        // Chose a cached thread pool for the cancelable task executor. This executor will be used to create another
        // thread when the task gets scheduled to run. The advantage of this thread-in-a-thread approach is that
        // the scheduler can now cancel the task if it is taking too long to complete. This will prevent the scheduler
        // from getting blocked
        cte = Executors.newCachedThreadPool(new SKThreadFactory("cthread"));
    }

    /**
     * Get the scheduled thread pool executor. This is the core executor for managing the feature
     *
     * @return
     */
    public ScheduledExecutorService getSte() {
        return ste;
    }

    /**
     * Get the cached thread pool executor. This executor will be used to create another thread when the task gets
     * scheduled to run. The advantage of this thread-in-a-thread approach is that the scheduler can now cancel the task
     * if it is taking too long to complete. This will prevent the scheduler from getting blocked
     *
     * @return
     */
    public ExecutorService getCte() {
        return cte;
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
}
