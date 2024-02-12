package com.bullethell.game.settings;

public class PlayerSettings {
    private float normalSpeed, slowSpeed;

    private String moveUp;
    private String moveDown;
    private String moveLeft;
    private String moveRight;
    private String slowMode;

    public PlayerSettings () {}

    public float getNormalSpeed() {
        return normalSpeed;
    }

    public float getSlowSpeed() {
        return slowSpeed;
    }

    public String getMoveUp() {
        return moveUp;
    }

    public String getMoveDown() {
        return moveDown;
    }

    public String getMoveLeft() {
        return moveLeft;
    }

    public String getMoveRight() {
        return moveRight;
    }

    public String getSlowMode() {
        return slowMode;
    }
}
