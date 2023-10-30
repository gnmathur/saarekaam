package com.gnmathur.saarekaam.core;

import com.gnmathur.saarekaam.core.schedulers.SKTaskScheduler;
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

            logger.info(String.format("Task %s completed successfully in %d ms", jw.getIdent(), end - start));
        } catch (SKTaskException e) {
            jw.setState(FAILED);
            jw.getTimesFailed();
            logger.warn(String.format("Task %s failed (err: %s)", jw.getIdent(), e.getMessage()));
        }
    }
}
