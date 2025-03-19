package org.example.campuswallet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginController {

    // Admin credentials (for now hardcoded)
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    @FXML
    private Button LoginAsAdmin;

    @FXML
    private TextField usernameField; // Assuming this is meant to collect 'name' field


    @FXML
    private PasswordField passwordField;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/campuswallet";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sayad@2024!";

    public void initialize() {
        // Event handler for "Login as Admin"
        LoginAsAdmin.setOnAction(event -> handleAdminLogin());
    }
@FXML
    private void handleAdminLogin() {
        // Prompt for Admin username and password
        TextInputDialog usernameDialog = new TextInputDialog();
        usernameDialog.setTitle("Admin Login");
        usernameDialog.setHeaderText("Please enter the admin username:");

        Optional<String> usernameResult = usernameDialog.showAndWait();
        if (usernameResult.isPresent()) {
            String username = usernameResult.get();

            // Prompt for password
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Enter password");

            Alert passwordDialog = new Alert(Alert.AlertType.CONFIRMATION);
            passwordDialog.setTitle("Admin Login");
            passwordDialog.setHeaderText("Please enter the admin password:");
            passwordDialog.getDialogPane().setContent(passwordField);
            passwordDialog.showAndWait().ifPresent(response -> {
                String password = passwordField.getText();

                // Check if credentials match
                if (isValidAdmin(username, password)) {
                    // Successfully authenticated, switch to admin interface
                    switchToAdminInterface();
                } else {
                    // Show error if credentials are incorrect
                    showError("Invalid Admin Credentials");
                }
            });
        }
    }

    private boolean isValidAdmin(String username, String password) {
        // Hardcoded admin credentials for simplicity
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    private void switchToAdminInterface() {
        // Load the Admin interface page (AdminDashboard.fxml)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
            Parent root = loader.load();

            // Get the current stage and change the scene
            Stage stage = (Stage) LoginAsAdmin.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleLogin(ActionEvent event) {
        String name = usernameField.getText(); // Use 'name' instead of 'username'
        String password = passwordField.getText();

        if (isValidCredentials(name, password)) {
            // Login successful
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + name + "!");

            // Load the interface.fxml file
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/campuswallet/interface.fxml"));
                Parent root = loader.load();

                // Set the controller for the interface.fxml file
                InterfaceController interfaceController = loader.getController();
                // Pass name to interfaceController if needed
                interfaceController.initialize(name);

                // Show the interface scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception
            }
        } else {
            // Login failed
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid name or password.");
        }
    }

    private boolean isValidCredentials(String name, String password) {
        boolean isValid = false;

        String query = "SELECT password FROM users WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (storedPassword.equals(password)) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        return isValid;
    }

    @FXML
    private void handleCreateAccountButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/campuswallet/CreateAccount.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Utility method to show alert dialog
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
