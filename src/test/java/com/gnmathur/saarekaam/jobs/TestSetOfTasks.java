package com.gnmathur.saarekaam.jobs;

import com.gnmathur.saarekaam.core.*;
import com.gnmathur.saarekaam.jobs.testtasks.simpleprinttest.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSetOfTasks {
    @Test
    public void testNormalCompletionOfASetOfTasks() throws InterruptedException {
        int iterationsToTestFor = 7;

        SKTaskScheduler scheduler = SKTaskScheduler.getInstance();

        // Create 6 SimplePrintTestTask tasks add them to the scheduler. Store the jobs in a list so that we can
        // check if they were executed at least `iterationsToTestFor` times.
        List<SKTaskWrapper> tasks = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            SKTaskWrapper task = new SKTaskWrapper(new SimplePrintTestTask(i));
            tasks.add(task);
            scheduler.schedule(task);
        }

        Thread.sleep(tasks.get(0).getPeriodInMs() * iterationsToTestFor);

        scheduler.shutdown();

        // Check if all the jobs were executed at least iterationsToTestFor times
        for (SKTaskWrapper task : tasks) {
            System.out.printf("Task: %s, times run: %d\n", task.getIdent(), task.getTimesCompleted());
            // Tasks should have been executed at least testForIterations times
            assertTrue(task.getTimesCompleted() >= iterationsToTestFor);
            // Tasks should not have been executed more than testForIterations + 1 times
            assertTrue(task.getTimesCompleted() <= iterationsToTestFor+1);
        }
    }

}