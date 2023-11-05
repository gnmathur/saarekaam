package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskException;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class SlowPrintSKTask implements SKTask {
    private static Logger logger = SKLogger.getLogger(SlowPrintSKTask.class);
    private static Random r = new Random();

    @Override
    public void execute() throws SKTaskException {
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
