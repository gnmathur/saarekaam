package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.task.SKTask;
import com.gnmathur.saarekaam.core.task.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LargeDownloadSKTask implements SKTask {
    private static Logger logger = SKLogger.getLogger(LargeDownloadSKTask.class);

    @Override
    public void execute() {
        logger.info("Downloading a large file from OpenLibrary");
        try {
            // URL url = new URL("https://openlibrary.org/data/ol_dump_ratings_latest.txt.gz");
            URL url = new URL("https://openlibrary.org/data/ol_dump_works_latest.txt.gz");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            int responseCode = httpURLConnection.getResponseCode();
            logger.info("Response code: " + responseCode);
            // read the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
                FileOutputStream fos = new FileOutputStream("/tmp/ol_dump_works_latest.txt.gz");
                //FileOutputStream fos = new FileOutputStream("/tmp/ol_dump_ratings_latest.txt.gz");

                byte[] buffer = new byte[8192];
                int bytesRead = -1;

                while ((bytesRead = bis.read(buffer)) != -1 && !isCancelled()) {
                    fos.write(buffer, 0, bytesRead);
                }
                // close
                fos.close();
                bis.close();
                httpURLConnection.disconnect();
            } else {
                logger.warn("Response code is not OK");
            }
            httpURLConnection.disconnect();
        } catch (IOException e) {
            logger.error("Error downloading file from OpenLibrary ", e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        //return new SKTaskSchedulingPolicy.Periodic(30 * 60 * 1000);
        return new SKTaskSchedulingPolicy.FixedNumberOfTimes(17, 1 * 60 * 60 * 1000);
    }
}
