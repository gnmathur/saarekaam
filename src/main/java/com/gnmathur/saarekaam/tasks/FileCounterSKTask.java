package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;


public class FileCounterSKTask implements SKTask {
    // get the logger for this class
    private static Logger logger = SKLogger.getLogger(FileCounterSKTask.class);

    static List<String> dirs = Arrays.asList(
            "/var/lib/",
            "/var/log/",
            "/var/cache/",
            "/opt/GoLand-2023.2/lib",
            "/opt/idea-IU-223.7571.182/lib",
            "/usr/local/lib");
    static Random rand = new Random();
    @Override
    public void execute() {
        logger.info("Starting FileCounter task");
        String dir = dirs.get(rand.nextInt(dirs.size()));
        Path directoryPath = Paths.get(dir);

        if (!Files.isDirectory(directoryPath)) {
            logger.warn("The provided path is not a directory!");
            return;
        }

        int fileCount = countFiles(directoryPath);
        logger.info("Total number of files in " + dir + " directory and subdirectories: " + fileCount);
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.Periodic(10000);
    }

    public int countFiles(Path directory) {
        FileCounterVisitor fileCounterVisitor = new FileCounterVisitor();
        try {
            Files.walkFileTree(directory, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, fileCounterVisitor);
        } catch (IOException e) {
            logger.warn("Error reading directory: " + e.getMessage());
        }
        return fileCounterVisitor.getCount();
    }

    private class FileCounterVisitor extends SimpleFileVisitor<Path> {
        private int count = 0;

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (attrs.isRegularFile()) {
                count++;
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            logger.warn("Error visiting file: " + file.toString() + " - " + exc.getMessage());
            return FileVisitResult.CONTINUE;
        }

        public int getCount() {
            return count;
        }
    }
}
