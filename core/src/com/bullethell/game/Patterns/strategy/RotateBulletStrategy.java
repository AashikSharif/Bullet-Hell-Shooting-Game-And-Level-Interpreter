package com.bullethell.game.Patterns.strategy;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Player;
import com.bullethell.game.settings.LevelInterpreter;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.systems.AssetHandler;

import java.util.ArrayList;
import java.util.List;
public class RotateBulletStrategy  implements BulletStrategy{
    private int num;
    private float timeDelay;

    public RotateBulletStrategy(int num, float timeDelay) {
        this.num = num;
        this.timeDelay = timeDelay;
    }
    public List<Bullet> createBullets(Enemy enemy, Player player, AssetHandler assetHandler) {
        String bulletSprite = Settings.getInstance().getBulletSprites().get(enemy.getType());
        LevelInterpreter levelInterpreter = Settings.getInstance().getLevelInterpreter();
        String difficulty = levelInterpreter.getDifficulty();
        float bulletSpeed = levelInterpreter.getDifficultySettings().get(difficulty).getBulletSpeed();

        List<Bullet> bullets = new ArrayList<>();
        float angleBetweenBullets = 360f / num;

        for (int i = 0; i < num; i++) {
            float angle = i * angleBetweenBullets;

            // Convert angle to radians
            float radians = MathUtils.degreesToRadians * angle;

            // direction

            // bullet spawn position
            float circleRadius = 100f;
            float bulletX = enemy.getPosition().x + circleRadius * MathUtils.cos(radians);
            float bulletY = enemy.getPosition().y + circleRadius * MathUtils.sin(radians);
            Vector2 direction = new Vector2(player.getPosition().x + player.sprite.getWidth() / 2 - bulletX,
                    player.getPosition().y + player.sprite.getHeight() / 2 - bulletY).nor();
            Vector2 velocity = direction.scl(bulletSpeed);

            bullets.add(new Bullet(bulletX, bulletY, bulletSprite, velocity, 25, assetHandler));

        }

        return bullets;
    }

}
