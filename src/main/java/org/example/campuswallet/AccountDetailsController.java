package org.example.campuswallet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDetailsController {

    @FXML
    private Text nameText;

    @FXML
    private Text idText;

    @FXML
    private Text passwordText;

    @FXML
    private Text emailText;

    @FXML
    private Text dobText;

    @FXML
    private Text phoneText;

    @FXML
    private Text balanceText;

    @FXML
    private Button backButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/campuswallet";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sayad@2024!";

    private String username;

    public void initialize(String username) {
        this.username = username;
        loadAccountDetails();
    }

    private void loadAccountDetails() {
        String query = "SELECT id, name, password, email, dob, phone,balance FROM users WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String dob = resultSet.getString("dob");
                String phone = resultSet.getString("phone");
                int balance=resultSet.getInt("balance");

                idText.setText("Id: " + id);
                nameText.setText("Name: " + name);
                passwordText.setText("Password: " + password);
                emailText.setText("Email: " + email);
                dobText.setText("Date of Birth: " + dob);
                phoneText.setText("Phone: " + phone);
                balanceText.setText("Balance: "+balance);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
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

}
