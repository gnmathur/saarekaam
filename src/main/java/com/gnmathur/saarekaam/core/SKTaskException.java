package com.gnmathur.saarekaam.core;

public class SKTaskException extends Exception {
    public SKTaskException(String message) {
        super(message);
    }

    public SKTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public SKTaskException(Throwable cause) {
        super(cause);
    }
}
