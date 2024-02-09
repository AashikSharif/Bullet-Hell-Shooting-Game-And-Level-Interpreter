package com.bullethell.game.settings;

public class GlobalSettings {
    private float normalSpeed, slowSpeed;

    private String moveUp;
    private String moveDown;
    private String moveLeft;
    private String moveRight;

    private String slowMode;

    public GlobalSettings () {}
    public float getNormalSpeed() {
        return normalSpeed;
    }

    public void setNormalSpeed(float normalSpeed) {
        this.normalSpeed = normalSpeed;
    }

    public float getSlowSpeed() {
        return slowSpeed;
    }

    public void setSlowSpeed(float slowSpeed) {
        this.slowSpeed = slowSpeed;
    }

    public String getMoveUp() {
        return moveUp;
    }

    public void setMoveUp(String moveUp) {
        this.moveUp = moveUp;
    }

    public String getMoveDown() {
        return moveDown;
    }

    public void setMoveDown(String moveDown) {
        this.moveDown = moveDown;
    }

    public String getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(String moveLeft) {
        this.moveLeft = moveLeft;
    }

    public String getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(String moveRight) {
        this.moveRight = moveRight;
    }

    public String getSlowMode() {
        return slowMode;
    }

    public void setSlowMode(String slowMode) {
        this.slowMode = slowMode;
    }
}
