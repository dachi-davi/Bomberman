package com.example.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ExtraBomb extends Circle {
    private Player player;
    private Timeline timeline;

    public ExtraBomb(Player player) {
        setRadius(6);
        this.player = player;

        timeline = new Timeline(new KeyFrame(Duration.millis(16), e->{
           if (pickedUp()){
               SoundManager.playItemGet();
               Bomb.bombMax++;
               ((Pane) getParent()).getChildren().remove(this);
               timeline.stop();
           }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private boolean pickedUp(){
        return player.intersects(getBoundsInParent());
    }
}
