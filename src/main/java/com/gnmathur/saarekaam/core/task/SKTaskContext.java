package com.gnmathur.saarekaam.core.task;

import java.util.concurrent.ConcurrentHashMap;

public class SKTaskContext {
    private final ConcurrentHashMap<String, Object> ctxData = new ConcurrentHashMap<>();
    private final String ident;

    private SKTaskContext(final String ident) {
        this.ident = ident;
    }

    public <T> void put(String key, T value) {
        ctxData.put(key, value);
    }

    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(ctxData.get(key));
    }

    public long size() {
        return ctxData.size();
    }
}
