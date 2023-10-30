package com.gnmathur.saarekaam.core.schedulers;

import com.gnmathur.saarekaam.core.SKLogger;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 *     This class is used to create threads for the task scheduler. The advantage of using this
 *     class is that we can customize thread names and other properties of the thread. It also
 *     helps in accounting for the threads created by the scheduler <code>{@link SKTaskScheduler}</code>
 * </p>
 *
 * @see SKTaskScheduler
 */
public final class SKThreadFactory implements ThreadFactory {
    private static final Logger logger = SKLogger.getLogger(SKThreadFactory.class);
    private AtomicLong ident = new AtomicLong(0);
    // Thread name prefix - Prefix for the thread name
    private final String tnp;

    SKThreadFactory(final String tnp) {
        this.tnp = tnp;
    }

    @Override
    public Thread newThread(Runnable r) {
        final String threadName = String.format("%s-%d", tnp, ident.incrementAndGet());
        logger.info("Creating new thread {}", threadName);
        return new Thread(r, threadName);
    }
}
