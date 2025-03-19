package org.example.campuswallet;

import javafx.beans.property.*;

public class AuditLog {

    private final IntegerProperty id;
    private final IntegerProperty userId;
    private final StringProperty action;
    private final StringProperty details;
    private final StringProperty timestamp;

    public AuditLog(int id, int userId, String action, String details, String timestamp) {
        this.id = new SimpleIntegerProperty(id);
        this.userId = new SimpleIntegerProperty(userId);
        this.action = new SimpleStringProperty(action);
        this.details = new SimpleStringProperty(details);
        this.timestamp = new SimpleStringProperty(timestamp);
    }

    // Getters and setters for each property
    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public StringProperty actionProperty() {
        return action;
    }

    public StringProperty detailsProperty() {
        return details;
    }

    public StringProperty timestampProperty() {
        return timestamp;
    }
}

