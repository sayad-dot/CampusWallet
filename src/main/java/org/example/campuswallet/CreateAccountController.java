package org.example.campuswallet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.IOException;
import javafx.scene.control.Button;

public class CreateAccountController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField dobField;



    @FXML
    private TextField phoneField;

    @FXML
    private Button backButton;

    private UserDAO userDAO;

    public CreateAccountController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public CreateAccountController() {

        this.userDAO = new UserDAO();

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


    @FXML
    private void handleSubmitButton() {
        String id = idField.getText();
        String name = nameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String dob = dobField.getText();
        String phone = phoneField.getText();
        // Perform validation if needed

        // Create a new User object with the provided information
        User newUser = new User(id,name,password,dob,email,phone);

        // Call the createUser method of the UserDAO to insert the new user into the database
        boolean createdSuccessfully = userDAO.createUser(newUser);

        // Show alert based on the result
        if (createdSuccessfully) {
            showAlert(Alert.AlertType.INFORMATION, "Account Creation", "Account created successfully.");

            // Close the current stage (window)
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Account Creation Failed", "Failed to create account. Please try again.");
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
