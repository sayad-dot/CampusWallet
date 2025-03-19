package org.example.campuswallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/campuswallet";
    private static final String USER = "root";
    private static final String PASSWORD = "Sayad@2024!";

    // Change return type to List<AuditLog>
    public List<AuditLog> getAuditLogs() {
        List<AuditLog> logs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs ORDER BY timestamp DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                logs.add(new AuditLog(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("action"),
                        rs.getString("details"),
                        rs.getString("timestamp")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

}

