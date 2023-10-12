package com.gnmathur.saarekaam.jobs;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.SKTask;
import com.gnmathur.saarekaam.core.SKTaskException;
import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

public class StalledSKTask implements SKTask {
    // get the logger
    private static Logger logger = SKLogger.getLogger(StalledSKTask.class);

    @Override
    public void execute() throws SKTaskException {
        try {
            logger.info("Starting a stalled task..");
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new SKTaskException(e);
        }
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.PeriodicTaskSchedulingPolicy(5000);
    }
}
