package org.example.campuswallet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class   Main extends Application {
    private Connection connection;
    private TransactionDAO transactionDAO;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/campuswallet/login.fxml"));
        Parent root = loader.load();

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/campuswallet", "root", "Sayad@2024!");
        transactionDAO = new TransactionDAO(connection);


        // Set the scene
        primaryStage.setTitle("Banking Management System - Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
