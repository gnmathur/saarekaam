package com.gnmathur.saarekaam.core.task;

public interface SKTaskMBean {
    public String getTaskName();
    public String getTaskRunState();
    public long getTaskLastStartTime();
    public long getTaskLastEndTime();
    public long getTaskStartTime();
    public long getTaskSuccessCount();
    public long getTaskFailureCount();
    public long getTaskCancelCount();
    public long getTaskRunCount();
    public long getTaskRunTime();
    public long getTaskAverageRunTime();
}
