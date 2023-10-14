package com.gnmathur.saarekaam.jobs;

import com.gnmathur.saarekaam.core.SKLogger;
import com.gnmathur.saarekaam.core.SKTask;
import com.gnmathur.saarekaam.core.SKTaskException;
import com.gnmathur.saarekaam.core.SKTaskSchedulingPolicy;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PGQueryTaskA implements SKTask {
    private static final Logger logger = SKLogger.getLogger(PGQueryTaskA.class);

    @Override
    public void execute() throws SKTaskException {
        String jdbcUrl = "jdbc:postgresql://192.168.52.194:5432/dvdrental";
        String username = "postgres";
        String password = "postgres";

        Connection connection = null;

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

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                int id = resultSet.getInt("customer_id");
                String name = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                String favoriteStore = resultSet.getString("Favorite Store");
                logger.info("ID: " + id + ", Name: " + name + ", Favorite Store: " + favoriteStore);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public SKTaskSchedulingPolicy policy() {
        return new SKTaskSchedulingPolicy.PeriodicTaskSchedulingPolicy(5_000);
    }
}
