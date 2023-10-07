package com.gnmathur.saarekaam.core;

import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class SKThreadFactory implements ThreadFactory {
    private static final Logger logger = SKLogger.getLogger(SKThreadFactory.class);
    private AtomicLong ident = new AtomicLong(0);
    private final String label;

    SKThreadFactory(final String label) {
        this.label = label;
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadName = String.format("%s-%d", label, ident.incrementAndGet());
        logger.info("Creating new thread {}", threadName);
        return new Thread(r, threadName);
    }
}
