package com.bullethell.game.Patterns.strategy;

import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Player;
import com.bullethell.game.systems.AssetHandler;

import java.util.ArrayList;
import java.util.List;

public class StarBulletStrategy implements BulletStrategy {
    private int numArms;
    private float angleBetweenArms;
    private float bulletSpeed = 3;

    public StarBulletStrategy(int numArms) {
        this.numArms = numArms;
        this.angleBetweenArms = 360.0f / numArms;
    }

    @Override
    public List<Bullet> createBullets(Enemy enemy, Player player, AssetHandler assetHandler) {
        List<Bullet> bullets = new ArrayList<>();
        float currentAngle = 0;

        for (int i = 0; i < numArms; i++) {
            // direction
            Vector2 direction = new Vector2(
                    (float) Math.cos(Math.toRadians(currentAngle)),
                    (float) Math.sin(Math.toRadians(currentAngle))
            ).nor();


            Vector2 velocity = new Vector2(direction).scl(bulletSpeed);

            // bullet spawn position
            float bulletX = enemy.getPosition().x + enemy.sprite.getWidth() / 2 - Bullet.HITBOX_WIDTH / 2;
            float bulletY = enemy.getPosition().y + enemy.sprite.getHeight() / 2 - Bullet.HITBOX_HEIGHT / 2;

            bullets.add(new Bullet(bulletX, bulletY, "bullet", velocity, 25, assetHandler));

            currentAngle += angleBetweenArms;
        }

        return bullets;
    }
}

