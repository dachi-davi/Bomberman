package com.example.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class Enemy extends Circle {
    protected Timeline timeline;
    protected int lastDirection = -1;
    protected static Player player;
    protected Group wallGroup;
    protected boolean wasInDanger = false;
    protected double speed = 1;
    protected static boolean playerKilled = false;
    protected ImageView image = new ImageView();

    public void setImage(){
        image.setImage(new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Red_circle.svg/480px-Red_circle.svg.png"));
        image.setFitHeight(50);
        image.setFitWidth(50);
        image.xProperty().bind(centerXProperty().subtract(25));
        image.yProperty().bind(centerYProperty().subtract(25));
        ((Group)getParent()).getChildren().add(image);
    }
    public Enemy() {
        setRadius(25);
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
                if (tries == 20 && correctDirectionChecker(lastDirection)) {
                    direction = lastDirection;
                    break;
                } else if (tries == 20) direction = -1;
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
        timeline = new Timeline(new KeyFrame(Duration.millis(24), e -> {
            enemyIntersectsPlayer();
            setCenterX(getCenterX() + finalXCord);
            setCenterY(getCenterY() + finalYCord);
            if (getCenterX() % 52 == 26 && getCenterY() % 52 == 26) {
                timeline.stop();
                move();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public boolean correctDirectionChecker(int direction) {
        double xCord = 0, yCord = 0;
        if (direction == 0) xCord = 2;
        if (direction == 1) yCord = 2;
        if (direction == 2) xCord = -2;
        if (direction == 3) yCord = -2;

        setCenterX(getCenterX() + xCord);
        setCenterY(getCenterY() + yCord);
        boolean hitsWall = enemyIntersectsWalls();
        setCenterX(getCenterX() - xCord);
        setCenterY(getCenterY() - yCord);

        return !hitsWall;
    }

    public boolean enemyIntersectsWalls() {
        for (Node wall : wallGroup.getChildren()) {
            if (this.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                return true;
            }
        }

        for (Node node : wallGroup.getParent().getChildrenUnmodifiable())
            if (node instanceof Bomb){
                Bomb bomb = (Bomb) node;
                if (this.getBoundsInParent().intersects(node.getBoundsInParent())) {
                    if (Math.abs(getCenterX() - bomb.getCenterX()) > 5 || Math.abs(getCenterY() - bomb.getCenterY()) > 5)
                        return true;
                }
            }

        return false;
    }

    public boolean playerIsNear() {
        double distX = Math.abs(player.getCenterX() - getCenterX());
        double distY = Math.abs(player.getCenterY() - getCenterY());
        return distX <= 160 && distY <= 160;
    }

    public int getChaseDirection() {
        double distX = player.getCenterX() - getCenterX();
        double distY = player.getCenterY() - getCenterY();

        if (Math.abs(distX) >= Math.abs(distY)) {
            if (distX >= 0 && correctDirectionChecker(0) && lastDirection != 2) return 0;
            if (distX <= 0 && correctDirectionChecker(2) && lastDirection != 0) return 2;
        }
        if (distY >= 0 && correctDirectionChecker(1) && lastDirection != 3) return 1;
        if (distY <= 0 && correctDirectionChecker(3) && lastDirection != 1) return 3;

        if (distX >= 0 && correctDirectionChecker(0) && lastDirection != 2) return 0;
        if (distX <= 0 && correctDirectionChecker(2) && lastDirection != 0) return 2;

        Random random = new Random();
        int direction = random.nextInt(0, 4);
        for (int i = 0; i < 8; i++) {
            if (i == 7) return -1;
            if (correctDirectionChecker(direction)) {
                return direction;
            }
            direction = (direction + 1) % 4;
        }
        return -1;
    }

    public boolean bombIsNear() {
        for (Node node : wallGroup.getParent().getChildrenUnmodifiable())
            if (node instanceof Bomb) {
                double distX = Math.abs(((Bomb) node).getCenterX() - getCenterX());
                double distY = Math.abs(((Bomb) node).getCenterY() - getCenterY());
                if (distX <= 55 && distY <= 15) return true;
                if (distY <= 55 && distX <= 15) return true;
            }
        return false;
    }

    public int getEscapeDirection() {
        Random random = new Random();
        int direction = random.nextInt(0, 4);

        for (int i = 0; i < 4; i++) {
            if (correctDirectionChecker(direction)) return direction;
            direction = (direction + 1) % 4;
        }
        return -1;
    }

    public void enemyKilled() {
        if (timeline != null) timeline.stop();
        ((Group) getParent()).getChildren().remove(this);
    }

    public void enemyIntersectsPlayer(){
        if (!playerKilled)
            if (getBoundsInParent().intersects(player.getBoundsInParent())){
                if (player.getScene()!=null){
                    stopEnemies();
                    System.out.println("enemy killed player");
                    playerKilled = true;
                    player.playerKilled();
                }

        }
    }
    private void stopEnemies(){
        for (Node node : ((Group)getParent()).getChildren()){
            if (node instanceof Enemy) if (((Enemy) node).timeline!=null){
                ((Enemy) node).timeline.stop();
                System.out.println("yay");
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setWallGroup(Group wallGroup) {
        this.wallGroup = wallGroup;
    }

    public static void setPlayerKilled(boolean playerKilled) {
        Enemy.playerKilled = playerKilled;
    }
}