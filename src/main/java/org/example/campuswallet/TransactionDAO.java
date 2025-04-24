package org.example.campuswallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class TransactionDAO {
    private Connection connection;

    public TransactionDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean transferMoney(String senderId, String receiverId, double amount) throws SQLException {
        connection.setAutoCommit(false);
        try {
            // 1) Withdraw
            if (!updateAccountBalance(senderId, -amount)) {
                connection.rollback();
                return false; // insufficient funds or error
            }
            // 2) Deposit
            if (!updateAccountBalance(receiverId, amount)) {
                connection.rollback();
                return false; // receiver error
            }
            // 3) Record the transaction
            addTransaction(senderId, receiverId, amount);

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private double getBalance(String userId) throws SQLException {
        String sql = "SELECT balance FROM users WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                } else {
                    throw new SQLException("Account not found: " + userId);
                }
            }
        }
    }

    private boolean updateAccountBalance(String userId, double delta) throws SQLException {
        // If this is a withdrawal, ensure no overdraft
        if (delta < 0) {
            double current = getBalance(userId);
            if (current + delta < 0) {
                return false; // insufficient funds
            }
        }
        // Perform the update
        String sql = "UPDATE users SET balance = balance + ? WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, delta);
            ps.setString(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    private void addTransaction(String senderId, String receiverId, double amount) throws SQLException {
        String sql = "INSERT INTO transactions (sender_id, receiver_id, amount, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, senderId);
            ps.setString(2, receiverId);
            ps.setDouble(3, amount);
            ps.setTimestamp(4, new Timestamp(new Date().getTime()));
            ps.executeUpdate();
        }
    }
}
