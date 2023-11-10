package com.gnmathur.saarekaam.core.task;

public interface SKTask {
    /** To be implemented by the job definition */

    /* Job definition */
    void execute();

    /* Get the job period in milliseconds */
    SKTaskSchedulingPolicy policy();

    /* Get task context */
    default Object context() {
        return null;
    }
}
