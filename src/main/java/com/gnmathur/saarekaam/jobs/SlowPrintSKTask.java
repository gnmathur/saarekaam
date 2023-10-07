package com.gnmathur.saarekaam.jobs;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.SKTask;
import com.gnmathur.saarekaam.core.SKTaskException;
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
    public long getPeriodInMs() {
        return 5000;
    }
}
