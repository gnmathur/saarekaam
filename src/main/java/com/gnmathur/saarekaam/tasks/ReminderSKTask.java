package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReminderSKTask implements SKTask {
    private static Logger logger = LogManager.getLogger(ReminderSKTask.class);

    @Override
    public void execute() {
        logger.info("Hello world! from PrintTask");
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.FixedNumberOfTimes(7);
    }
}
