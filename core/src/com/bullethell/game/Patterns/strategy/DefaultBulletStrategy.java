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

public class DefaultBulletStrategy implements BulletStrategy {

    public DefaultBulletStrategy() {}
    @Override
    public List<Bullet> createBullets(Enemy enemy, Player player, AssetHandler assetHandler) {
        String bulletSprite = Settings.getInstance().getBulletSprites().get(enemy.getType());
        LevelInterpreter levelInterpreter = Settings.getInstance().getLevelInterpreter();
        String difficulty = levelInterpreter.getDifficulty();
        float bulletSpeed = levelInterpreter.getDifficultySettings().get(difficulty).getBulletSpeed();

        List<Bullet> bullets = new ArrayList<>();
                Vector2 direction = new Vector2(
                player.getPosition().x + player.sprite.getWidth() / 2 - (enemy.getPosition().x + enemy.sprite.getWidth() / 2),
                player.getPosition().y + player.sprite.getHeight() / 2 - (enemy.getPosition().y + enemy.sprite.getHeight() / 2)
        ).nor();

        Vector2 velocity = direction.scl(bulletSpeed);

        float bulletX = enemy.getPosition().x + (enemy.sprite.getWidth() / 2) - Bullet.HITBOX_WIDTH / 2;
        float bulletY = enemy.getPosition().y - Bullet.HITBOX_HEIGHT;
        Bullet enemyBullet = new Bullet(bulletX, bulletY, bulletSprite, velocity, 25, assetHandler);
        bullets.add(enemyBullet);

        return bullets;
    }
}
