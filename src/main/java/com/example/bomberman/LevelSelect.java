package com.example.bomberman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LevelSelect {
    @FXML
    Button back;
    @FXML
    Button level1;

    @FXML
    private void switchToStartScreen(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void switchTolevel1(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Level1.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchTolevel2(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Level2.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchTolevel3(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Level3.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
