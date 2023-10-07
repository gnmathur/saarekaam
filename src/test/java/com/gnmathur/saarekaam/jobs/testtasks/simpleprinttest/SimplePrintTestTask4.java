package com.gnmathur.saarekaam.jobs.testtasks.simpleprinttest;

import com.gnmathur.saarekaam.core.SKTaskException;

public class SimplePrintTestTask4 extends SimplePrintTestTask {
    @Override
    public void execute() throws SKTaskException {
        logger.info("SimplePrintTestTask4 executed at: " + System.currentTimeMillis());
    }
}
