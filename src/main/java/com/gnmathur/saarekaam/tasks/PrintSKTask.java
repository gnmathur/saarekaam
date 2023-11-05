package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskException;

import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
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
        return new SKTaskSchedulingPolicy.Periodic(5000);
    }
}
