package com.gnmathur.saarekaam.tasks.cron;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

public class CronPrinterTaskEveryTwoDayAt1345AM implements SKTask {
    private static final Logger logger = SKLogger.getLogger(CronPrinterTaskEveryTwoDayAt1345AM.class);

    @Override
    public void execute() {
        logger.info("Every two day at 10:05 AM task executed");
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.Cron("45 13 */2 * *");
    }
}
