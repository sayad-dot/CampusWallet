package org.example.campuswallet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class UserDetailsController {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> mailColumn;
    @FXML
    private TableColumn<User, String> phoneColumn;
    @FXML
    private TableColumn<User, String> dobColumn;
    @FXML
    private TableColumn<User, Double> balanceColumn;

    private UserDetailsDao userDetailsDao;

    public void initialize() {
        // Initialize DAO
        userDetailsDao = new UserDetailsDao();

        // Set up the columns with the corresponding properties from the User class
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Load user details into the table
        loadUserDetails();
    }

    private void loadUserDetails() {
        List<User> users = userDetailsDao.getAllUsers();
        ObservableList<User> userData = FXCollections.observableArrayList(users);
        userTable.setItems(userData);
    }
}

