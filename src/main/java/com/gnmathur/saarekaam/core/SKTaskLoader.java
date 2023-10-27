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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;

public class SKTaskLoader {
    private static Logger logger = LogManager.getLogger(SKTaskLoader.class);

    private SKTaskLoader() {}

    public static SKTaskLoader getInstance() {
        return new SKTaskLoader();
    }

    public void loadClassesFromJar(final String jarPath, final SKTaskScheduler ts) {
        logger.info("Loading classes from jar: " + jarPath);

        try (FileSystem fs = FileSystems.newFileSystem(Paths.get(jarPath), (ClassLoader) null)) {
            URLClassLoader classLoader = getURLClassLoader(jarPath);

            Files.walk(fs.getPath("/"))
                    .filter(this::isMatchingClassPath)
                    .forEach(path -> processClassPath(path, classLoader, ts));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URLClassLoader getURLClassLoader(final String jarPath) throws MalformedURLException {
        URL[] urls = { new URL("jar:file:" + jarPath + "!/") };
        return URLClassLoader.newInstance(urls);
    }

    private boolean isMatchingClassPath(Path path) {
        logger.debug("Checking if path matches: " + path.toString());
        String className = path.toString().substring(1).replace("/", ".");
        return className.endsWith(".class") &&
                className.startsWith("com.gnmathur.saarekaam.tasks.cron");
    }

    private void processClassPath(final Path path, final URLClassLoader classLoader, final SKTaskScheduler ts) {
        String className = path.toString().substring(1).replace("/", ".").replace(".class", "");
        try {
            Class<?> klass = Class.forName(className, true, classLoader);
            logger.info("Loaded class: " + klass.getName());
            instantiateAndSchedule(klass, ts);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void instantiateAndSchedule(final Class<?> klass, final SKTaskScheduler ts) {
        try {
            if (SKTask.class.isAssignableFrom(klass) && !klass.isInterface()) {
                logger.info("Instantiating class: " + klass.getName());
                SKTask SKTaskInstance = (SKTask) klass.getDeclaredConstructor().newInstance();
                SKTaskWrapper SKTaskWrapperInstance = new SKTaskWrapper(SKTaskInstance);
                ts.schedule(SKTaskWrapperInstance);
            }
        } catch (InstantiationException | IllegalAccessException |  NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
