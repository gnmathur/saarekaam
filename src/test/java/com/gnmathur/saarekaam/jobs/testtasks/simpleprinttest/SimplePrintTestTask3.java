package com.gnmathur.saarekaam.jobs.testtasks.simpleprinttest;

import com.gnmathur.saarekaam.core.SKTaskException;

public class SimplePrintTestTask3 extends SimplePrintTestTask {
    @Override
    public void execute() throws SKTaskException {
        logger.info("SimplePrintTestTask3 executed at: " + System.currentTimeMillis());
    }
}
