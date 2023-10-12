package com.gnmathur.saarekaam.jobs.testtasks.slowtasks;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.SKTask;
import com.gnmathur.saarekaam.core.SKTaskException;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class SlowPrintTestTask implements SKTask {
    private static Logger logger = SKLogger.getLogger(SlowPrintTestTask.class);
    public int slowTaskTime = 2_000; // 2 seconds in ms

    @Override
    public void execute() throws SKTaskException {
        logger.info("A slow task..");
        try {
            Thread.sleep(slowTaskTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getPeriodInMs() {
        return 1000;
    }
}
