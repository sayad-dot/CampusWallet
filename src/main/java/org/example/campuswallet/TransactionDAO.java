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

    public void transferMoney(String senderUsername, String receiverUsername, double amount) throws SQLException {
        // Start a transaction
        connection.setAutoCommit(false);

        try {
            // Deduct amount from sender's account
            updateAccountBalance(senderUsername, -amount);

            // Add amount to receiver's account
            updateAccountBalance(receiverUsername, amount);

            // Add a new transaction record
            addTransaction(new Transaction(0, senderUsername, receiverUsername, amount, new Date()));

            // Commit the transaction if all statements are executed successfully
            connection.commit();
        } catch (SQLException e) {
            // Rollback the transaction if an error occurs
            connection.rollback();
            throw e;
        } finally {
            // Restore auto-commit mode
            connection.setAutoCommit(true);
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

    private void updateAccountBalance(String username, double amount) throws SQLException {
        String query = "UPDATE accounts SET balance = balance + ? WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        }
    }

    public void addTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (sender_username, receiver_username, amount, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, transaction.getSenderUsername());
            preparedStatement.setString(2, transaction.getReceiverUsername());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setTimestamp(4, new Timestamp(transaction.getTimestamp().getTime()));
            preparedStatement.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    transaction.setId(generatedId);
                }
            }
        }
    }
}