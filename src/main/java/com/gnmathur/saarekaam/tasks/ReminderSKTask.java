package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.SKTask;
import com.gnmathur.saarekaam.core.SKTaskException;
import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReminderSKTask implements SKTask {
    private static Logger logger = LogManager.getLogger(ReminderSKTask.class);

    @Override
    public void execute() throws SKTaskException {
        logger.info("Hello world! from PrintTask");
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.FixedNumberOfTimes(7);
    }
}
