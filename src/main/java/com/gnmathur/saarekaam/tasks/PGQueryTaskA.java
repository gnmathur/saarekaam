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

public class PGQueryTaskA implements SKTask {
    private static final Logger logger = SKLogger.getLogger(PGQueryTaskA.class);

    @Override
    public void execute() throws SKTaskException {
        final String jdbcUrl = "jdbc:postgresql://192.168.52.194:5432/dvdrental";
        final String username = "postgres";
        final String password = "postgres";

        Connection connection = null;
        PrintWriter writer = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            String sqlQuery =
                    "SELECT "
                        + "c.customer_id, "
                        + "c.first_name, "
                        + "c.last_name, "
                        + "(SELECT i.store_id "
                            + "FROM rental r2 "
                            + "INNER JOIN inventory i "
                                + "ON r2.inventory_id = i.inventory_id "
                            + "WHERE r2.customer_id = c.customer_id "
                            + "GROUP BY i.store_id "
                            + "ORDER BY count(*) DESC "
                            + "LIMIT 1) AS \"Favorite Store\" "
                    + "FROM customer c";

            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(sqlQuery);
            long rowsReturned = 0;

            writer = new PrintWriter(new FileWriter("/tmp/PGQueryTaskA.out", false));

            while (resultSet.next()) {
                int id = resultSet.getInt("customer_id");
                String name = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                String favoriteStore = resultSet.getString("Favorite Store");
                writer.println("ID: " + id + ", Name: " + name + ", Favorite Store: " + favoriteStore);
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
                if (connection != null) {
                    connection.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.Periodic(5_000);
    }
}
