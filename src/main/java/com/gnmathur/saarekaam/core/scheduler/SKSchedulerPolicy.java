package com.gnmathur.saarekaam.core.scheduler;

public class SKSchedulerPolicy {
    // The maximum number of concurrent jobs that can be executed by the scheduler at any given time
    public static final int MAX_CONCURRENT_JOBS = 20;
    public static final int JOB_TIMEOUT_IN_MS = (5 * 60 * 1000); // 5 minutes in milliseconds
    public static final int SHUTDOWN_TIMEOUT_IN_MS = (5 * 1000); // 5 seconds in milliseconds
}
