package com.bullethell.game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
public class Bullet extends Sprite {
    private Vector2 velocity;

    public Bullet(TextureRegion region) {
        super(region);
        velocity = new Vector2();
    }

    public void update(float deltaTime) {
        // Update bullet position based on its velocity
        setPosition(getX() + velocity.x * deltaTime, getY() + velocity.y * deltaTime);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

}

