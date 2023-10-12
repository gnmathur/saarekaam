package com.gnmathur.saarekaam.core;

public interface SKTask {
    /** To be implemented by the job definition */

    /* Job definition */
    void execute() throws SKTaskException;

    /* Get the job period in milliseconds */
    long getPeriodInMs();
}