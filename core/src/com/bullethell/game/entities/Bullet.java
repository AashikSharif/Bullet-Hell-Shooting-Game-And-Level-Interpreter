package com.bullethell.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.Patterns.observer.IObserver;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.utils.Event;

public class Bullet extends Entity {
    private final Vector2 velocity;
    private final int damage;

    public static final float HITBOX_WIDTH = 30;
    public static final float HITBOX_HEIGHT = 30;

    public Bullet(float x, float y, String entity, Vector2 velocity, int damage, AssetHandler assetHandler) {
        super(x, y, entity, assetHandler);
        this.velocity = new Vector2(velocity);
        this.damage = damage;
    }

    public void update() {
        this.position.add(velocity);
        super.update();
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void registerObserver(IObserver observer) {

    }

    @Override
    public void removeObserver(IObserver observer) {

    }

    @Override
    public void notifyObservers(Event event) {

    }
}
