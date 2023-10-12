/*
MIT License

Copyright (c) 2023 Gaurav Mathur

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.gnmathur.saarekaam.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class SKTaskLoader {
    private static Logger logger = LogManager.getLogger(SKTaskLoader.class);
    private static SKTaskLoader instance = new SKTaskLoader();

    private SKTaskLoader() {}

    public static SKTaskLoader getInstance() {
        return instance;
    }

    public void loadClassesFromJar(String jarPath) {
        File jarFile = new File(jarPath);
        try {
            JarFile jar = new JarFile(jarFile);

            // Use URL class loader to load classes from jar
            URL[] urls = {
                    new URL("jar:file:" + jarPath + "!/")
            };
            URLClassLoader cl = URLClassLoader.newInstance(urls);

            // Go over all the classes in the jar
            jar.stream().forEach(jarEntry -> {
                String className = jarEntry.getName();
                if (
                        className.startsWith("com/gnmathur/saarekaam/jobs/SlowPrintSKTask") &&
                        className.endsWith(".class") &&
                        className.startsWith("com/gnmathur/saarekaam/jobs")) {
                    logger.debug(String.format("Found class: %s", className));
                    className = className.substring(0, className.length() - 6);
                    className = className.replace('/', '.');
                    try {
                        Class<?> klass = Class.forName(className, true, cl);
                        logger.debug("Loaded class: " + klass.getName());
                        if (SKTask.class.isAssignableFrom(klass) && !klass.isInterface()) {
                            logger.info("Instantiating class: " + klass.getName());
                            SKTask SKTask = (SKTask) klass.newInstance();
                            SKTaskWrapper SKTaskWrapper = new SKTaskWrapper(SKTask);
                            SKTaskScheduler.getInstance().schedule(SKTaskWrapper);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
