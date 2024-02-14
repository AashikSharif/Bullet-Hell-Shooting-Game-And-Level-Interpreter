package com.bullethell.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.systems.AssetHandler;

public abstract class Entity {
    protected Vector2 position;
    public Sprite sprite;

    public Entity (float x, float y, String entity, AssetHandler assetHandler) {
        this.position = new Vector2(x, y);
        this.sprite = new Sprite(assetHandler.getAssetTexture(entity));
        this.sprite.setPosition(x, y);
    }

    public void update () {
        this.sprite.setPosition(position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
