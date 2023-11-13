package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.task.SKTask;

import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * A simple task that prints "Hello world!" to the console. This is a good test to see if the scheduler is working.
 * There is a sleep in the task that will allow us to to see the task transition from RUNNING to COMPLETED in the
 * metrics.
 */
public class PrintSKTask implements SKTask {
    private static Logger logger = LogManager.getLogger(PrintSKTask.class);

    @Override
    public void execute() {
        logger.info("Hello world! from PrintTask");
        try {
            Thread.sleep(15_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.Periodic(60_000);
    }
}
