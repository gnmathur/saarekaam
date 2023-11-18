package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReminderSKTask implements SKTask {
    private static Logger logger = LogManager.getLogger(ReminderSKTask.class);
    private long times = 0L;

    @Override
    public void execute() {
        times++;

        logger.info("ReminderSKTask executed at: " + System.currentTimeMillis() + " times: " + times);
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        // Run every 30 seconds, 163 times
        return new SKTaskSchedulingPolicy.FixedNumberOfTimes(163, 30_000);
    }
}
