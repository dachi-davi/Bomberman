package com.example.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ScoreManager {
    private static int score = 0;
    private static int multiplier = 1;
    private static Timeline comboTimer;

    public static void addScore(int basePoints) {
        score += basePoints * multiplier;

        multiplier++;

        if (comboTimer != null) comboTimer.stop();

        comboTimer = new Timeline(new KeyFrame(Duration.seconds(6.5), e -> resetCombo()));
        comboTimer.setCycleCount(1);
        comboTimer.play();
    }

    public static void resetCombo() {
        multiplier = 1;
    }

    public static int getScore() {
        return score;
    }

    public static void resetScore() {
        score = 0;
        multiplier = 1;
        if (comboTimer != null) comboTimer.stop();
    }
}
