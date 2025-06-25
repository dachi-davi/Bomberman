package com.example.bomberman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class LogIn {
    public static String currentUser;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    Button SignUp;
    @FXML
    Button LogIn;
    @FXML
    Label incorrect;

    @FXML
    private void logIn(ActionEvent e) {

        if (Objects.equals(username.getText(), "") || Objects.equals(password.getText(), "")) {
            incorrect.setText("Enter your username and password");
            return;
        }
        try {
            Statement statement = HelloApplication.connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from Users " +
                    "where username = \"" + username.getText() + "\"");

            if (resultSet.next()){
                if (Objects.equals(username.getText(), resultSet.getString(1)) && Objects.equals(password.getText(), resultSet.getString(2))){
                    currentUser = username.getText();
                    switchToStartScreen(e);
                }else {
                    incorrect.setText("your username or password is incorrect");
                }
            }else incorrect.setText("your username or password is incorrect");

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void switchToSignUp(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToStartScreen(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        SoundManager.playTitleScreenTheme();
    }
}
