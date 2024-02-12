package com.bullethell.game.controllers;

public interface IControllable {
    public void shoot();
    public void moveUp (float speedFactor);
    public void moveDown (float speedFactor);
    public void moveLeft (float speedFactor);
    public void moveRight (float speedFactor);
    public void slowMode (boolean isSlow);
}
