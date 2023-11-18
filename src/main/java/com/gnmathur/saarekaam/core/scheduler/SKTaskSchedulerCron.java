package com.gnmathur.saarekaam.core.scheduler;

import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import com.gnmathur.saarekaam.core.task.SKTaskWrapper;

import java.util.concurrent.*;

public class SKTaskSchedulerCron extends SKTaskScheduler {
    public SKTaskSchedulerCron(ScheduledExecutorService ste, ExecutorService cte) {
        super(ste, cte);
    }

    @Override
    public ScheduledFuture schedule(SKTaskWrapper wrappedTask) {
        // check if the job is already running and get the runnable
        Runnable taskRunnable = createCancellableTask(wrappedTask);

        var ut = wrappedTask.getUnderlyingTask();
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

    @Override
    public void shutdown() {

    }
}
