package com.bullethell.game.systems.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.Patterns.strategy.*;
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
    private BulletStrategy bulletStrategy;

    public EnemyBulletManager(AssetHandler assetHandler, Renderer renderer) {
        this.bullets = new ArrayList<>();
        this.assetHandler = assetHandler;
        this.renderer = renderer;
        //this.bulletStrategy = new FibonacciBulletStrategy(10, 35);
        this.bulletStrategy = new DefaultBulletStrategy();
        //this.bulletStrategy = new StarBulletStrategy(5);
//        this.bulletStrategy = new SpiralBulletStrategy(2,35);
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

        List<Bullet> newBullets = bulletStrategy.createBullets(enemy, player, assetHandler);
        bullets.addAll(newBullets);
    }

}
