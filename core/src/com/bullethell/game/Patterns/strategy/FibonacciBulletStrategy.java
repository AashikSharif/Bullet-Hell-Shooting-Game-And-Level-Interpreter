package com.bullethell.game.Patterns.strategy;

import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Player;
import com.bullethell.game.settings.LevelInterpreter;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.systems.AssetHandler;

import java.util.ArrayList;
import java.util.List;

public class FibonacciBulletStrategy implements BulletStrategy {
    private int steps;
    private float angle;

    public FibonacciBulletStrategy(int steps, float angle) {
        this.steps = steps;
        this.angle = angle;
    }
//    @Override
//    public List<Bullet> createBullets(Enemy enemy, Player player, AssetHandler assetHandler) {
//        LevelInterpreter levelInterpreter = Settings.getInstance().getLevelInterpreter();
//        String difficulty = levelInterpreter.getDifficulty();
//        float bulletSpeed = levelInterpreter.getDifficultySettings().get(difficulty).getBulletSpeed();
//
//        List<Bullet> bullets = new ArrayList<>();
//        int a = 0, b = 1;
//        float initialAngle = 0; // Initial angle
//
//        for (int i = 0; i < steps; i++) {
//            int next = a + b;
//            a = b;
//            b = next;
//
//            // direction
//            Vector2 direction = new Vector2((float) Math.cos(Math.toRadians(initialAngle)), (float) Math.sin(Math.toRadians(initialAngle))).nor();
//
//            Vector2 velocity = direction.scl(bulletSpeed);
//
//            // Position
//            float bulletX = enemy.getPosition().x + (enemy.sprite.getWidth() / 2) - Bullet.HITBOX_WIDTH / 2;
//            float bulletY = enemy.getPosition().y + (enemy.sprite.getHeight() / 2) - Bullet.HITBOX_HEIGHT / 2;
//            Bullet bullet = new Bullet(bulletX, bulletY, "bullet", velocity, 25, assetHandler);
//            bullets.add(bullet);
//
//            initialAngle += angle;
//        }
//
//        return bullets;
//    }
@Override
public List<Bullet> createBullets(Enemy enemy, Player player, AssetHandler assetHandler) {
    String bulletSprite = Settings.getInstance().getBulletSprites().get(enemy.getType());
    LevelInterpreter levelInterpreter = Settings.getInstance().getLevelInterpreter();
    String difficulty = levelInterpreter.getDifficulty();
    float bulletSpeed = levelInterpreter.getDifficultySettings().get(difficulty).getBulletSpeed();

    List<Bullet> bullets = new ArrayList<>();
    int a = 0, b = 1;
    float initialAngle = 0; // Initial angle
    float distanceFactor = 1.0f; // Starting distance factor

    for (int i = 0; i < steps; i++) {
        int next = a + b;
        a = b;
        b = next;

        // Increase distance factor
        distanceFactor += 0.1F; // Adjust the increment value based on visual tests

        // Calculate direction
        Vector2 direction = new Vector2((float) Math.cos(Math.toRadians(initialAngle)), (float) Math.sin(Math.toRadians(initialAngle))).nor();
        Vector2 position = new Vector2(enemy.getPosition().x + (enemy.sprite.getWidth() / 2), enemy.getPosition().y + (enemy.sprite.getHeight() / 2));

        // Apply distance factor to position
        position.add(direction.scl(distanceFactor));

        Vector2 velocity = direction.scl(bulletSpeed);

        // Create bullet at modified position
        Bullet bullet = new Bullet(position.x - Bullet.HITBOX_WIDTH / 2, position.y - Bullet.HITBOX_HEIGHT / 2, bulletSprite, velocity, 25, assetHandler);
        bullets.add(bullet);

        initialAngle += angle;
    }

    return bullets;
}

}
