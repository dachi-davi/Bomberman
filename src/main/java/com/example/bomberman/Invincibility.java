package com.example.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Invincibility extends Circle {
    private Player player;
    private Timeline timeline;
    private static Timeline powerDown;

    public Invincibility(Player player) {
        setRadius(6);
        this.player = player;
        setFill(Color.DARKGOLDENROD);
        timeline = new Timeline(new KeyFrame(Duration.millis(16), e->{
            if (pickedUp()){
                SoundManager.playItemGet();
                if (player.getSpeed() == 1)
                    SoundManager.playPowerUpTheme();
                if (powerDown!=null)
                    powerDown.stop();
                player.setInvincible(true);
                player.setImageInvincible();
                ((Pane) getParent()).getChildren().remove(this);
                timeline.stop();

                powerDown = new Timeline(new KeyFrame(Duration.seconds(10), ev -> {
                    player.setInvincible(false);
                    if (player.getSpeed() == 1){
                        player.setImageNormal();
                        if (!SoundManager.isPlayingTitleScreenTheme())
                            SoundManager.playMainTheme();
                    }

                }));
                powerDown.play();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private boolean pickedUp(){
        return player.intersects(getBoundsInParent());
    }
}
