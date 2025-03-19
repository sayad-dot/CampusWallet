package org.example.campuswallet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoanCalculatorController {
    @FXML private TextField loanAmountField;
    @FXML private TextField interestRateField;
    @FXML private TextField loanTermField;
    @FXML private Label resultLabel;

    @FXML
    private Button backButton;

    @FXML
    public void handleCalculateLoan() {
        double loanAmount = Double.parseDouble(loanAmountField.getText());
        double annualInterestRate = Double.parseDouble(interestRateField.getText());
        int loanTerm = Integer.parseInt(loanTermField.getText());

        double monthlyInterestRate = annualInterestRate / 1200;
        int numberOfPayments = loanTerm * 12;
        double monthlyPayment = (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));

        resultLabel.setText(String.format("Monthly Payment: %.2f", monthlyPayment));
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
