package com.gnmathur.saarekaam;

import com.gnmathur.saarekaam.core.SKTaskLoader;
import com.gnmathur.saarekaam.core.SKTaskScheduler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gnmathur.saarekaam.core.SKTaskScheduler.*;

public class Main {
    public static Logger logger = LogManager.getLogger(Main.class);
    public static String jarPath = "target/sarekaam-1.0-SNAPSHOT.jar";

    public static void main(String[] args) {
        /* Create a new Job Scheduler */
        SKTaskScheduler SKTaskScheduler = getInstance();
        SKTaskLoader tl = SKTaskLoader.getInstance();
        tl.loadClassesFromJar(jarPath);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            SKTaskScheduler.shutdown();
        }));
    }
}