package com.gnmathur.saarekaam.tasks.cron;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import com.gnmathur.saarekaam.tasks.PGQueryTaskA;
import org.apache.logging.log4j.Logger;

public class CronPrinterTaskEveryMinute implements SKTask {
    private static final Logger logger = SKLogger.getLogger(CronPrinterTaskEveryMinute.class);

    @Override
    public void execute() {
        logger.info("Every minute task executed");
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        // Every minute
        return new SKTaskSchedulingPolicy.Cron("0/1 * * * *");
    }
}
