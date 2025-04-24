package org.example.campuswallet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransactionController {

    @FXML private TextField accountNumberField;
    @FXML private TextField amountField;
    @FXML private PasswordField passwordField;
    @FXML private Button backButton;

    private static final String DB_URL      = "jdbc:mysql://localhost:3306/campuswallet";
    private static final String DB_USER     = "root";
    private static final String DB_PASSWORD = "Sayad@2024!";

    private String username;
    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    private void handleSendMoney() {
        String receiverId = accountNumberField.getText();
        String amtText    = amountField.getText();
        String pwd        = passwordField.getText();

        if (receiverId.isEmpty() || amtText.isEmpty() || pwd.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Please fill in all fields.");
            return;
        }
        double amount;
        try { amount = Double.parseDouble(amtText); }
        catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Invalid amount.");
            return;
        }

        if (amount <= 0) {
            showAlert(Alert.AlertType.ERROR,
                    "Transaction Failed",
                    "Please enter an amount greater than zero.");
            return;
        }

        if (!authenticateUser(username, pwd)) {
            showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Invalid password.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            TransactionDAO dao = new TransactionDAO(conn);
            String senderId = getUserIdByUsername(conn, username);
            if (senderId == null) {
                showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Sender account not found.");
                return;
            }

            boolean ok = dao.transferMoney(senderId, receiverId, amount);
            if (ok) {
                showAlert(Alert.AlertType.INFORMATION, "Transaction Successful", "Amount transferred successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Insufficient balance or receiver not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Transaction Failed", "Database error occurred.");
        }
    }

    @FXML
    private void handleBackButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fetch user ID by username
    private String getUserIdByUsername(Connection conn, String username) throws SQLException {
        String sql = "SELECT ID FROM users WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("ID");
                }
            }
        }
        return null;
    }

    // Authenticate user by username & password
    private boolean authenticateUser(String username, String password) {
        String sql = "SELECT password FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password").equals(password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Utility to show JavaFX alerts
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
