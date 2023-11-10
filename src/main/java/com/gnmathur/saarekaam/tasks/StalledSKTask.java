package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

public class StalledSKTask implements SKTask {
    // get the logger
    private static Logger logger = SKLogger.getLogger(StalledSKTask.class);

    @Override
    public void execute() {
            logger.info("Starting a stalled task..");
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.Periodic(120_000);
    }
}
