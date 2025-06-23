package com.example.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class Level3 implements Initializable {
    @FXML
    AnchorPane root;
    @FXML
    Group wallGroup;
    @FXML
    Group enemyGroup;
    @FXML
    Label score;
    @FXML
    Button levelSelectButton;
    @FXML
    Enemy1 enemy1_1;
    @FXML
    Enemy1 enemy1_2;
    @FXML
    Enemy1 enemy1_3;
    @FXML
    Enemy1 enemy1_4;
    @FXML
    Enemy2Hunter enemy2_1;
    @FXML
    Enemy2Hunter enemy2_2;
    @FXML
    Enemy4BombEater enemy4_1;
    @FXML
    Enemy4BombEater enemy4_2;
    @FXML
    Enemy4BombEater enemy4_3;
    @FXML
    Enemy4BombEater enemy4_4;


    Timeline essentials;
    Player player;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> root.requestFocus());
        Set<KeyCode> pressedKeys = new HashSet<>();

        essentials = new Timeline(new KeyFrame(Duration.seconds(0.2), e -> {
            score.setText("Score: " + ScoreManager.getScore());
            if (enemyGroup.getChildren().isEmpty()) {
                switchToVictoryScreen();
                essentials.stop();
            }
        }));
        essentials.setCycleCount(Timeline.INDEFINITE);
        essentials.play();

        player = new Player(wallGroup);
        player.setCenterX(78);
        player.setCenterY(78);
        root.getChildren().add(player);
        Enemy.player = player;

        startEnemy(enemy1_1);
        startEnemy(enemy1_2);
        startEnemy(enemy1_3);
        startEnemy(enemy1_4);
        startEnemy(enemy2_1);
        startEnemy(enemy2_2);
        startEnemy(enemy4_1);
        startEnemy(enemy4_2);
        startEnemy(enemy4_3);
        startEnemy(enemy4_4);

        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE && !pressedKeys.contains(KeyCode.SPACE) && Bomb.bombAmount < Bomb.bombMax) {
                Bomb bomb = new Bomb(player, enemyGroup, wallGroup, root);
                Bomb.bombAmount++;
                root.getChildren().add(bomb);
            }
            pressedKeys.add(e.getCode());
        });
        root.setOnKeyReleased(e -> {
            pressedKeys.remove(e.getCode());
        });

        Timeline movePlayer = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            player.move(pressedKeys);
        }));
        movePlayer.setCycleCount(Timeline.INDEFINITE);
        movePlayer.play();
    }

    public void switchToVictoryScreen() {
        stopEnemies();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            try {
                VictoryScreen.levelNumber = "3";
                Parent root = FXMLLoader.load(getClass().getResource("VictoryScreen.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) enemyGroup.getScene().getWindow();
                if (stage != null) {
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));
        timeline.play();
    }

    public void startEnemy(Enemy enemy) {
        enemy.setPlayer(player);
        enemy.setWallGroup(wallGroup);
        enemy.setImage();
        enemy.move();
    }

    @FXML
    private void switchToLevelSelect(ActionEvent e) throws IOException {
        stopEnemies();
        Parent root = FXMLLoader.load(getClass().getResource("LevelSelect.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void stopEnemies() {
        for (Node node : enemyGroup.getChildren()) {
            if (node instanceof Enemy) if (((Enemy) node).timeline != null) ((Enemy) node).timeline.stop();
        }
    }
}
