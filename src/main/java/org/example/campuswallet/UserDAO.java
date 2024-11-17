package org.example.campuswallet;

import java.sql.*;
import java.text.SimpleDateFormat;

public class UserDAO {
    private Connection connection;

    // Constructor to initialize the connection
    public UserDAO() {
        this.connection = DatabaseConnection.getConnection();  // Assuming DatabaseConnection is a utility class to manage DB connections
    }

    // Helper method to format date from DD/MM/YYYY to java.sql.Date
    private java.sql.Date formatDate(String date) throws SQLException {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date parsedDate = inputFormat.parse(date);
            return new java.sql.Date(parsedDate.getTime());  // Convert to java.sql.Date
        } catch (Exception e) {
            throw new SQLException("Invalid date format, expected DD/MM/YYYY");
        }
    }

    // Method to create a new user
    public boolean createUser(User user) {
        String query = "INSERT INTO users (id, name, password, email, dob, phone) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());

            // Set the date as java.sql.Date
            preparedStatement.setDate(5, formatDate(user.getDob()));
            preparedStatement.setString(6, user.getPhone());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update user details
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, password = ?, email = ?, dob = ?, phone = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());

            // Set the date as java.sql.Date
            statement.setDate(4, formatDate(user.getDob()));  // Ensure date is formatted correctly
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get user by id
    public User getUserById(String userId) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);  // Helper method to map result set to User object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to delete user by id
    public boolean deleteUser(String userId) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to map result set to User object
    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");
        java.sql.Date dob = resultSet.getDate("dob");
        String phone = resultSet.getString("phone");

        // Convert java.sql.Date to java.util.Date (if necessary)
        java.util.Date utilDate = new java.util.Date(dob.getTime());

        return new User(id, name, password, email, new SimpleDateFormat("dd/MM/yyyy").format(utilDate), phone);
    }
}
