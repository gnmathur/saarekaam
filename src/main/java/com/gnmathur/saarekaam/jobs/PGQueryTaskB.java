package com.gnmathur.saarekaam.jobs;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.SKTask;
import com.gnmathur.saarekaam.core.SKTaskException;
import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PGQueryTaskB implements SKTask {
    private static final Logger logger = SKLogger.getLogger(PGQueryTaskB.class);

    @Override
    public void execute() throws SKTaskException {
        String jdbcUrl = "jdbc:postgresql://192.168.52.194:5432/dvdrental";
        String username = "postgres";
        String password = "postgres";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            String sqlQuery = "SELECT title, length, "
                    + "CASE "
                    + "WHEN length <= 60 THEN 'short' "
                    + "WHEN length > 60 AND length <= 120 THEN 'long' "
                    + "WHEN length > 120 THEN 'very long' "
                    + "ELSE 'unknown' "
                    + "END AS length_description "
                    + "FROM film;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int length = resultSet.getInt("length");
                String lengthDescription = resultSet.getString("length_description");
                logger.info("Title: " + title + ", Length: " + length + ", Length Description: " + lengthDescription);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) { connection.close(); }
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.PeriodicTaskSchedulingPolicy(5_000);
    }
}
