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
package com.gnmathur.saarekaam;

import com.gnmathur.saarekaam.core.SKTaskDispatcher;
import com.gnmathur.saarekaam.core.SKTaskLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {
    public static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java -cp target/<jar> com.gnmathur.saarekaam.Main <jarPath>");
            System.exit(1);
        }

        SKTaskDispatcher td = new SKTaskDispatcher();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Shutting down...\n");
                logger.info("Shutting down...");

                td.shutdown();

                logger.info("Shutdown complete. Bye!");
            } catch (Exception e) {
                System.out.printf("Error while shutting down: %s\n", e.getMessage());
            }
            // flush stdout
            System.out.flush();
            // flush logger
            LogManager.shutdown();
        }));

        String jarPath = args[0];

        SKTaskLoader tl = SKTaskLoader.getInstance();

        tl.loadClassesFromJar(jarPath, td);

    }
}