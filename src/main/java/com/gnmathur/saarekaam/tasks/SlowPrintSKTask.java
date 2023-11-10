package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Simulates a task that chronically takes a long time to execute. Its execution time is longer than the scheduling
 * period. This task is scheduled to run every 5 seconds while it takes 10 seconds to execute. This means that the
 * task will always be "late". This is a good test to see how the scheduler handles late tasks. An important scheduler
 * be
 */
public class SlowPrintSKTask implements SKTask {
    private static Logger logger = SKLogger.getLogger(SlowPrintSKTask.class);
    private static Random r = new Random();

    @Override
    public void execute() {
        logger.info("A slow task..");
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.Periodic(5000);
    }
}
