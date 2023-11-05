package com.gnmathur.saarekaam.core.task;

import com.gnmathur.saarekaam.core.SKMetricsCollector;
import com.gnmathur.saarekaam.core.scheduler.SKTaskScheduler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SKTaskRunnable implements Runnable {
    private static final Logger logger = LogManager.getLogger(SKTaskScheduler.class);
    private SKTaskWrapper jw;
    private SKMetricsCollector mc = SKMetricsCollector.getInstance();

    public SKTaskRunnable(SKTaskWrapper jw) { this.jw = jw; }

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();
            jw.incTimesScheduled();
            jw.execute();
            long end = System.currentTimeMillis();
            jw.incTimesCompleted();
            jw.setPreviousRunTime(end);

            logger.info(String.format("Task %s completed successfully in %d ms", jw.getIdent(), end - start));
        } catch (SKTaskException e) {
            jw.incTimesFailed();
            logger.warn(String.format("Task %s failed (err: %s)", jw.getIdent(), e.getMessage()));
        }
    }
}