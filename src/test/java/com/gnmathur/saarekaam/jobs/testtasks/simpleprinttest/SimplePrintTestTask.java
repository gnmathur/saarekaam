package com.gnmathur.saarekaam.jobs.testtasks.simpleprinttest;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.SKTask;
import org.apache.logging.log4j.Logger;

public abstract class SimplePrintTestTask implements SKTask {
    protected static Logger logger = SKLogger.getLogger(SimplePrintTestTask1.class);
    public long periodInMs = 2000;

    @Override
    public long getPeriodInMs() {
        return periodInMs; // Executes every 2 seconds
    }
}
