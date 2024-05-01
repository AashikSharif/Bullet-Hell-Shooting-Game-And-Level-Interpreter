package com.bullethell.game.Patterns.strategy;

import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Player;
import com.bullethell.game.systems.AssetHandler;

import java.util.ArrayList;
import java.util.List;

public class SpiralBulletStrategy implements BulletStrategy {
    private int numberOfBulletsPerSpiral;
    private float spiralSeparationAngle;  // Angle offset for bullet direction
    private float bulletSpeed = 2;        // Speed of the bullets
    private float rotationSpeed = 0.5f;   // Speed at which the origins rotate
    private float currentRotation = 0f;   // Current rotation angle of the origins

    public SpiralBulletStrategy(int numberOfBulletsPerSpiral, float spiralSeparationAngle) {
        this.numberOfBulletsPerSpiral = numberOfBulletsPerSpiral;
        this.spiralSeparationAngle = spiralSeparationAngle;
    }

    @Override
    public List<Bullet> createBullets(Enemy enemy, Player player, AssetHandler assetHandler) {
        List<Bullet> bullets = new ArrayList<>();
        float angleStep = 360.0f / numberOfBulletsPerSpiral;
        float originRadius = 50;  // Radius of the circle along which the origins move

        // Update the rotation of the origins
        currentRotation += rotationSpeed;

        // Calculate positions for both origins
        Vector2 origin1 = new Vector2(
                enemy.getPosition().x + enemy.sprite.getWidth() / 2 + originRadius * (float)Math.cos(Math.toRadians(currentRotation)),
                enemy.getPosition().y + enemy.sprite.getHeight() / 2 + originRadius * (float)Math.sin(Math.toRadians(currentRotation))
        );

        Vector2 origin2 = new Vector2(
                enemy.getPosition().x + enemy.sprite.getWidth() / 2 + originRadius * (float)Math.cos(Math.toRadians(currentRotation + 180)),
                enemy.getPosition().y + enemy.sprite.getHeight() / 2 + originRadius * (float)Math.sin(Math.toRadians(currentRotation + 180))
        );

        for (int i = 0; i < numberOfBulletsPerSpiral; i++) {
            float currentAngle = i * angleStep;

            // Create bullets from origin 1
            Vector2 direction1 = new Vector2(
                    (float)Math.cos(Math.toRadians(currentAngle)),
                    (float)Math.sin(Math.toRadians(currentAngle))
            ).nor();
            Vector2 velocity1 = new Vector2(direction1).scl(bulletSpeed);
            bullets.add(new Bullet(origin1.x - Bullet.HITBOX_WIDTH / 2, origin1.y - Bullet.HITBOX_HEIGHT / 2, "bullet", velocity1, 25, assetHandler));

            // Create bullets from origin 2
            Vector2 direction2 = new Vector2(
                    (float)Math.cos(Math.toRadians(currentAngle + spiralSeparationAngle)),  // Offset angle for a different spiral pattern
                    (float)Math.sin(Math.toRadians(currentAngle + spiralSeparationAngle))
            ).nor();
            Vector2 velocity2 = new Vector2(direction2).scl(bulletSpeed);
            bullets.add(new Bullet(origin2.x - Bullet.HITBOX_WIDTH / 2, origin2.y - Bullet.HITBOX_HEIGHT / 2, "bullet", velocity2, 25, assetHandler));
        }

        return bullets;
    }
}
