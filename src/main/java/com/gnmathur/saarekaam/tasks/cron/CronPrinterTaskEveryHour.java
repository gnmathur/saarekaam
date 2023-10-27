package com.gnmathur.saarekaam.tasks.cron;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.SKTask;
import com.gnmathur.saarekaam.core.SKTaskException;
import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy;
import com.gnmathur.saarekaam.tasks.PGQueryTaskA;
import org.apache.logging.log4j.Logger;

public class CronPrinterTaskEveryHour implements SKTask {
    private static final Logger logger = SKLogger.getLogger(PGQueryTaskA.class);

    @Override
    public void execute() throws SKTaskException {
        logger.info("Every hour task executed");
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        // Every 7th minute
        return new SKTaskSchedulingPolicy.Cron("0 * * * *");
    }

    @Override
    public Object context() {
        return SKTask.super.context();
    }
}
