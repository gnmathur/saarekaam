package com.gnmathur.saarekaam.tasks;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.SKTask;
import com.gnmathur.saarekaam.core.SKTaskException;
import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class PGQueryTaskB implements SKTask {
    private static final Logger logger = SKLogger.getLogger(PGQueryTaskB.class);

    @Override
    public void execute() throws SKTaskException {
        String jdbcUrl = "jdbc:postgresql://192.168.52.194:5432/dvdrental";
        String username = "postgres";
        String password = "postgres";

        Connection connection = null;
        PrintWriter writer = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            final String sqlQuery = "SELECT title, length, "
                    + "CASE "
                    + "WHEN length <= 60 THEN 'short' "
                    + "WHEN length > 60 AND length <= 120 THEN 'long' "
                    + "WHEN length > 120 THEN 'very long' "
                    + "ELSE 'unknown' "
                    + "END AS length_description "
                    + "FROM film;";
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(sqlQuery);
            long rowsReturned = 0;

            writer = new PrintWriter(new FileWriter("/tmp/PGQueryTaskB.out", false));

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int length = resultSet.getInt("length");
                String lengthDescription = resultSet.getString("length_description");
                writer.println("Title: " + title + ", Length: " + length + ", Length Description: " + lengthDescription);
                rowsReturned++;
            }
            logger.info("Wrote " + rowsReturned + " rows to the file");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) { connection.close(); }
                if (writer != null) { writer.close(); }
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.Periodic(5_000);
    }
}
