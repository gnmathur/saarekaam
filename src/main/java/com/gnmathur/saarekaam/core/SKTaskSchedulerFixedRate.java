package com.gnmathur.saarekaam.core;

import java.util.concurrent.*;

public class SKTaskSchedulerFixedRate extends SKTaskScheduler {
    @Override
    public ScheduledFuture schedule(SKTaskWrapper SKTaskWrapper) {
        // Get the task runnable
        Runnable taskRunnable = createTaskRunnable(SKTaskWrapper);

        var ut = SKTaskWrapper.getUnderlyingTask();
        var p = ut.policy();

        // Schedule the job
        ScheduledFuture<?> f = ses.scheduleAtFixedRate(
                taskRunnable,
                0,
                ((SKTaskSchedulingPolicy.Periodic) SKTaskWrapper.getUnderlyingTask().policy()).period(),
                TimeUnit.MILLISECONDS);

        return f;
    }
}
