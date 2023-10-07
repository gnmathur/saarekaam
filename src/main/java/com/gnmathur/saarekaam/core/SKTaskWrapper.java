package com.gnmathur.saarekaam.core;

public class SKTaskWrapper {
    private final SKTask j;
    public SKTaskWrapper(SKTask j) { this.j = j; }

    /** Private job state */
    SKTaskRunState runState = SKTaskRunState.UNKNOWN;
    private long previousRunTime = System.currentTimeMillis();
    private long timesScheduled = 0;
    private long timesRun = 0;
    private long timesFailed = 0;

    /** Public job API */
    public SKTaskRunState getState() { return runState; }
    public String getIdent() { return j.getClass().getCanonicalName(); }
    public void setState(SKTaskRunState s) { runState = s; }
    public long getPreviousRunTime() { return previousRunTime; }
    public void setPreviousRunTime(long previousRunTime) { previousRunTime = previousRunTime; }
    public long getTimesRun() { return timesRun; }
    public void incTimesRun() { timesRun += 1; }
    public long getTimesScheduled() { return timesScheduled; }
    public void incTimesScheduled() { timesScheduled += 1; }
    public long getTimesFailed() { return timesFailed; }
    public void incTimesFailed() { timesFailed += 1; }


    public void execute() throws SKTaskException { j.execute(); }
    public long getPeriodInMs() { return j.getPeriodInMs(); }
}
