package com.gnmathur.saarekaam.core;

public class SKSchedulerPolicy {
    // The maximum number of concurrent jobs that can be executed by the scheduler at any given time
    public static final int MAX_CONCURRENT_JOBS = 20;
    public static final int JOB_TIMEOUT_IN_MS = (1 * 60 * 1000); // 1 minutes in milliseconds
    public static final int SHUTDOWN_TIMEOUT_IN_MS = (5 * 1000); // 5 seconds in milliseconds
}
