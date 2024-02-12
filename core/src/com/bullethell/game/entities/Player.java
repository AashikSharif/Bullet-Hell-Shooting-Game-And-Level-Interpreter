package com.bullethell.game.entities;

import com.bullethell.game.controllers.IControllable;

public class Player extends Entity implements IControllable {
    boolean isSlow = false;
    public Player(float x, float y) {
        super(x, y, "player.png");
    }

    public void update () {
        super.update();
    }

    @Override
    public void shoot() {

    }

    @Override
    public void moveUp(float speedFactor) {
        this.position.y += speedFactor;
    }

    @Override
    public void moveDown(float speedFactor) {
        this.position.y -= speedFactor;
    }

    @Override
    public void moveLeft(float speedFactor) {
        this.position.x -= speedFactor;
    }

    @Override
    public void moveRight(float speedFactor) {
        this.position.x += speedFactor;
    }


    @Override
    public void slowMode(boolean isSlow) {
        this.isSlow = isSlow;
    }
}
