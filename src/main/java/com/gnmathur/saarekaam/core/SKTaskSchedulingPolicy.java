package com.gnmathur.saarekaam.core;

public sealed interface SKTaskSchedulingPolicy permits
        SKTaskSchedulingPolicy.PeriodicTaskSchedulingPolicy,
        SKTaskSchedulingPolicy.OneTimeTaskSchedulingPolicy,
        SKTaskSchedulingPolicy.CronTaskSchedulingPolicy {

    record PeriodicTaskSchedulingPolicy(long period) implements SKTaskSchedulingPolicy { }
    record OneTimeTaskSchedulingPolicy(long delay) implements SKTaskSchedulingPolicy { }
    record CronTaskSchedulingPolicy(String cronExpression) implements SKTaskSchedulingPolicy { }
}

