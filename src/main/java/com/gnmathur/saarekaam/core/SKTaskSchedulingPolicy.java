package com.gnmathur.saarekaam.core;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;

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

    record Periodic(long period) implements SKTaskSchedulingPolicy { }
    record OneTime(long delay) implements SKTaskSchedulingPolicy { }
    record Cron(String cronExpression) implements SKTaskSchedulingPolicy { }
    record FixedNumberOfTimes(int times) implements SKTaskSchedulingPolicy { }
}

