package org.example.campuswallet;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TransactionDAO {
    private Connection connection;

    public TransactionDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean transferMoney(Connection connection, String senderId, String receiverId, double amount) throws SQLException {
        connection.setAutoCommit(false); // Start a transaction

        try {
            // Step 1: Deduct amount from sender's account
            if (!updateAccountBalance(connection, senderId, -amount)) {
                connection.rollback();
                return false; // Insufficient funds or other failure
            }

            // Step 2: Add amount to receiver's account
            if (!updateAccountBalance(connection, receiverId, amount)) {
                connection.rollback();
                return false; // Receiver account error
            }

            // Step 3: Record the transaction
            addTransaction(connection, senderId, receiverId, amount);

            // Commit the transaction
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback(); // Rollback if any error occurs
            throw e; // Re-throw to propagate exception
        } finally {
            connection.setAutoCommit(true); // Restore default auto-commit behavior
        }
    }

    private boolean updateAccountBalance(Connection connection, String userId, double amount) throws SQLException {
        String query = "UPDATE users SET balance = balance + ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if rows are affected
        }
    }

    private void addTransaction(Connection connection, String senderId, String receiverId, double amount) throws SQLException {
        String query = "INSERT INTO transactions (sender_id, receiver_id, amount, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, senderId);
            preparedStatement.setString(2, receiverId);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
            preparedStatement.executeUpdate();
        }
    }




    public boolean authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if user is found, false otherwise
            }
        }
    }


}