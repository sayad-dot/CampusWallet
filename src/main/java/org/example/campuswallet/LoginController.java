package org.example.campuswallet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField; // Assuming this is meant to collect 'name' field

    @FXML
    private PasswordField passwordField;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/campuswallet";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "$@Y@d012";

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
