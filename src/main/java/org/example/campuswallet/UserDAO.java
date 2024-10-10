package org.example.campuswallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean createUser(User user) {
        String query = "INSERT INTO users (id, name, password, email, dob, phone) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getDob());
            preparedStatement.setString(6, user.getPhone());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("dob"),
                            resultSet.getString("phone")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(new User(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("dob"),
                            resultSet.getString("phone")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET  name = ?,password = ?, email = ?, dob = ?, phone = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(2, user.getName());
            statement.setString(1, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getDob());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(String id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
