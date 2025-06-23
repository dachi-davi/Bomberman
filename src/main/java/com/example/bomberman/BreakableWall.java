package com.example.bomberman;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class BreakableWall extends Rectangle {
    public BreakableWall() {
        setHeight(52);
        setWidth(52);
    }

    public void breakWall(Player player) {
        Random random = new Random();
        int powerPicker = random.nextInt(0, 45);

        if (powerPicker <= 3) {
            Invincibility invincibility = new Invincibility(player);
            invincibility.setCenterX(getX() + getLayoutX() + 26);
            invincibility.setCenterY(getY() + getLayoutY() + 26);
            ((Pane) ((Group) getParent()).getParent()).getChildren().add(invincibility);
        } else if (powerPicker <= 7) {
            SpeedBoost speedBoost = new SpeedBoost(player);
            speedBoost.setCenterX(getX() + getLayoutX() + 26);
            speedBoost.setCenterY(getY() + getLayoutY() + 26);
            ((Pane) ((Group) getParent()).getParent()).getChildren().add(speedBoost);
        } else if (powerPicker <= 17) {
            ExtraBomb extraBomb = new ExtraBomb(player);
            extraBomb.setCenterX(getX() + getLayoutX() + 26);
            extraBomb.setCenterY(getY() + getLayoutY() + 26);
            ((Pane) ((Group) getParent()).getParent()).getChildren().add(extraBomb);
        }

        ((Group) getParent()).getChildren().remove(this);
    }
}
