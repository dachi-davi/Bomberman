package com.example.bomberman;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URISyntaxException;

public class SoundManager {
    private static double volume = 0.7;
    private static double sfxVolume = 1;
    private static boolean playingTitleScreenTheme = true;

    private static final Media mediaBombExplodes;
    private static final Media mediaBombEat;
    private static final Media mediaPlaceBomb;
    private static final Media mediaEnemyDies;
    private static final Media mediaItemGet;
    private static final Media mediaStageIntro;
    private static final Media mediaWalking;
    private static final Media mediaGameOver;
    private static final Media mediaMainTheme;
    private static final Media mediaPowerUpTheme;
    private static final Media mediaStageClear;
    private static final Media mediaTitleScreenTheme;



    static {
        try {
            mediaBombExplodes = new Media(SoundManager.class.getResource("/sounds/BombExplodes.wav").toURI().toString());
            mediaBombEat = new Media(SoundManager.class.getResource("/sounds/BombEat.wav").toURI().toString());
            mediaPlaceBomb = new Media(SoundManager.class.getResource("/sounds/PlaceBomb.wav").toURI().toString());
            mediaEnemyDies = new Media(SoundManager.class.getResource("/sounds/EnemyDies.wav").toURI().toString());
            mediaItemGet = new Media(SoundManager.class.getResource("/sounds/ItemGet.wav").toURI().toString());
            mediaStageIntro = new Media(SoundManager.class.getResource("/sounds/StageIntro.wav").toURI().toString());
            mediaWalking = new Media(SoundManager.class.getResource("/sounds/Walking1.wav").toURI().toString());
            mediaGameOver = new Media(SoundManager.class.getResource("/sounds/GameOver.mp3").toURI().toString());
            mediaMainTheme = new Media(SoundManager.class.getResource("/sounds/MainTheme.mp3").toURI().toString());
            mediaPowerUpTheme = new Media(SoundManager.class.getResource("/sounds/PowerUpTheme.mp3").toURI().toString());
            mediaStageClear = new Media(SoundManager.class.getResource("/sounds/StageClear.mp3").toURI().toString());
            mediaTitleScreenTheme = new Media(SoundManager.class.getResource("/sounds/TitleScreenTheme.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }



    private static MediaPlayer themePlayer;
    private static MediaPlayer walkingPlayer;

    public static void playBombExplodes(){
        MediaPlayer mediaPlayer = new MediaPlayer(mediaBombExplodes);
        mediaPlayer.setVolume(sfxVolume);
        mediaPlayer.play();
    }

    public static void playBombEat(){
        MediaPlayer mediaPlayer = new MediaPlayer(mediaBombEat);
        mediaPlayer.setVolume(sfxVolume);
        mediaPlayer.play();
    }

    public static void playPlaceBomb(){
        MediaPlayer mediaPlayer = new MediaPlayer(mediaPlaceBomb);
        mediaPlayer.setVolume(sfxVolume);
        mediaPlayer.play();
    }

    public static void playEnemyDies(){
        MediaPlayer mediaPlayer = new MediaPlayer(mediaEnemyDies);
        mediaPlayer.setVolume(sfxVolume);
        mediaPlayer.play();
    }

    public static void playItemGet(){
        MediaPlayer mediaPlayer = new MediaPlayer(mediaItemGet);
        mediaPlayer.setVolume(sfxVolume);
        mediaPlayer.play();
    }

    public static void playStageIntro(){
        MediaPlayer mediaPlayer = new MediaPlayer(mediaStageIntro);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }

    public static void playWalking(){
        if (walkingPlayer==null){
            walkingPlayer = new MediaPlayer(mediaWalking);
            walkingPlayer.setVolume(sfxVolume);
            walkingPlayer.play();
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e->{walkingPlayer.stop();}));
            timeline.play();
        }
        else if (walkingPlayer.getStatus() == MediaPlayer.Status.STOPPED){
            walkingPlayer = new MediaPlayer(mediaWalking);
            walkingPlayer.setVolume(sfxVolume);
            walkingPlayer.play();
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e->{walkingPlayer.stop();}));
            timeline.play();
        }
    }

    public static void playGameOver(){
        MediaPlayer mediaPlayer = new MediaPlayer(mediaGameOver);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }

    public static void playStageClear(){
        MediaPlayer mediaPlayer = new MediaPlayer(mediaStageClear);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }

    public static void playTitleScreenTheme(){
        playingTitleScreenTheme = true;
        if (themePlayer != null)
            if (themePlayer.getStatus() == MediaPlayer.Status.PLAYING)themePlayer.stop();
        themePlayer = new MediaPlayer(mediaTitleScreenTheme);
        themePlayer.setVolume(volume);
        themePlayer.setCycleCount(MediaPlayer.INDEFINITE);
        themePlayer.play();
    }

    public static void playMainTheme(){
        playingTitleScreenTheme = false;
        if (themePlayer != null)
            if (themePlayer.getStatus() == MediaPlayer.Status.PLAYING)themePlayer.stop();
        themePlayer = new MediaPlayer(mediaMainTheme);
        themePlayer.setVolume(volume);
        themePlayer.setCycleCount(MediaPlayer.INDEFINITE);
        themePlayer.play();
    }

    public static void playPowerUpTheme(){
        playingTitleScreenTheme = false;
        if (themePlayer != null)
            if (themePlayer.getStatus() == MediaPlayer.Status.PLAYING)themePlayer.stop();
        themePlayer = new MediaPlayer(mediaPowerUpTheme);
        themePlayer.setVolume(volume);
        themePlayer.setCycleCount(MediaPlayer.INDEFINITE);
        themePlayer.play();
    }

    public static void setVolume(double volume) {SoundManager.volume = volume;}

    public static double getVolume() {return volume;}

    public static MediaPlayer getThemePlayer() {return themePlayer;}

    public static void setThemePlayer(MediaPlayer themePlayer) {SoundManager.themePlayer = themePlayer;}

    public static double getSfxVolume() {return sfxVolume;}

    public static void setSfxVolume(double sfxVolume) {SoundManager.sfxVolume = sfxVolume;}

    public static boolean isPlayingTitleScreenTheme() {
        return playingTitleScreenTheme;
    }

    public static void setPlayingTitleScreenTheme(boolean playingTitleScreenTheme) {
        SoundManager.playingTitleScreenTheme = playingTitleScreenTheme;
    }
}

