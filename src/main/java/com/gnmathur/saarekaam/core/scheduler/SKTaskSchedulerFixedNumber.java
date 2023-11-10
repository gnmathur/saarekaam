package com.gnmathur.saarekaam.core.scheduler;

import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import com.gnmathur.saarekaam.core.task.SKTaskWrapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SKTaskSchedulerFixedNumber extends  SKTaskScheduler {
    public SKTaskSchedulerFixedNumber(ScheduledExecutorService ste, ExecutorService cte) {
        super(ste, cte);
    }

    @Override
    public ScheduledFuture schedule(SKTaskWrapper wrappedTask) {
        Runnable taskRunnable = createCancellableTask(wrappedTask);

        var ut = wrappedTask.getUnderlyingTask();
        var p = ut.policy();

        final Runnable rescheduleWrapper = new Runnable() {
            // Times is decremented every time the task is run. When it reaches 0, the task is not rescheduled
            int times = ((SKTaskSchedulingPolicy.FixedNumberOfTimes) p).times() - 1;

            @Override
            public void run() {
                taskRunnable.run();
                if (times != 0) {
                    ste.schedule(
                            this,
                            ((SKTaskSchedulingPolicy.FixedNumberOfTimes) p).delayBetweenInvocations(),
                            TimeUnit.MILLISECONDS);
                    times--;
                }
            }
        };

        final ScheduledFuture<?> f = ste.schedule(
                rescheduleWrapper,
                ((SKTaskSchedulingPolicy.FixedNumberOfTimes) p).delayBetweenInvocations(),
                TimeUnit.MILLISECONDS);

        return f;
    }

    @Override
    public void shutdown() {

    }
}
