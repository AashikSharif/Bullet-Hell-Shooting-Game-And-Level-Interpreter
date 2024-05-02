package com.bullethell.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.systems.AssetHandler;

public class BombBullet extends Bullet {
    private static final int DEFAULT_DAMAGE = 150; // Example damage value for bomb bullets
    public static final float HITBOX_WIDTH = 251;
    public static final float HITBOX_HEIGHT = 400;
    public BombBullet(float x, float y, String entity, Vector2 velocity, int damage, AssetHandler assetHandler) {
        super(x, y, entity, velocity, damage, assetHandler);
        if (assetHandler == null) {
            throw new IllegalArgumentException("AssetHandler cannot be null");
        }
    }
    public boolean isExpired() {
        return super.isExpired();
    }
}

