package com.example.bomberman;

import javafx.beans.property.*;

public class UserScore {
    private final StringProperty username;
    private final IntegerProperty score;

    public UserScore(String username, int score) {
        this.username = new SimpleStringProperty(username);
        this.score = new SimpleIntegerProperty(score);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int score) {
        this.score.set(score);
    }
}
