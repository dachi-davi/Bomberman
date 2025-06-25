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

public class SignUp {
    @FXML
    Button SignUp;
    @FXML
    Button LogInButton;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    Label incorrect;

    @FXML
    private void signUp(ActionEvent e){
        if (Objects.equals(username.getText(), "") || Objects.equals(password.getText(), "")) {
            incorrect.setText("Enter username and password");
            return;
        }

        try {
            Statement statement = HelloApplication.connection.createStatement();
            statement.execute("use BombermanDB");

            ResultSet resultSet = statement.executeQuery("select * from Users " +
                    "where username = \"" + username.getText() + "\"");
            if (resultSet.next()){
                incorrect.setText("This username already exists");
                return;
            }
            statement.execute("insert into users (username,password) " +
                    "value (\"" + username.getText() + "\", \"" + password.getText() + "\")");
            LogIn.currentUser = username.getText();
            switchToStartScreen(e);
        }catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
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
    @FXML
    private void switchToLogIn(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
