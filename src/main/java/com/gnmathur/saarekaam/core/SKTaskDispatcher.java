/*
MIT License

Copyright (c) 2023 Gaurav Mathur

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.gnmathur.saarekaam.core;

import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy.Cron;
import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy.Periodic;
import com.gnmathur.saarekaam.core.schedulers.SKTaskScheduler;
import com.gnmathur.saarekaam.core.schedulers.SKTaskSchedulerCron;
import com.gnmathur.saarekaam.core.schedulers.SKTaskSchedulerFixedRate;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SKTaskDispatcher {
    private static final Logger logger = SKLogger.getLogger(SKTaskDispatcher.class);

    private final Map<Class<? extends SKTaskSchedulingPolicy>, SKTaskScheduler> schedulerMap;


    public SKTaskDispatcher() {
        logger.info("Initializing the dispatcher");
        this.schedulerMap = new HashMap<>();
        schedulerMap.put(Periodic.class, new SKTaskSchedulerFixedRate());
        schedulerMap.put(Cron.class, new SKTaskSchedulerCron());
    }

    public void dispatch(SKTaskWrapper taskWrapper) {
        final SKTask task = taskWrapper.getUnderlyingTask();
        final SKTaskScheduler scheduler = schedulerMap.get(task.policy().getClass());

        if (scheduler != null) {
            scheduler.scheduleIt(taskWrapper);
        } else {
            logger.error("No scheduler available for task type: " + task.getClass() +
                    " with policy: " + task.policy().getClass() +
                    ". Skipping task.");
        }
    }

    public void shutdown() {
        logger.info("Shutting down the dispatcher");

        schedulerMap.values().forEach(SKTaskScheduler::shutdown);
    }
}
