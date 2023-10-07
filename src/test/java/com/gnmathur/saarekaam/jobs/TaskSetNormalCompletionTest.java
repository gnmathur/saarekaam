package com.gnmathur.saarekaam.jobs;

import com.gnmathur.saarekaam.core.*;
import com.gnmathur.saarekaam.jobs.testtasks.simpleprinttest.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskSetNormalCompletionTest {
    @Test
    public void testASetOfTasks() throws InterruptedException {
        int iterationsToTestFor = 5;

        SKTaskScheduler scheduler = SKTaskScheduler.getInstance();

        // Create 6 SimplePrintTestTask tasks add them to the scheduler. Store the jobs in a list so that we can
        // check if they were executed at least `iterationsToTestFor` times.
        List<SKTaskWrapper> jobs = new ArrayList<>();
        SKTaskWrapper task1 = new SKTaskWrapper(new SimplePrintTestTask1()); jobs.add(task1); scheduler.schedule(task1);
        SKTaskWrapper task2 = new SKTaskWrapper(new SimplePrintTestTask2()); jobs.add(task2); scheduler.schedule(task2);
        SKTaskWrapper task3 = new SKTaskWrapper(new SimplePrintTestTask3()); jobs.add(task3); scheduler.schedule(task3);
        SKTaskWrapper task4 = new SKTaskWrapper(new SimplePrintTestTask4()); jobs.add(task4); scheduler.schedule(task4);
        SKTaskWrapper task5 = new SKTaskWrapper(new SimplePrintTestTask5()); jobs.add(task5); scheduler.schedule(task5);
        SKTaskWrapper task6 = new SKTaskWrapper(new SimplePrintTestTask6()); jobs.add(task6); scheduler.schedule(task6);

        Thread.sleep(task1.getPeriodInMs() * iterationsToTestFor);

        // Check if all the jobs were executed at least iterationsToTestFor times
        for (SKTaskWrapper job : jobs) {
            System.out.printf("Job: %s, times run: %d\n", job.getIdent(), job.getTimesRun());
            // Tasks should have been executed at least testForIterations times
            assertTrue(job.getTimesRun() >= iterationsToTestFor);
            // Tasks should not have been executed more than testForIterations + 1 times
            assertTrue(job.getTimesRun() <= iterationsToTestFor+1);
        }
        scheduler.shutdown();
    }

}
