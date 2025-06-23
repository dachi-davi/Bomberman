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

public class StartScreen {
    @FXML
    Button start;
    @FXML
    Button leaderBoard;
    @FXML
    Button options;

    @FXML
    private void switchToOptions(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Options.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToLeaderboards(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LeaderBoard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToLevelSelect(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LevelSelect.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
