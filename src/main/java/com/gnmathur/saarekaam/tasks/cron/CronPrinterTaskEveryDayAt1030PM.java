package com.gnmathur.saarekaam.tasks.cron;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import com.gnmathur.saarekaam.tasks.PGQueryTaskA;
import org.apache.logging.log4j.Logger;

public class CronPrinterTaskEveryDayAt1030PM implements SKTask {
    private static final Logger logger = SKLogger.getLogger(CronPrinterTaskEveryDayAt1030PM.class);

    @Override
    public void execute() {
        logger.info("Everyday 10:30 PM task executed");
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        // Run every day at 10:30 PM local time
        return new SKTaskSchedulingPolicy.Cron("30 22 * * *");
    }
}
