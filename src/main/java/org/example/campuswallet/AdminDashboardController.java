package org.example.campuswallet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {

    @FXML
    private void handleAuditLog(ActionEvent event) {
        try {
            // Load the AuditLog.fxml scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/campuswallet/AuditLog.fxml"));
            Parent root = loader.load();

            // Optionally, you can set up a controller if specific initialization is needed
            AuditLogController auditLogController = loader.getController();

            // Set the scene and show the AuditLog page
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Audit Logs");  // Set the title for the Audit Log page
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception
        }
    }
}
