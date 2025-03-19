package org.example.campuswallet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class AuditLogController {

    @FXML
    private TableView<AuditLog> logTable;
    @FXML
    private TableColumn<AuditLog, Integer> columnId;
    @FXML
    private TableColumn<AuditLog, Integer> columnUserId;
    @FXML
    private TableColumn<AuditLog, String> columnAction;
    @FXML
    private TableColumn<AuditLog, String> columnDetails;
    @FXML
    private TableColumn<AuditLog, String> columnTimestamp;

    private AuditLogDAO auditLogDAO;

    public void initialize() {
        // Initialize DAO
        auditLogDAO = new AuditLogDAO();

        // Set up the columns
        columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        columnUserId.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
        columnAction.setCellValueFactory(cellData -> cellData.getValue().actionProperty());
        columnDetails.setCellValueFactory(cellData -> cellData.getValue().detailsProperty());
        columnTimestamp.setCellValueFactory(cellData -> cellData.getValue().timestampProperty());

        // Load audit logs into the TableView
        loadAuditLogs();
    }
    private void loadAuditLogs() {
        List<AuditLog> logs = auditLogDAO.getAuditLogs();  // Fetch logs from DAO
        ObservableList<AuditLog> auditLogData = FXCollections.observableArrayList();

        // Simply add the logs fetched from the DAO to the ObservableList
        auditLogData.addAll(logs);

        // Bind the data to the table
        logTable.setItems(auditLogData);
    }



}

