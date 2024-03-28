package com.bullethell.game.settings;

public class PlayerSettings {
    private float normalSpeed, slowSpeed;

    private String moveUp;
    private String moveDown;
    private String moveLeft;
    private String moveRight;
    private String slowMode;
    private String shoot;

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

    public String getShoot() {
        return shoot;
    }

    public void setNormalSpeed(float normalSpeed) {
        this.normalSpeed = normalSpeed;
    }

    public void setSlowSpeed(float slowSpeed) {
        this.slowSpeed = slowSpeed;
    }

    public void setMoveUp(String moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(String moveDown) {
        this.moveDown = moveDown;
    }

    public void setMoveLeft(String moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(String moveRight) {
        this.moveRight = moveRight;
    }

    public void setSlowMode(String slowMode) {
        this.slowMode = slowMode;
    }


    public void setShoot(String shoot) {
        this.shoot = shoot;
    }
}
