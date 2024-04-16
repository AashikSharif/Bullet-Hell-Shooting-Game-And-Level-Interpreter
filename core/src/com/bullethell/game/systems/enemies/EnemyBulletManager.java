package com.bullethell.game.systems.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Player;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.utils.Event;
import com.bullethell.game.utils.Renderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyBulletManager {
    private List<Bullet> bullets;
    private AssetHandler assetHandler;
    private Renderer renderer;

    public EnemyBulletManager(AssetHandler assetHandler, Renderer renderer) {
        this.bullets = new ArrayList<>();
        this.assetHandler = assetHandler;
        this.renderer = renderer;
    }

    public void render(SpriteBatch spriteBatch) {
        for (Bullet bullet : bullets) {
            spriteBatch.draw(bullet.sprite, bullet.getPosition().x, bullet.getPosition().y);
        }
    }

    public void update(float deltaTime) {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
            if (bullet.getPosition().y < 0 || bullet.getPosition().y > Gdx.graphics.getHeight()) {
                iterator.remove();
            }
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void clearBullets() {
        bullets.clear();
    }

    public void addBullet(Event event) {
        Enemy enemy = (Enemy) event.getSource();
        Player player = (Player) event.getDestination();
        Vector2 direction = new Vector2(
                player.getPosition().x + player.sprite.getWidth() / 2 - (enemy.getPosition().x + enemy.sprite.getWidth() / 2),
                player.getPosition().y + player.sprite.getHeight() / 2 - (enemy.getPosition().y + enemy.sprite.getHeight() / 2)
        ).nor();
        float bulletSpeed = 3;
        Vector2 velocity = direction.scl(bulletSpeed);

        float bulletX = enemy.getPosition().x + (enemy.sprite.getWidth() / 2) - Bullet.HITBOX_WIDTH / 2;
        float bulletY = enemy.getPosition().y - Bullet.HITBOX_HEIGHT;
        Bullet enemyBullet = new Bullet(bulletX, bulletY, "bullet", velocity, 25, assetHandler);
        bullets.add(enemyBullet);
    }

}
