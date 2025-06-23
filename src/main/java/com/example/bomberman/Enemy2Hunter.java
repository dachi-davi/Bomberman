package com.example.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class Enemy2Hunter extends Enemy{
//    private Timeline timeline;
    private int lastDirection = -1;
    private boolean wasInDanger = false;
    private double speed = 2;

    public Enemy2Hunter() {
        setRadius(25);
        setFill(Color.DARKMAGENTA);
    }

    public void move() {
        if (getParent() == null)return;
        Random random = new Random();
        int direction = random.nextInt(0, 4);

        if (bombIsNear()) {
            direction = getEscapeDirection();
            wasInDanger = true;
        } else if (playerIsNear() && !wasInDanger) {
            direction = getChaseDirection();
            if (direction == (lastDirection+2)%4)direction = getEscapeDirection();
        } else {
            wasInDanger = false;
            int tries = 0;
            while (!correctDirectionChecker(direction) || (direction + 2) % 4 == lastDirection) {
                direction = random.nextInt(0, 4);
                tries++;
                if (tries >= 10){
                    direction = getEscapeDirection();
                    break;
                }
            }
        }

        lastDirection = direction;
        if (direction == -1) {
            Timeline retry = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> move()));
            retry.setCycleCount(1);
            retry.play();
            return;
        }
        double xCord = 0, yCord = 0;
        if (direction == 0) xCord = speed;
        if (direction == 1) yCord = speed;
        if (direction == 2) xCord = -speed;
        if (direction == 3) yCord = -speed;


        double finalXCord = xCord;
        double finalYCord = yCord;
        timeline = new Timeline(new KeyFrame(Duration.millis(28), e -> {
            if (player!=null)enemyIntersectsPlayer();
            setCenterX(getCenterX() + finalXCord);
            setCenterY(getCenterY() + finalYCord);
            setCenterX((double) Math.round(getCenterX() * 1000) / 1000);
            setCenterY((double) Math.round(getCenterY() * 1000) / 1000);
            if (getCenterX() % 52 == 26 && getCenterY() % 52 == 26) {
                timeline.stop();
                move();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public boolean playerIsNear() {
        double distX = Math.abs(player.getCenterX() - getCenterX());
        double distY = Math.abs(player.getCenterY() - getCenterY());
        return distX <= 500 && distY <= 500;
    }


    @Override
    public void enemyKilled() {
        ScoreManager.addScore(200);
        if (timeline != null) timeline.stop();
        ((Group) getParent()).getChildren().remove(image);
        ((Group) getParent()).getChildren().remove(this);
    }

    public void setImage(){
        image.setImage(new Image(getClass().getResource("/images/enemy2.png").toExternalForm()));
        image.setFitHeight(54);
        image.setFitWidth(54);
        image.xProperty().bind(centerXProperty().subtract(27));
        image.yProperty().bind(centerYProperty().subtract(27));
        ((Group)getParent()).getChildren().add(image);
    }
}
