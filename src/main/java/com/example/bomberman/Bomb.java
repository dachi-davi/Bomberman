package com.example.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

public class Bomb extends Circle {
    public static int bombAmount = 0, bombMax = 1;
    private Timeline countdown;
    private Timeline secondExplosion;
    private Group enemyGroup;
    private Player player;
    private Group wallGroup;
    private final double centerX;
    private final double centerY;
    private final Pane pane;
    private ImageView bombImage = new ImageView();
    private ImageView explosionImage = new ImageView();
    private int explosionAmount = 0;

    public Bomb(Player player, Group enemyGroup, Group wallGroup, AnchorPane root) {
        this.enemyGroup = enemyGroup;
        this.player = player;
        this.wallGroup = wallGroup;
        this.pane = root;
        setOpacity(0);
        setCenterX(player.getCenterX());
        setCenterX(getCenterX() - getCenterX() % 52 + 26);
        setCenterY(player.getCenterY());
        setCenterY(getCenterY() - getCenterY() % 52 + 26);
        setRadius(25);
        centerX = getCenterX();
        centerY = getCenterY();
        setImageBomb();
        countdown = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            explode();
            secondExplosion = new Timeline(new KeyFrame(Duration.seconds(0.15), ex -> explode()));
            secondExplosion.play();
        }));
        countdown.play();
    }


    public void explode() {
        ArrayList<Enemy> killedEnemies = new ArrayList<>();
        if (getParent() != null) {
            pane.getChildren().remove(bombImage);
            pane.getChildren().remove(this);
            bombAmount--;
        }
        if (explosionAmount == 0){
            setImageExplosion();
            explosionAmount++;
        }else pane.getChildren().remove(explosionImage);

        for (Node node : enemyGroup.getChildren()) {
            if (node instanceof Enemy) {
                Enemy enemy = (Enemy) node;
                if (isInBlastRange(enemy.getCenterX(), enemy.getCenterY()))
                    killedEnemies.add((Enemy) node);
            }
        }
        while (!killedEnemies.isEmpty()) killedEnemies.removeLast().enemyKilled();

        if (isInBlastRange(player.getCenterX(), player.getCenterY())) {
            for (Node nodeGroup : ((Pane) player.getParent()).getChildren())
                if (nodeGroup instanceof Group)
                    for (Node node : ((Group) nodeGroup).getChildren())
                        if (node instanceof Enemy)
                            if (((Enemy) node).timeline != null) ((Enemy) node).timeline.stop();

            player.playerKilled();
        }

        ArrayList<Node> wallsToRemove = new ArrayList<>();
        for (Node node : wallGroup.getChildren()) {
            if (node instanceof BreakableWall) {
                BreakableWall wall = (BreakableWall) node;
                if (isInBlastRange(wall.getX() + wall.getLayoutX() + 26, wall.getY() + wall.getLayoutY() + 26))
                    wallsToRemove.add(node);
            }
        }
        while (!wallsToRemove.isEmpty()) ((BreakableWall) wallsToRemove.removeLast()).breakWall(player);


        ArrayList<Bomb> bombsToExplode = new ArrayList<>();
        if (pane != null)
            for (Node node : pane.getChildren()) {
                if (node instanceof Bomb) {
                    if (isInBlastRange(((Bomb) node).getCenterX(), ((Bomb) node).getCenterY()))
                        bombsToExplode.add((Bomb) node);
                }
            }
        while (!bombsToExplode.isEmpty()) {
//            bombsToExplode.removeLast().explode();
            Bomb tempBomb = bombsToExplode.removeLast();
            tempBomb.countdown.stop();
            tempBomb.explode();
            secondExplosion = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> tempBomb.explode()));
            secondExplosion.play();
        }
    }


    public boolean isInBlastRange(double x, double y) {
        double bombX = centerX;
        double bombY = centerY;

        if (Math.abs(x - bombX) <= 90 && Math.abs(y - bombY) <= 44) return true;
        if (Math.abs(y - bombY) <= 90 && Math.abs(x - bombX) <= 44) return true;
        return false;
    }

    public void setImageBomb(){
        bombImage.setImage(new Image(getClass().getResource("/images/bomb.png").toExternalForm()));
        bombImage.setFitHeight(50);
        bombImage.setFitWidth(50);
        bombImage.xProperty().bind(centerXProperty().subtract(25));
        bombImage.yProperty().bind(centerYProperty().subtract(25));
        pane.getChildren().add(bombImage);
    }
    public void setImageExplosion(){
        explosionImage.setImage(new Image(getClass().getResource("/images/explosion.png").toExternalForm()));
        explosionImage.setFitHeight(160);
        explosionImage.setFitWidth(160);
        explosionImage.xProperty().bind(centerXProperty().subtract(79));
        explosionImage.yProperty().bind(centerYProperty().subtract(79));
        pane.getChildren().add(explosionImage);
    }

    public Timeline getCountdown() {
        return countdown;
    }
}
