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

        SKTaskScheduler scheduler = SKTaskScheduler.getInstance();
        SlowPrintTestTask slowPrintTestTask = new SlowPrintTestTask();
        SKTaskWrapper task = new SKTaskWrapper(slowPrintTestTask);
        scheduler.schedule(task);

        int iterationsToTestFor = 8;
        long taskExpectedToCompleteInMs = slowPrintTestTask.getPeriodInMs() * iterationsToTestFor;

        // Wait for the task to complete
        Thread.sleep(taskExpectedToCompleteInMs);

        scheduler.shutdown();

        assertTrue(task.getTimesCompleted() < taskExpectedToCompleteInMs/slowPrintTestTask.slowTaskTime + 1);
    }

}
