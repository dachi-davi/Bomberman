package com.example.bomberman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Options implements Initializable {
    @FXML
    Button back;
    @FXML
    Button LogOut;
    @FXML
    Slider soundVolumeSlider;
    @FXML
    Slider sfxVolumeSlider;
    @FXML
    AnchorPane root;

    private double volume = SoundManager.getVolume();
    private double svolume = SoundManager.getSfxVolume();

    @FXML
    private void switchToStartScreen(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToLogIn(ActionEvent e) throws IOException {
        SoundManager.getThemePlayer().stop();
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sfxVolumeSlider.setValue(svolume);
        soundVolumeSlider.setValue(volume);

        soundVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            volume = (double) Math.round(newVal.doubleValue() * 100) /100;
            SoundManager.setVolume(volume);
            if (SoundManager.getThemePlayer()!=null)
                if (SoundManager.getThemePlayer().getStatus() == MediaPlayer.Status.PLAYING)
                    SoundManager.getThemePlayer().setVolume(volume);
        });

        sfxVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            svolume = (double) Math.round(newVal.doubleValue() * 100) /100;
            SoundManager.setSfxVolume(svolume);
        });

    }
}
