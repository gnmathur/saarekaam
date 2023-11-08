package com.gnmathur.saarekaam.core.scheduler;

public interface SKTaskSchedulerMBean {
    /**
     * @return the number of tasks that are currently running for this scheduler
     */
    public long getActiveTasks();

    /**
     * @return the number of milliseconds since this scheduler was started
     */
    public long getUpTime();

    /**
     * @return the number of inner runnable instances created by this scheduler
     */
    public long getInnerRunnableCount();

    /**
     * @return the number of times the same task was rescheduled. This might be a sign of some problem with the task
     */
    public long getRescheduleAttempts();

    public long getExceptionCount();

}
