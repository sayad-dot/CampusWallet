package org.example.campuswallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsDao {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/campuswallet";
    private static final String USER = "root";
    private static final String PASSWORD = "Sayad@2024!";

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, password, mail, phone, dob, balance FROM users"; // Update table/column names as necessary

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                users.add(new User(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("mail"),
                        rs.getString("phone"),
                        rs.getString("dob")

                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
