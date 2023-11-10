package com.gnmathur.saarekaam.core.task;

public interface SKTaskWrapperMBean {
    public String getTaskRunState();
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

    /**
     * Returns the total time this task has run in milliseconds. This includes the time spent in failed, cancelled and
     * successful runs.
     *
     * @return the total time this task has run in milliseconds.
     */
    public long getTaskTotalRunTime();
}
