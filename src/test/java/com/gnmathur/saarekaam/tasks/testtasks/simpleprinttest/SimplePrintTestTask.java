package com.gnmathur.saarekaam.tasks.testtasks.simpleprinttest;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

public class SimplePrintTestTask implements SKTask {
    private final Logger logger;
    public long periodInMs = 2000;
    private int ident;

    public SimplePrintTestTask(int ident) {
        this.ident = ident;
        logger = SKLogger.getLogger(SimplePrintTestTask.class + "-" + ident);
    }

    @Override
    public void execute() {
        logger.info("SimplePrintTestTask" + ident + " executed at: " + System.currentTimeMillis());
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.Periodic(periodInMs);
    }
}
