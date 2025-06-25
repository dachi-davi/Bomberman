package com.example.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class GameOver implements Initializable {
    @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SoundManager.playGameOver();
        SoundManager.getThemePlayer().stop();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e->{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("LevelSelect.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) anchorPane.getScene().getWindow();
                if (stage!=null){
                    stage.setScene(scene);
                    stage.show();
                }
                SoundManager.playTitleScreenTheme();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));
        timeline.play();
    }
}
