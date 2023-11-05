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

import com.gnmathur.saarekaam.core.scheduler.SKSchedulerExecutors;
import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy.Cron;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy.Periodic;
import com.gnmathur.saarekaam.core.scheduler.SKTaskScheduler;
import com.gnmathur.saarekaam.core.scheduler.SKTaskSchedulerCron;
import com.gnmathur.saarekaam.core.scheduler.SKTaskSchedulerFixedRate;
import com.gnmathur.saarekaam.core.task.SKTaskWrapper;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     This class manages the lifecycle of the scheduler. It is responsible for initializing the scheduler and
 *     shutting it down. It also dispatches tasks to the scheduler.
 *
 *     Note: In the future we may consider creating singleton instances of the schedulers and registering them with
 *     more than one Task types.
 *     </p>
 */
public class SKManager {
    private static final Logger logger = SKLogger.getLogger(SKManager.class);
    private final SKSchedulerExecutors schedulerExecutors;
    private final Map<Class<? extends SKTaskSchedulingPolicy>, SKTaskScheduler> schedulerMap;


    public SKManager() {
        logger.info("Initializing the dispatcher");
        this.schedulerMap = new HashMap<>();
        this.schedulerExecutors = new SKSchedulerExecutors();
        schedulerMap.put(Periodic.class, new SKTaskSchedulerFixedRate(schedulerExecutors.getSte(), schedulerExecutors.getCte()));
        schedulerMap.put(Cron.class, new SKTaskSchedulerCron(schedulerExecutors.getSte(), schedulerExecutors.getCte()));
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
        schedulerExecutors.shutdown();
    }
}
