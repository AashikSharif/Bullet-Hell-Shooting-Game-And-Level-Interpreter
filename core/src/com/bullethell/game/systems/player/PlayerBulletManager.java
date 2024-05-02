package com.bullethell.game.systems.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Player;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.utils.Event;
import com.bullethell.game.utils.Renderer;
import com.bullethell.game.entities.BombBullet;

import java.util.ArrayList;
import java.util.List;

public class PlayerBulletManager {
    private List<Bullet> bullets;
    private List<BombBullet> bombBullets = new ArrayList<>();
    private int maxBombs = 3;  // Maximum number of bombs allowed at a time
    private AssetHandler assetHandler;
    private Renderer renderer;

    public PlayerBulletManager(AssetHandler assetHandler, Renderer renderer) {
        this.bullets = new ArrayList<>();
        this.assetHandler = assetHandler;
        this.renderer = renderer;
    }

    public void addBullet(Event event) {
        Player player = (Player) event.getSource();
            Bullet bullet = new Bullet(
                    player.getPosition().x + player.sprite.getWidth() / 2,
                    player.getPosition().y + player.sprite.getHeight(),
                    "bullet",
                    new Vector2(0, 5),
                    player.damage,
                    assetHandler
            );
            bullets.add(bullet);
    }
    public void addBomb(Event event) {
        if (bombBullets.size() >= maxBombs) {
            System.out.println("Maximum number of bombs reached.");
            return;
        }
        Player player = (Player) event.getSource();
            BombBullet bombBullet = new BombBullet(
                    player.getPosition().x + player.sprite.getWidth() / 2,
                    player.getPosition().y + player.sprite.getHeight(),
                    "bomb",
                    new Vector2(0, 5),
                    150,
                    assetHandler
            );
            bullets.add(bombBullet);
    }

    public void update(float deltaTime) {
        updateBullets(deltaTime, bullets);
        updateBullets(deltaTime, bombBullets);
    }

    private <T extends Bullet> void updateBullets(float deltaTime, List<T> bullets) {
        List<T> removeList = new ArrayList<>();
        for (T bullet : bullets) {
            bullet.update();
            if (bullet.getPosition().y > Gdx.graphics.getHeight() || bullet.isExpired()) {
                removeList.add(bullet);
            }
        }
        bullets.removeAll(removeList);
    }
    public void render(SpriteBatch spriteBatch) {
        for (Bullet bullet : bullets) {
            renderer.renderEntity(spriteBatch, bullet, false);
        }
        for (BombBullet bombBullet : bombBullets) {
            renderer.renderEntity(spriteBatch, bombBullet, true); // Render bomb bullets
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
    public List<BombBullet> getBombs() {
        return bombBullets;
    }
    public void clearBullets() {
        bullets.clear();
    }
}

