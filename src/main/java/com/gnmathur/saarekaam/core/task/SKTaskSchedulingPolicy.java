package com.gnmathur.saarekaam.core.task;

/**
 * A scheduling policy for a task. A task can be scheduled in one of the following ways:
 *
 * Periodic: The task will be executed periodically after a fixed interval
 * OneTime: The task will be executed after a fixed delay
 * Cron: The task will be executed at a fixed time of the day
 * FixedNumberOfTimes: The task will be executed a fixed number of times
 *
 * The task will be executed in a separate thread.
 */
public sealed interface SKTaskSchedulingPolicy permits
        SKTaskSchedulingPolicy.Periodic,
        SKTaskSchedulingPolicy.OneTime,
        SKTaskSchedulingPolicy.Cron,
        SKTaskSchedulingPolicy.FixedNumberOfTimes {

    // Periodic: The task will be executed periodically after a fixed interval. Interval is in milliseconds
    record Periodic(long period) implements SKTaskSchedulingPolicy { }
    // OneTime: The task will be executed after a fixed delay. Delay is in milliseconds
    record OneTime(long delay) implements SKTaskSchedulingPolicy { }
    // Cron: The task will be executed at a fixed time of the day. The cron expression is in the UNIX cron format
    record Cron(String cronExpression) implements SKTaskSchedulingPolicy { }
    // FixedNumberOfTimes: The task will be executed a fixed number of times
    record FixedNumberOfTimes(int times, int delayBetweenInvocations) implements SKTaskSchedulingPolicy { }
}

