package com.gnmathur.saarekaam.jobs.testtasks.simpleprinttest;

import com.gnmathur.saarekaam.core.SKTaskException;

public class SimplePrintTestTask2 extends SimplePrintTestTask {
    @Override
    public void execute() throws SKTaskException {
        logger.info("SimplePrintTestTask2 executed at: " + System.currentTimeMillis());
    }
}
