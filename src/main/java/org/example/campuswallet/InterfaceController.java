package org.example.campuswallet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;


import java.io.IOException;

public class InterfaceController {
    private String username;

    public void initialize(String username){
        this.username=username;

    }
    private UserDAO userDAO;
    public InterfaceController() {
        // No initialization needed here, but you can add any setup logic if required
    }

    // Constructor injection for UserDAO
    public InterfaceController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }



    @FXML
    private void handleAccountDetails(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/campuswallet/AccountDetails.fxml"));
            Parent root = loader.load();

            AccountDetailsController accountDetailsController = loader.getController();
            accountDetailsController.initialize(username);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception
        }
    }




    @FXML
    private void handleSendMoney(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/campuswallet/Transaction.fxml"));
            Parent root = loader.load();

            TransactionController sendMoneyController = loader.getController();
            sendMoneyController.setUsername(username);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception
        }
    }

    @FXML
    private void handleAddMoney(ActionEvent event) {
        loadTransactionPage(event);
    }
    @FXML
    public void handleCurrencyConverter(ActionEvent event) {
        try {
            // Load Currency Converter scene
            Parent root = FXMLLoader.load(getClass().getResource("CurrencyConverter.fxml"));

            // Use the current Stage (window) to set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Currency Converter");  // Set the title of the stage
            Scene scene = new Scene(root);

            // Set preferred width and height for the new window
            scene.getRoot().requestLayout(); // Ensures scene properly lays out its content
            stage.setWidth(800);   // Set width to 800px
            stage.setHeight(600);  // Set height to 600px

            stage.setScene(scene);  // Set the updated scene with the proper layout
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLoanCalculator(ActionEvent event) {
        try {
            // Load Loan Calculator scene
            Parent root = FXMLLoader.load(getClass().getResource("LoanCalculator.fxml"));

            // Use the current Stage (window) to set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Loan Calculator");  // Set the title of the stage
            Scene scene = new Scene(root);

            // Set preferred width and height for the new window
            scene.getRoot().requestLayout(); // Ensures scene properly lays out its content
            stage.setWidth(800);   // Set width to 800px
            stage.setHeight(600);  // Set height to 600px

            stage.setScene(scene);  // Set the updated scene with the proper layout
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleAuditLog(ActionEvent event) {
        try {
            // Load Audit Log scene
            Parent root = FXMLLoader.load(getClass().getResource("AuditLog.fxml"));

            // Use the current Stage (window) to set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Audit Log");  // Set the title of the stage
            Scene scene = new Scene(root);

            // Set preferred width and height for the new window
            scene.getRoot().requestLayout(); // Ensures scene properly lays out its content
            stage.setWidth(800);   // Set width to 600px
            stage.setHeight(600);  // Set height to 600px

            stage.setScene(scene);  // Set the updated scene with the proper layout
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    @FXML
    private void showAccountDetails() {
        // Fetch account details from the backend (replace this with your actual logic)
        String accountDetails = fetchAccountDetails();

        // Show account details in a dialog
        showAlert(Alert.AlertType.INFORMATION, "Account Details", accountDetails);
    }

    private boolean hasSufficientFunds(double amount) {
        // Replace this with your actual logic to check if sender has sufficient funds
        return true;
    }

    private void updateSenderBalance(double amount) {
        // Replace this with your actual logic to update sender's account balance
    }

    private void updateRecipientBalance(double amount, String recipientAccount) {
        // Replace this with your actual logic to update recipient's account balance
    }

    private void updateAccountBalance(double amount) {
        // Replace this with your actual logic to update user's account balance
    }

    private String fetchAccountDetails() {
        // Replace this with your actual logic to fetch account details from the backend
        return "Account Name: John Doe\nAccount Balance: $1000";
    }

    private void loadTransactionPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/campuswallet/transaction.fxml"));
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

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
