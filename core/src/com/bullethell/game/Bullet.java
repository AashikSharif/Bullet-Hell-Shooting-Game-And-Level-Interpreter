package com.bullethell.game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
public class Bullet extends Sprite {
    private float x;
    private float y;
    private float speed;
    private float angle;
    public void setBullet(float x, float y, float speed, float angle) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
    }
    public void update(float delta) {
        x += MathUtils.cos(angle) * speed * delta;
        y += MathUtils.sin(angle) * speed * delta;
    }
    public Bullet(TextureRegion region) {
        super(region);
    }
}

