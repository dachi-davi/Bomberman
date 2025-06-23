package com.example.bomberman;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public class Player extends Circle {
    private Group wallGroup;
    private boolean invincible;
    private double speed = 1;

    public Player(Group wallGroup) {
        Bomb.bombMax = 1;
        this.wallGroup = wallGroup;
        setRadius(22);
        setFill(Paint.valueOf("#bc7177"));
        Enemy.setPlayerKilled(false);
    }

    public void move(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.UP)) {
            setCenterY(getCenterY() - speed);
            if (intersectsWall()) setCenterY(getCenterY() + speed);
        }
        if (pressedKeys.contains(KeyCode.DOWN)) {
            setCenterY(getCenterY() + speed);
            if (intersectsWall()) setCenterY(getCenterY() - speed);
        }
        if (pressedKeys.contains(KeyCode.LEFT)) {
            setCenterX(getCenterX() - speed);
            if (intersectsWall()) setCenterX(getCenterX() + speed);
        }
        if (pressedKeys.contains(KeyCode.RIGHT)) {
            setCenterX(getCenterX() + speed);
            if (intersectsWall()) setCenterX(getCenterX() - speed);
        }
    }

    private boolean intersectsWall() {
        boolean indicator = false;
        for (Node rectangle : wallGroup.getChildren()){
            if (getBoundsInParent().intersects(rectangle.getBoundsInParent())){
                indicator = true;
                break;
            }
        }
        return indicator;
    }

    public void playerKilled(){
        if (!invincible){
            this.getScene().getRoot().getChildrenUnmodifiable().forEach(node -> {
                if (node instanceof Enemy enemy) {
                    enemy.timeline.stop();
                }
            });
            try {
                Parent root = FXMLLoader.load(getClass().getResource("GameOver.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) getScene().getWindow();
                if (stage!=null){
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void setInvincible(boolean bool){
        invincible = bool;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}

