/*
MIT License

Copyright (c) 2023 Gaurav Mathur

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.gnmathur.saarekaam.core;

public class SKTaskWrapper {
    private final SKTask underlyingTask;

    public SKTaskWrapper(SKTask underlyingTask) { this.underlyingTask = underlyingTask; }

    /** Private job state */
    SKTaskRunState runState = SKTaskRunState.UNKNOWN;
    private long previousRunTime = System.currentTimeMillis();
    private long timesScheduled = 0;
    private long timesCompleted = 0;
    private long timesFailed = 0;

    /** Public job API */
    public SKTaskRunState getState() { return runState; }
    public String getIdent() { return underlyingTask.getClass().getSimpleName(); }
    public void setState(SKTaskRunState s) { runState = s; }
    public long getPreviousRunTime() { return previousRunTime; }
    public void setPreviousRunTime(long previousRunTime) { previousRunTime = previousRunTime; }
    public long getTimesCompleted() { return timesCompleted; }
    public void incTimesCompleted() { timesCompleted += 1; }
    public long getTimesScheduled() { return timesScheduled; }
    public void incTimesScheduled() { timesScheduled += 1; }
    public long getTimesFailed() { return timesFailed; }
    public void incTimesFailed() { timesFailed += 1; }
    public SKTask getUnderlyingTask() { return underlyingTask; }


    public void execute() throws SKTaskException { underlyingTask.execute(); }
}
