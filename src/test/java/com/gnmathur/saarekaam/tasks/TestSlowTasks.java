package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.SKTaskDispatcher;
import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy;
import com.gnmathur.saarekaam.core.SKTaskWrapper;
import com.gnmathur.saarekaam.tasks.testtasks.slowtasks.SlowPrintTestTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSlowTasks {
    // This test checks that the scheduler does not execute a slow task again before it has finished executing.
    @Test
    public void testSlowTasksAreNotOverscheduled() throws InterruptedException {

        SKTaskDispatcher d = new SKTaskDispatcher();

        SlowPrintTestTask t = new SlowPrintTestTask();
        SKTaskWrapper tw = new SKTaskWrapper(t);
        d.dispatch(tw);

        int iterationsToTestFor = 8;
        long taskExpectedToCompleteInMs = ((SKTaskSchedulingPolicy.Periodic)t.policy()).period() * iterationsToTestFor;

        // Wait for the task to complete
        Thread.sleep(taskExpectedToCompleteInMs);

        d.shutdown();

        assertTrue(tw.getTimesCompleted() < taskExpectedToCompleteInMs/t.slowTaskTime + 1);
    }

}