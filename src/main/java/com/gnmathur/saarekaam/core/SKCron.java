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

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import org.apache.logging.log4j.Logger;

public class SKCron {
    private static final Logger logger = SKLogger.getLogger(SKCron.class);

    /**
     * Returns the next execution time for the given cron expression.
     *
     * @param cronExpression The cron expression
     * @return The next execution time in milliseconds
     */
    public static long getNextExecutionTime(String cronExpression) {
        var cd = CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX);
        var cp = new CronParser(cd);
        var cron = cp.parse(cronExpression);
        var duration = ExecutionTime.forCron(cron).timeToNextExecution(java.time.ZonedDateTime.now()).get();

        logger.info(String.format("Next execution time for cron %s is %d ms", cronExpression, duration.toMillis()));
        return duration.toMillis();
    }
}
