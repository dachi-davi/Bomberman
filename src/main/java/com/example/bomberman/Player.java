package com.example.bomberman;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public class Player extends Circle {
    private Group wallGroup;
    private boolean invincible;
    private double speed = 1;
    private ImageView image = new ImageView();
    private boolean playerKilled = false;

    public Player(Group wallGroup) {
        Bomb.bombMax = 1;
        this.wallGroup = wallGroup;
        setRadius(22);
        setFill(Paint.valueOf("#bc7177"));
        Enemy.setPlayerKilled(false);
        setOpacity(0);
        setImage();
    }

    public void move(Set<KeyCode> pressedKeys) {
        if (playerKilled)return;
        if (pressedKeys.contains(KeyCode.UP) || pressedKeys.contains(KeyCode.DOWN) || pressedKeys.contains(KeyCode.LEFT) || pressedKeys.contains(KeyCode.RIGHT)){
            SoundManager.playWalking();
        }

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
        if (playerKilled)return;
        if (!invincible){
            playerKilled = true;
            for (Node nodeGroup : ((Pane)getParent()).getChildren()){
                if (nodeGroup instanceof Group)
                    for (Node node : ((Group) nodeGroup).getChildren())
                        if (node instanceof Enemy)
                            if (((Enemy) node).timeline != null) ((Enemy) node).timeline.stop();
            }

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

    public void setImage(){
        image.setImage(new Image(getClass().getResource("/images/sans.png").toExternalForm()));
        image.setFitHeight(48);
        image.setFitWidth(48);
        image.xProperty().bind(centerXProperty().subtract(24));
        image.yProperty().bind(centerYProperty().subtract(24));
        ((Pane)wallGroup.getParent()).getChildren().add(image);
    }

    public void setImageInvincible(){
        image.setImage(new Image(getClass().getResource("/images/sansYellow.png").toExternalForm()));
    }
    public void setImageSpeed(){
        image.setImage(new Image(getClass().getResource("/images/sansBlue.png").toExternalForm()));
    }
    public void setImageNormal(){
        image.setImage(new Image(getClass().getResource("/images/sans.png").toExternalForm()));
    }

    public boolean isInvincible() {
        return invincible;
    }

    public double getSpeed() {
        return speed;
    }
}

