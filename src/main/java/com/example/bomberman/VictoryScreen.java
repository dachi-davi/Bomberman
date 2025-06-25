package com.example.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class VictoryScreen implements Initializable {
    @FXML
    Label scoreLabel;

    public static String levelNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SoundManager.getThemePlayer().stop();
        SoundManager.playStageClear();
        scoreLabel.setText("Your Score Was: " + ScoreManager.getScore());
        try {
            Statement statement = HelloApplication.connection.createStatement();
            int bestScore = 0;

            ResultSet resultSet = statement.executeQuery("Select score" + levelNumber + " from Users " +
                    "where username = \"" + LogIn.currentUser + "\"");
            if (resultSet.next())bestScore = resultSet.getInt("score" + levelNumber);
            if (bestScore <= ScoreManager.getScore())
                statement.execute("update Users " +
                        "set score" + levelNumber + " = " + ScoreManager.getScore() + " " +
                        "where username = \"" + LogIn.currentUser +"\"");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ScoreManager.resetScore();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e->{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("LevelSelect.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) scoreLabel.getScene().getWindow();
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
