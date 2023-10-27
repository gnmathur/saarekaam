package com.gnmathur.saarekaam;

import com.gnmathur.saarekaam.core.SKTaskLoader;
import com.gnmathur.saarekaam.core.SKTaskScheduler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {
    public static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java -cp target/<jar> com.gnmathur.saarekaam.Main <jarPath>");
            System.exit(1);
        }

        String jarPath = args[0];
        /* Create a new Job Scheduler */
        SKTaskScheduler ts = SKTaskScheduler.getInstance();
        SKTaskLoader tl = SKTaskLoader.getInstance();
        tl.loadClassesFromJar(jarPath, ts);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            ts.shutdown();
        }));
    }
}