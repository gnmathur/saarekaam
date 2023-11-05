/*
MIT License

Copyright (c) 2023 Gaurav Mathur

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.gnmathur.saarekaam.core.task;

import java.util.UUID;

/**
 * A wrapper for SKTask that holds the state of the task. This is used by the scheduler to keep track of the task.
 */
public class SKTaskWrapper implements SKTaskMBean {
    private final SKTask underlyingTask;
    private final String taskName;

    public SKTaskWrapper(SKTask underlyingTask) {
        this.underlyingTask = underlyingTask;
        this.taskName = underlyingTask.getClass().getSimpleName();
    }

    /** Private job state */
    SKTaskRunState runState = SKTaskRunState.UNKNOWN;
    private long previousRunTime = System.currentTimeMillis();
    private long timesScheduled = 0;
    private long timesCompleted = 0;
    private long timesFailed = 0;
    private long timesCancelled = 0;

    /** Public job API */
    public SKTaskRunState getState() {
        return runState;
    }

    public String getIdent() {
        return underlyingTask.getClass().getSimpleName();
    }

    public synchronized void setState(SKTaskRunState s) {
        runState = s;
    }

    public long getPreviousRunTime() {
        return previousRunTime;
    }

    public void setPreviousRunTime(long previousRunTime) {
        previousRunTime = previousRunTime;
    }

    public long getTimesCompleted() {
        return timesCompleted;
    }

    public void incTimesCompleted() {
        timesCompleted += 1;
    }

    public long getTimesScheduled() {
        return timesScheduled;
    }

    public void incTimesScheduled() {
        timesScheduled += 1;
    }

    public long getTimesFailed() {
        return timesFailed;
    }

    public void incTimesFailed() { timesFailed += 1; }

    public SKTask getUnderlyingTask() {
        return underlyingTask;
    }

    public void execute() throws SKTaskException { underlyingTask.execute(); }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public String getTaskRunState() {
        return runState.toString();
    }

    @Override
    public long getTaskLastStartTime() {
        return 0;
    }

    @Override
    public long getTaskLastEndTime() {
        return 0;
    }

    @Override
    public long getTaskStartTime() {
        return 0;
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
        return 0;
    }

    @Override
    public long getTaskRunTime() {
        return 0;
    }

    @Override
    public long getTaskAverageRunTime() {
        return 0;
    }

    public SKTaskMBean getMBean() {
        return this;
    }
}
