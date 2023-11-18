/*
 * MIT License
 *
 * Copyright (c) 2023 Gaurav Mathur
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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

    /**
     * Periodic: The task will be executed periodically after a fixed interval. Interval is in milliseconds
     */
    record Periodic(long period) implements SKTaskSchedulingPolicy { }

    /** OneTime: The task will be executed after a fixed delay. Delay is in milliseconds
     *
     * @param delay delay in milliseconds
     */
    record OneTime(long delay) implements SKTaskSchedulingPolicy { }

    /** Cron: The task will be executed at a fixed time of the day. The cron expression is in the UNIX cron format
     *
     * @param cronExpression cron expression in the UNIX cron format
     */
    record Cron(String cronExpression) implements SKTaskSchedulingPolicy { }

    /** FixedNumberOfTimes: The task will be executed a fixed number of times
     *
     * @param times number of times
     * @param delayBetweenInvocations delay between invocations in milliseconds
     */
    record FixedNumberOfTimes(int times, int delayBetweenInvocations) implements SKTaskSchedulingPolicy { }
}

