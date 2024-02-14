package com.bullethell.game.entities;

import com.bullethell.game.systems.AssetHandler;

public class Enemy extends Entity {
    private int health;
    private String type;

    public Enemy(float x, float y, AssetHandler assetHandler, String type) {
        super(x, y, type, assetHandler);
        this.health = 100; // temporary, get from config later
        this.type = type;
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
