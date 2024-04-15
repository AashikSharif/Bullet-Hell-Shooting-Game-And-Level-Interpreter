package com.bullethell.game.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.Patterns.observer.IObservable;
import com.bullethell.game.Patterns.observer.IObserver;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Entity;
import com.bullethell.game.entities.Player;
import com.bullethell.game.systems.enemies.EnemyManager;
import com.bullethell.game.systems.player.PlayerBulletManager;
import com.bullethell.game.systems.player.PlayerManager;
import com.bullethell.game.utils.Event;
import com.bullethell.game.utils.Renderer;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class GameObjectManager implements IObserver {
    private List<Bullet> enemyBullets;
    private PlayerManager playerManager;
    private PlayerBulletManager playerBulletManager;
    private EnemyManager enemyManager;

    public GameObjectManager(Renderer renderer, AssetHandler assetHandler) {
        enemyManager = new EnemyManager(assetHandler, renderer);
        playerManager = new PlayerManager(assetHandler, renderer, this);
        enemyBullets = new ArrayList<>();
        playerBulletManager = new PlayerBulletManager(assetHandler, renderer);
    }

    public void update(float seconds, float deltaTime) {
//        playerManager.update(deltaTime);
        enemyManager.update(seconds, deltaTime, this);
        playerBulletManager.update(deltaTime);
    }

    public void render(float deltaTime, SpriteBatch spriteBatch) {
        playerManager.render(spriteBatch);
        enemyManager.renderEnemies(deltaTime, spriteBatch);
        playerBulletManager.render(spriteBatch);
    }

    public Map<String, ArrayList<Enemy>> getEnemyList() {
        return enemyManager.getEnemyList();
    }

    public Player getPlayer() {
        return playerManager.getPlayer();
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public PlayerBulletManager getPlayerBulletManager() {
        return playerBulletManager;
    }

    public void addPlayerBullet (Player player) {
        getPlayerBulletManager().addBullet(player);
    }

    @Override
    public void onNotify(IObservable observable, Event event) {
        if (observable instanceof Entity) {
            Entity entity = ((Entity) observable);

            switch (event.getType()) {
                case PLAYER_SHOOT:
                    addPlayerBullet(playerManager.getPlayer());
                    break;
                default:
                    break;
            }
        }
    }
}
