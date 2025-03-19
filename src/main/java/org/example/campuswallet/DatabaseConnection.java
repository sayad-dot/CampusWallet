package org.example.campuswallet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/campuswallet";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Sayad@2024!";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the connection
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void initializeDatabase() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();

            // Prepare SQL statement to create users table if not exists
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255))";
            preparedStatement = connection.prepareStatement(createTableQuery);

            // Execute the create table statement
            preparedStatement.executeUpdate();

            // Insert sample user data
            String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, "sayad");
            preparedStatement.setString(2, "sayad");
            preparedStatement.executeUpdate();

            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
