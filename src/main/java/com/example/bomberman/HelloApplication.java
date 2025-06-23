package com.example.bomberman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class HelloApplication extends Application {
    public static String url = "jdbc:mysql://localhost:3306/sys";
    public static String Username = "root";
    public static String Password = "password";
    public static Connection connection;
    static {
        try {
            connection = DriverManager.getConnection(url, Username, Password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Statement statement = connection.createStatement();
            statement.execute("create database if not exists BombermanDB");
            statement.execute("use BombermanDB");

            statement.execute("create table if not exists Users (" +
                    "username varchar(30) primary key," +
                    "password varchar(30)," +
                    "score1 int," +
                    "score2 int," +
                    "score3 int " +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Parent root = FXMLLoader.load(HelloApplication.class.getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

