package com.gnmathur.saarekaam.tasks.cron;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.SKTask;
import com.gnmathur.saarekaam.core.SKTaskException;
import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy;
import com.gnmathur.saarekaam.tasks.PGQueryTaskA;
import org.apache.logging.log4j.Logger;

public class CronPrinterTaskEveryTwoDayAt1005AM implements SKTask {
    private static final Logger logger = SKLogger.getLogger(PGQueryTaskA.class);

    @Override
    public void execute() throws SKTaskException {
        logger.info("Every two day at 10:05 AM task executed");
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        //
        return new SKTaskSchedulingPolicy.Cron("5 17 */2 * *");
    }

    @Override
    public Object context() {
        return SKTask.super.context();
    }
}
