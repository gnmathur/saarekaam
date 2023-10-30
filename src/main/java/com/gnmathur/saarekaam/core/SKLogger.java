package com.gnmathur.saarekaam.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is used to get the logger instance for the application. The logger is based on the
 * Log4j2 framework. The logger is configured in the log4j2.xml file.
 *
 * @see org.apache.logging.log4j.Logger
 * @see org.apache.logging.log4j.LogManager
 */
public class SKLogger {
    // Get the logger for the class
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    // Get the logger for the name
    public static Logger getLogger(String name) {
        return LogManager.getLogger(name);
    }
}
