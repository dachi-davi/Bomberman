package com.example.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class SpeedBoost extends Circle {
    private Player player;
    private Timeline timeline;
    private static Timeline powerDown;

    public SpeedBoost(Player player) {
        setRadius(6);
        this.player = player;
        setFill(Color.LIGHTBLUE);

        timeline = new Timeline(new KeyFrame(Duration.millis(16), e->{
            if (pickedUp()){
                SoundManager.playItemGet();
                if (!player.isInvincible())
                    SoundManager.playPowerUpTheme();
                if (powerDown!=null)
                    powerDown.stop();
                player.setSpeed(2.4);
                player.setImageSpeed();
                ((Pane) getParent()).getChildren().remove(this);
                timeline.stop();

                powerDown = new Timeline(new KeyFrame(Duration.seconds(10), ev -> {
                    player.setSpeed(1);
                    if (!player.isInvincible()){
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
