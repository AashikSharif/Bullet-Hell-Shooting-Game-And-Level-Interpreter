package com.bullethell.game.systems.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Player;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.utils.Event;
import com.bullethell.game.utils.Renderer;

import java.util.ArrayList;
import java.util.List;

public class PlayerBulletManager {
    private List<Bullet> bullets;
    private AssetHandler assetHandler;
    private Renderer renderer;

    public PlayerBulletManager(AssetHandler assetHandler, Renderer renderer) {
        this.bullets = new ArrayList<>();
        this.assetHandler = assetHandler;
        this.renderer = renderer;
    }

    public void addBullet(Event event) {
        Player player = (Player) event.getSource();
        this.bullets.add(
                new Bullet(
                        player.getPosition().x + (player.sprite.getWidth() / 2),
                        player.getPosition().y + player.sprite.getHeight(),
                        "playerBullet",
                        new Vector2(0, 5),
                        player.damage,
                        assetHandler
                )
        );
    }

    public void update(float deltaTime) {
        List<Bullet> removeList = new ArrayList<>();
        for (Bullet bullet : bullets) {
            if (bullet.getPosition().y > Gdx.graphics.getHeight()) {
                removeList.add(bullet);
            }
            bullet.update();
        }
        if (!removeList.isEmpty()) {
            bullets.removeAll(removeList);
        }
    }

    public void render(SpriteBatch spriteBatch) {
        for (Bullet bullet : bullets) {
            renderer.renderEntity(spriteBatch, bullet, false);
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void clearBullets() {
        bullets.clear();
    }
}

