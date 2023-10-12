package com.gnmathur.saarekaam.jobs;

import com.gnmathur.saarekaam.core.SKTaskScheduler;
import com.gnmathur.saarekaam.core.SKTaskWrapper;
import com.gnmathur.saarekaam.jobs.testtasks.slowtasks.SlowPrintTestTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSlowTasks {
    // This test checks that the scheduler does not execute a slow task again before it has finished executing.
    @Test
    public void testSlowTasksAreNotOverscheduled() throws InterruptedException {

        SKTaskScheduler s = SKTaskScheduler.getInstance();
        SlowPrintTestTask t = new SlowPrintTestTask();
        SKTaskWrapper tw = new SKTaskWrapper(t);
        s.schedule(tw);

        int iterationsToTestFor = 8;
        long taskExpectedToCompleteInMs = tw.getPeriodInMs() * iterationsToTestFor;

        // Wait for the task to complete
        Thread.sleep(taskExpectedToCompleteInMs);

        s.shutdown();

        assertTrue(tw.getTimesCompleted() < taskExpectedToCompleteInMs/t.slowTaskTime + 1);
    }

}
