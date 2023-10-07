package com.gnmathur.saarekaam.jobs.slowtasks;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.SKTask;
import com.gnmathur.saarekaam.core.SKTaskException;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class SlowPrintTask implements SKTask {
    private static Logger logger = SKLogger.getLogger(SlowPrintTask.class);
    private static Random r = new Random();

    @Override
    public void execute() throws SKTaskException {
        logger.info("A slow task..");
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getPeriodInMs() {
        return 1000;
    }
}
