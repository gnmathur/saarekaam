package com.gnmathur.saarekaam.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by gaurav on 6/9/17.
 *
 * This class is used to get the logger instance
 *
 * @see org.apache.logging.log4j.Logger
 * @see org.apache.logging.log4j.LogManager
 *
 */
public class SKLogger {
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        return LogManager.getLogger(name);
    }
}
