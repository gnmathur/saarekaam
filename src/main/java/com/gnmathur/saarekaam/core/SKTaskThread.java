package com.gnmathur.saarekaam.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gnmathur.saarekaam.core.SKTaskRunState.*;

public class SKTaskThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(SKTaskScheduler.class);
    private SKTaskWrapper jw;
    private SKMetricsCollector mc = SKMetricsCollector.getInstance();

    public SKTaskThread(SKTaskWrapper jw) { this.jw = jw; }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            jw.incTimesScheduled();
            jw.execute();
            long end = System.currentTimeMillis();
            if (end - start > jw.getPeriodInMs()) {
                logger.warn(String.format("Task %s is taking longer to complete than its time period", jw.getIdent()));
                mc.lateJob();
            }
            jw.setState(COMPLETED);
            jw.incTimesRun();
            // logger info with multiple parameters
            jw.setPreviousRunTime(end);
            logger.info(String.format("Task %s took %d ms", jw.getIdent(), (end - start)));
        } catch (SKTaskException e) {
            jw.setState(FAILED);
            jw.getTimesFailed();
            logger.warn(String.format("Task %s failed (err: %s)", jw.getIdent(), e.getMessage()));
        }
    }
}
