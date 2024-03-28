package com.bullethell.game.entities;

import com.bullethell.game.systems.AssetHandler;

public class Enemy extends Entity {
    private int health;
    private String type;

    private static final float HITBOX_WIDTH = 30;
    private static final float HITBOX_HEIGHT = 30;

    private float shootCoolDown = 2.0f;
    private float timeSinceLatShot = 0;

    public Enemy(float x, float y, AssetHandler assetHandler, String type) {
        super(x, y, type, assetHandler);
        this.health = 100; // temporary, get from config later
        this.type = type;
    }

    public boolean isReadyToShoot(float deltaTime){
        timeSinceLatShot += deltaTime;
        if(timeSinceLatShot >= shootCoolDown){
            timeSinceLatShot -= shootCoolDown;
            return true;
        }
        return false;
    }

    public void resetShootTimer(){
        timeSinceLatShot = 0;
    }

    public void update () {
        super.update();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
