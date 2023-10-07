package com.gnmathur.saarekaam.jobs.testtasks.simpleprinttest;

import com.gnmathur.saarekaam.core.SKTaskException;

public class SimplePrintTestTask1 extends SimplePrintTestTask {

    /* The execute method is called by the scheduler every time the task is scheduled to run. This test is based
     * on the premise that the execute method completes in less than 2 seconds.
     */
    @Override
    public void execute() throws SKTaskException {
        logger.info("SimplePrintTestTask1 executed at: " + System.currentTimeMillis());
    }
}
