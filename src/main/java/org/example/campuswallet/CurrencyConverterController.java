package org.example.campuswallet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;



    public class CurrencyConverterController {
        @FXML private TextField amountField;
        @FXML private ComboBox<String> fromCurrencyComboBox;
        @FXML private ComboBox<String> toCurrencyComboBox;
        @FXML private Label resultLabel;

        @FXML
        private Button backButton;

        @FXML
        private void handleConvertCurrency() {
            double amount = Double.parseDouble(amountField.getText());
            String fromCurrency = fromCurrencyComboBox.getValue();  // Get selected value
            String toCurrency = toCurrencyComboBox.getValue();      // Get selected value
            double convertedAmount = convertCurrency(amount, fromCurrency, toCurrency);
            resultLabel.setText(String.format("Converted Amount: %.2f", convertedAmount));
        }

        private double convertCurrency(double amount, String fromCurrency, String toCurrency) {
            try {
                String urlStr = String.format("https://api.exchangerate-api.com/v4/latest/%s", fromCurrency);
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                StringBuilder jsonStr = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonStr.append(line);
                    }
                }

                double exchangeRate = parseExchangeRate(jsonStr.toString(), toCurrency);
                return amount * exchangeRate;
            } catch (IOException e) {
                e.printStackTrace();
                return 0.0;
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


    private double parseExchangeRate(String jsonStr, String toCurrency) {
        String ratesKey = "\"rates\"";
        int ratesIndex = jsonStr.indexOf(ratesKey);
        if (ratesIndex != -1) {
            int startIndex = jsonStr.indexOf("{", ratesIndex);
            int endIndex = jsonStr.indexOf("}", startIndex);
            if (startIndex != -1 && endIndex != -1) {
                String ratesJson = jsonStr.substring(startIndex, endIndex + 1);
                String toCurrencyKey = "\"" + toCurrency + "\"";
                int toCurrencyIndex = ratesJson.indexOf(toCurrencyKey);
                if (toCurrencyIndex != -1) {
                    int colonIndex = ratesJson.indexOf(":", toCurrencyIndex);
                    if (colonIndex != -1) {
                        int commaIndex = ratesJson.indexOf(",", colonIndex);
                        if (commaIndex != -1) {
                            String exchangeRateStr = ratesJson.substring(colonIndex + 1, commaIndex).trim();
                            return Double.parseDouble(exchangeRateStr);
                        }
                    }
                }
            }
        }
        return 0.0;
    }
}
