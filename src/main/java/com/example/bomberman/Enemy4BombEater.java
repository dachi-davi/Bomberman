package com.example.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Enemy4BombEater extends Enemy {
//    private Timeline timeline;
    private int lastDirection = -1;
    private boolean wasInDanger = false;
    private double speed = 1;

    public Enemy4BombEater() {
        setRadius(25);
        setOpacity(0);
    }

    public void move() {
        if (getParent() == null)return;
        Random random = new Random();
        int direction = random.nextInt(0, 4);

        if (bombIsNear()) {
            direction = findBombDirection();
        } else {
            int tries = 0;
            while (!correctDirectionChecker(direction) || (direction + 2) % 4 == lastDirection) {
                direction = (direction+1)%4;
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
        timeline = new Timeline(new KeyFrame(Duration.millis(21), e -> {
            enemyIntersectsPlayer();
            eatBomb();
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

    @Override
    public void enemyKilled() {
        if (timeline != null) {
            timeline.stop();
            SoundManager.playEnemyDies();
            ScoreManager.addScore(250);
        }
        ((Group) getParent()).getChildren().remove(image);
        ((Group) getParent()).getChildren().remove(this);
    }

    public boolean enemyIntersectsWalls() {
        for (Node wall : wallGroup.getChildren()) {
            if (this.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                return true;
            }
        }

        return false;
    }

    public boolean bombIsNear() {
        for (Node node : wallGroup.getParent().getChildrenUnmodifiable())
            if (node instanceof Bomb) {
                double distX = Math.abs(((Bomb) node).getCenterX() - getCenterX());
                double distY = Math.abs(((Bomb) node).getCenterY() - getCenterY());
                if (distX <= 160 && distY <= 160) return true;
                if (distY <= 160 && distX <= 160) return true;
            }
        return false;
    }

    private int findBombDirection() {
        Bomb closestBomb = null;
        double closestDist = Double.MAX_VALUE;
        Pane pane = (Pane) ((Group) getParent()).getParent();


        for (Node node : pane.getChildren()) {
            if (node instanceof Bomb) {
                Bomb bomb = (Bomb) node;
                double dist = getDistance(bomb.getCenterX(), bomb.getCenterY(), getCenterX(), getCenterY());
                if (dist < closestDist) {
                    closestDist = dist;
                    closestBomb = bomb;
                }
            }
        }

        if (closestBomb == null) return getEscapeDirection();

        double distX = closestBomb.getCenterX() - getCenterX();
        double distY = closestBomb.getCenterY() - getCenterY();

        if (Math.abs(distX) >= Math.abs(distY)) {
            if (distX >= 0 && correctDirectionChecker(0)) return 0;
            if (distX <= 0 && correctDirectionChecker(2)) return 2;
        }
        if (distY >= 0 && correctDirectionChecker(1)) return 1;
        if (distY <= 0 && correctDirectionChecker(3) ) return 3;

        if (distX >= 0 && correctDirectionChecker(0)) return 0;
        if (distX <= 0 && correctDirectionChecker(2)) return 2;

        return getEscapeDirection();
    }

    private double getDistance(double centerX, double centerY, double centerX1, double centerY1) {
        return Math.abs(centerX - centerX1) + Math.abs(centerY + centerY1);
    }

    private void eatBomb() {
        if (getParent() == null)return;
        Pane pane = (Pane) ((Group) getParent()).getParent();
        ArrayList<Bomb> bombsToRemove = new ArrayList<>();
        ArrayList<ImageView> bombsToRemoveI = new ArrayList<>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Bomb && getBoundsInParent().intersects(node.getBoundsInParent())) {
                bombsToRemove.add((Bomb) node);
                bombsToRemoveI.add(((Bomb) node).getBombImage());
                Bomb.bombAmount--;
                break;
            }
        }
        while (!bombsToRemove.isEmpty()) {
            bombsToRemove.getLast().getCountdown().stop();
            pane.getChildren().remove(bombsToRemove.removeLast());
            SoundManager.playBombEat();
        }
        while (!bombsToRemoveI.isEmpty()) {
            pane.getChildren().remove(bombsToRemoveI.removeLast());
        }
    }

    public void setImage(){
        image.setImage(new Image(getClass().getResource("/images/enemy4.png").toExternalForm()));
        image.setFitHeight(54);
        image.setFitWidth(54);
        image.xProperty().bind(centerXProperty().subtract(27));
        image.yProperty().bind(centerYProperty().subtract(27));
        ((Group)getParent()).getChildren().add(image);
    }
}
