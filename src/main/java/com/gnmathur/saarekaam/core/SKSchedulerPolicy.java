package com.gnmathur.saarekaam.core;

public class SKSchedulerPolicy {
    public static final int MAX_CONCURRENT_JOBS = 1000; // number
    public static final int JOB_TIMEOUT_IN_MS = (5 * 60 * 1000); // 5 minutes in milliseconds
    public static final int SHUTDOWN_TIMEOUT_IN_MS = (30 * 1000); // 1 minute in milliseconds
}
