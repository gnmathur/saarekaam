package com.gnmathur.saarekaam.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gnmathur.saarekaam.core.SKTaskRunState.*;

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
            jw.setState(COMPLETED);
            jw.incTimesCompleted();
            jw.setPreviousRunTime(end);

            if (end - start > jw.getPeriodInMs()) {
                logger.warn(String.format("Task %s is taking longer to complete than its time period. Took %d ms", jw.getIdent(), (end - start)));
                mc.lateJob();
            } else {
                logger.info(String.format("Task %s took %d ms", jw.getIdent(), (end - start)));
            }
        } catch (SKTaskException e) {
            jw.setState(FAILED);
            jw.getTimesFailed();
            logger.warn(String.format("Task %s failed (err: %s)", jw.getIdent(), e.getMessage()));
        }
    }
}
