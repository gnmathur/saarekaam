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

import com.gnmathur.saarekaam.core.mgmt.SKMgmt;

import java.util.Optional;

/**
 * A wrapper for SKTask that holds the state of the task. This is used by the scheduler to keep track of the task. Task
 * state is being updated from two places -
 * 1. The scheduler {@code SKTaskScheduler} that is responsible for scheduling the task. This thread updates the state
 * of the task when it is scheduled, or when it is cancelled by the scheduler
 * 2. The task thread {@code SKTaskRunnable}  that is responsible for executing the task. This thread updates the state
 * of the task when itis running, completes successfully or fails
 */
public class SKTaskWrapper implements SKTaskWrapperMBean {
    private final SKTask underlyingTask;
    private final String taskName;
    private final SKTaskContext taskContext;

    public SKTaskWrapper(SKTask underlyingTask) {
        this.underlyingTask = underlyingTask;
        this.taskName = underlyingTask.getClass().getSimpleName();
        this.taskContext = new SKTaskContext(this.taskName);
        SKMgmt.registerMBean(this, Optional.of(this.taskName), Optional.of("Task"));
    }

    /** Public API */
    public String getIdent() {
        return underlyingTask.getClass().getSimpleName();
    }

    public SKTask getUnderlyingTask() {
        return underlyingTask;
    }

    /** Delegates to the underlying task */
    public void execute() {
        underlyingTask.execute();
    }

    /** Private job state */
    private SKTaskRunState runState = SKTaskRunState.UNKNOWN;
    private long _startTime = 0L;
    private long totalRunTime = 0L;
    private long previousStartTime = 0L;
    private long previousEndTime = 0L;
    private long timesScheduled = 0;
    private long timesRunning = 0;
    private long timesCompleted = 0;
    private long timesFailed = 0;
    private long timesCancelled = 0;

    /** State update methods */
    public synchronized void setState(SKTaskRunState s) { runState = s; }
    private void recordTaskTime(long startTime, long endTime) {
        previousStartTime = startTime; previousEndTime = endTime;
        totalRunTime += endTime - startTime;
        _startTime = 0L;
    }

    public void markTaskRunning() {
        setState(SKTaskRunState.RUNNING);
        timesRunning += 1;
        var startTime = System.currentTimeMillis();
        _startTime = startTime;
    }

    public void markTaskScheduled() {
        timesScheduled += 1;
    }

    public void markTaskCompleted() {
        timesCompleted += 1;
        var endTime = System.currentTimeMillis();
        recordTaskTime(_startTime, endTime);
        setState(SKTaskRunState.COMPLETED);
    }

    /**
     * Note: We do not record the time for cancelled tasks. This is because a cancelled task will eventually be marked
     * as either having completed successfully or failed. We will record the time then.
     */
    public void markTaskCancelled() {
        timesCancelled += 1;
        setState(SKTaskRunState.CANCELLED);
    }

    public void markTaskFailed() {
        timesFailed += 1;
        var endTime = System.currentTimeMillis();
        recordTaskTime(_startTime, endTime);
        setState(SKTaskRunState.FAILED);
    }

    /** State query methods */
    public long getTimesCompleted() { return timesCompleted; }

    /** MBean methods */
    @Override
    public int getTaskRunState() {
        return runState.getValue();
    }

    @Override
    public long getTaskLastStartTime() {
        return previousStartTime;
    }

    @Override
    public long getTaskLastEndTime() {
        return previousEndTime;
    }

    @Override
    public long getTaskSuccessCount() {
        return timesCompleted;
    }

    @Override
    public long getTaskFailureCount() {
        return timesFailed;
    }

    @Override
    public long getTaskCancelCount() {
        return timesCancelled;
    }

    @Override
    public long getTaskRunCount() {
        return timesRunning;
    }

    @Override
    public long getTaskScheduledCount() { return timesScheduled; }

    @Override
    public long getTaskTotalRunTime() {
        return totalRunTime;
    }
}
