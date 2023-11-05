package com.gnmathur.saarekaam.core.scheduler;

import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import com.gnmathur.saarekaam.core.task.SKTaskWrapper;

import java.util.concurrent.*;

public class SKTaskSchedulerFixedRate extends SKTaskScheduler {
    public SKTaskSchedulerFixedRate(ScheduledExecutorService ste, ExecutorService cte) {
        super(ste, cte);
    }

    @Override
    public ScheduledFuture schedule(SKTaskWrapper SKTaskWrapper) {
        // Get the task runnable
        Runnable taskRunnable = createTaskRunnable(SKTaskWrapper);

        var ut = SKTaskWrapper.getUnderlyingTask();
        var p = ut.policy();

        // Schedule the job
        ScheduledFuture<?> f = ste.scheduleAtFixedRate(
                taskRunnable,
                0,
                ((SKTaskSchedulingPolicy.Periodic) SKTaskWrapper.getUnderlyingTask().policy()).period(),
                TimeUnit.MILLISECONDS);
        return f;
    }

    @Override
    public void shutdown() {

    }
}
