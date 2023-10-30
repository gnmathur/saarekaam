package com.gnmathur.saarekaam.core.schedulers;

import com.gnmathur.saarekaam.core.SKCron;
import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy;
import com.gnmathur.saarekaam.core.SKTaskWrapper;

import java.util.concurrent.*;

public class SKTaskSchedulerCron extends SKTaskScheduler {
    @Override
    public ScheduledFuture schedule(SKTaskWrapper SKTaskWrapper) {
        // check if the job is already running and get the runnable
        Runnable taskRunnable = createTaskRunnable(SKTaskWrapper);

        var ut = SKTaskWrapper.getUnderlyingTask();
        var p = ut.policy();

        final Runnable rescheduleWrapper = new Runnable() {
            @Override
            public void run() {
                taskRunnable.run();
                ste.schedule(
                        this,
                        SKCron.getNextExecutionTime(((SKTaskSchedulingPolicy.Cron) p).cronExpression()),
                        TimeUnit.MILLISECONDS);
            }
        };

        final ScheduledFuture<?> f = ste.schedule(
                rescheduleWrapper,
                SKCron.getNextExecutionTime(((SKTaskSchedulingPolicy.Cron) p).cronExpression()),
                TimeUnit.MILLISECONDS);

        return f;
    }
}
