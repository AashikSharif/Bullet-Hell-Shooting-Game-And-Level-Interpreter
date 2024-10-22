package com.bullethell.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.Patterns.observer.IObservable;
import com.bullethell.game.Patterns.observer.IObserver;
import com.bullethell.game.systems.AssetHandler;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements IObservable {
    protected Vector2 position;
    public Sprite sprite;
    protected Rectangle hitbox;
    public ShapeRenderer shapeRenderer;
    private List<IObserver> observers;

    public Entity(float x, float y, String entity, AssetHandler assetHandler) {
        this.position = new Vector2(x, y);
        this.sprite = new Sprite(assetHandler.getAssetTexture(entity));
        this.sprite.setPosition(x, y);

        float hbW, hbH = 0;

        if (entity.equals("player")) {
            hbW = this.sprite.getWidth() / 5;
            hbH = this.sprite.getHeight() / 5;
        } else if (entity.equals("bomb")) {
            hbW = 251;
            hbH = 400;
        } else {
            hbW = this.sprite.getWidth();
            hbH = this.sprite.getHeight();
        }

        this.hitbox = new Rectangle(x, y, hbW, hbH);
        this.shapeRenderer = new ShapeRenderer();
        this.observers = new ArrayList<>();
    }

    public void update() {
        this.sprite.setPosition(position.x, position.y);
        this.hitbox.setPosition(position.x + sprite.getWidth() / 2 - hitbox.width / 2,
                position.y + sprite.getHeight() / 2 - hitbox.height / 2);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public List<IObserver> getObservers() {
        return observers;
    }

    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IObserver observer) {
        this.observers.remove(observer);
    }
}
