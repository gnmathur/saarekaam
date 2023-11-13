/*
 * MIT License
 *
 * Copyright (c) 2023 Gaurav Mathur
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.gnmathur.saarekaam.core.task;

import java.util.concurrent.ConcurrentHashMap;

public final class SKTaskContext {
    private final ConcurrentHashMap<String, Object> ctxData = new ConcurrentHashMap<>();
    private final String ident;

    public SKTaskContext(final String ident) {
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
