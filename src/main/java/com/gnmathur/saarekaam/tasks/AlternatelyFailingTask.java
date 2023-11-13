package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

public class AlternatelyFailingTask implements SKTask {
    private static Logger logger = SKLogger.getLogger(AlternatelyFailingTask.class);
    private boolean fail = false;

    @Override
    public void execute() {
        logger.info("AlternatelyFailingTask (set to {})", fail ? "fail" : "succeed");

        try {
            Thread.sleep(30_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (fail) {
            // Alternate between success and failure
            fail = !fail;
            logger.info("AlternatelyFailingTask failed");
            throw new RuntimeException("AlternatelyFailingTask failed");
        }
        // Alternate between success and failure
        fail = !fail;
        logger.info("AlternatelyFailingTask succeeded");
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.Periodic(120_000);
    }
}
