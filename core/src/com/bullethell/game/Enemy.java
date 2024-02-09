package com.bullethell.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Sprite {
    private long lastBulletTime;
    private final long bulletInterval = 1000000000L;
    private List<Bullet> bullets;

    public void update() {
        long currentTime = TimeUtils.nanoTime();
        if (currentTime - lastBulletTime > bulletInterval) {
            shootBullet();
            lastBulletTime = currentTime;
        }
        // Update bullets
        for (Bullet bullet : bullets) {
            bullet.update(Gdx.graphics.getDeltaTime());
        }
    }
    private void shootBullet() {
        Bullet bullet = createBullet();
        bullet.setPosition(getX() + getWidth() / 2, getY());
        bullet.setVelocity(new Vector2(0, -100)); // Adjust bullet speed as needed
        bullets.add(bullet);
    }
    private Bullet createBullet() {
        TextureRegion bulletRegion = new TextureRegion(new Texture("bullet.png"));
        return new Bullet(bulletRegion);
    }
    public List<Bullet> getBullets() {
        return bullets;
    }
    public Enemy(TextureRegion region) {
        super(region);
        lastBulletTime = TimeUtils.nanoTime();
        bullets = new ArrayList<>();
    }
}
