package com.bullethell.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Vector2 position;
    public Sprite sprite;

    public Entity (float x, float y, String texturePath) {
        this.position = new Vector2(x, y);
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
    }

    public void update () {
        this.sprite.setPosition(position.x, position.y);
    }

//    public void render (SpriteBatch spriteBatch) {
//        sprite.draw(spriteBatch);
//    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
