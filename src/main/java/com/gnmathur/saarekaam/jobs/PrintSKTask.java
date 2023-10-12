package com.gnmathur.saarekaam.jobs;

import com.gnmathur.saarekaam.core.SKTask;
import com.gnmathur.saarekaam.core.SKTaskException;

import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class PrintSKTask implements SKTask {
    private static Logger logger = LogManager.getLogger(PrintSKTask.class);

    @Override
    public void execute() throws SKTaskException {
        logger.info("Hello world! from PrintTask");
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.PeriodicTaskSchedulingPolicy(5000);
    }
}
