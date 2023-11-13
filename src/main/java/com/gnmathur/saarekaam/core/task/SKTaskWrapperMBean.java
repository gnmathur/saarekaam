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

public interface SKTaskWrapperMBean {
    public int getTaskRunState();
    public long getTaskLastStartTime();
    public long getTaskLastEndTime();

    /**
     * Returns the number of times this task has completed successfully. A cancelled task can still complete
     * successfully.
     *
     * @return the number of times this task has completed successfully
     */
    public long getTaskSuccessCount();

    /**
     * Returns the number of times this task has failed. A cancelled task can fail
     *
     * @return the number of times this task has failed
     */
    public long getTaskFailureCount();

    /**
     * This method returns the number of times this task has been cancelled. A cancelled task can still complete
     * successfully or fail. Depending on whether it completes successfully or fails, the task will be marked as
     * completed or failed, and the corresponding success or fail counters will be incremented.
     *
     * @return the number of times this task has been cancelled
     */
    public long getTaskCancelCount();

    /**
     * Returns the number of times this task has been scheduled to run. The task could have completed successfully,
     * failed or cancelled.
     *
     * @return the number of times this task has been scheduled to run
     */
    public long getTaskRunCount();

    public long getTaskScheduledCount();

    /**
     * Returns the total time this task has run in milliseconds. This includes the time spent in failed, cancelled and
     * successful runs.
     *
     * @return the total time this task has run in milliseconds.
     */
    public long getTaskTotalRunTime();
}
