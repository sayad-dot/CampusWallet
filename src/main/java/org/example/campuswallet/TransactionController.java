package org.example.campuswallet;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class TransactionController {

    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField amountField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button backButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/campuswallet";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sayad@2024!";

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    private void handleSendMoney() {
        String receiverId = accountNumberField.getText(); // Assuming accountNumberField contains the receiver's ID
        String amountStr = amountField.getText();
        String password = passwordField.getText();

        if (receiverId.isEmpty() || amountStr.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Please fill in all fields.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Invalid amount.");
            return;
        }

        if (authenticateUser(username, password)) { // Assuming 'username' is already available in the controller
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                // Create a TransactionDAO instance with the current connection
                TransactionDAO transactionDAO = new TransactionDAO(connection);

                // Fetch sender's ID using the username
                String senderId = getUserIdByUsername(connection, username);
                if (senderId == null) {
                    showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Sender account not found.");
                    return;
                }

                // Call transferMoney method from the TransactionDAO
                boolean success = transactionDAO.transferMoney(connection, senderId, receiverId, amount);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Transaction Successful", "Amount transferred successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Unable to transfer amount. Please check the account details.");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Database error occurred.");
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Invalid password.");
        }
    }


    // Helper method to fetch user ID by username
    private String getUserIdByUsername(Connection connection, String username) throws SQLException {
        String query = "SELECT ID FROM users WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("ID");
                }
            }
        }
        return null; // Return null if no user found
    }

    @FXML
    private void handleBackButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interface.fxml")); // Adjust the path to the previous page's FXML
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean authenticateUser(String username, String password) {
        String query = "SELECT password FROM users WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return storedPassword.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean transferAmount(String fromUser, String toAccount, double amount) {
        String getBalanceQuery = "SELECT balance FROM users WHERE name = ?";
        String updateBalanceQuery = "UPDATE users SET balance = ? WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Check if sender has enough balance
            double senderBalance = 0;
            try (PreparedStatement getBalanceStmt = connection.prepareStatement(getBalanceQuery)) {
                getBalanceStmt.setString(1, fromUser);
                ResultSet resultSet = getBalanceStmt.executeQuery();
                if (resultSet.next()) {
                    senderBalance = resultSet.getDouble("balance");
                    if (senderBalance < amount) {
                        showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Insufficient balance.");
                        return false;
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Sender account not found.");
                    return false;
                }
            }

            // Deduct amount from sender's account
            try (PreparedStatement updateBalanceStmt = connection.prepareStatement(updateBalanceQuery)) {
                updateBalanceStmt.setDouble(1, senderBalance - amount);
                updateBalanceStmt.setString(2, fromUser);
                updateBalanceStmt.executeUpdate();
            }

            // Add amount to receiver's account
            double receiverBalance = 0;
            String getReceiverBalanceQuery = "SELECT balance FROM users WHERE id = ?";
            String updateReceiverBalanceQuery = "UPDATE users SET balance = ? WHERE id = ?";

            try (PreparedStatement getReceiverBalanceStmt = connection.prepareStatement(getReceiverBalanceQuery)) {
                getReceiverBalanceStmt.setString(1, toAccount);
                ResultSet resultSet = getReceiverBalanceStmt.executeQuery();
                if (resultSet.next()) {
                    receiverBalance = resultSet.getDouble("balance");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Receiver account not found.");
                    return false;
                }
            }

            try (PreparedStatement updateReceiverBalanceStmt = connection.prepareStatement(updateReceiverBalanceQuery)) {
                updateReceiverBalanceStmt.setDouble(1, receiverBalance + amount);
                updateReceiverBalanceStmt.setString(2, toAccount);
                updateReceiverBalanceStmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
