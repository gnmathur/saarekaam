package com.gnmathur.saarekaam.core.task;

public interface SKTaskWrapperMBean {
    public String getTaskRunState();
    public long getTaskLastStartTime();
    public long getTaskLastEndTime();
    public long getTaskSuccessCount();
    public long getTaskFailureCount();
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
