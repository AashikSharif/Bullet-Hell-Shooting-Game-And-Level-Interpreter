package com.bullethell.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.BulletHellGame;
import com.bullethell.game.Patterns.observer.IObservable;
import com.bullethell.game.Patterns.observer.IObserver;
import com.bullethell.game.controllers.SoundController;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Entity;
import com.bullethell.game.entities.Player;
import com.bullethell.game.screens.GameOverScreen;
import com.bullethell.game.screens.GameWinScreen;
import com.bullethell.game.systems.enemies.EnemyBulletManager;
import com.bullethell.game.systems.enemies.EnemyManager;
import com.bullethell.game.systems.enemies.EnemyStrategyCheck;
import com.bullethell.game.systems.player.PlayerBulletManager;
import com.bullethell.game.systems.player.PlayerManager;
import com.bullethell.game.systems.score.ScoreManager;
import com.bullethell.game.utils.Event;
import com.bullethell.game.utils.Explosion;
import com.bullethell.game.utils.Renderer;
import com.bullethell.game.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameObjectManager implements IObserver {
    private EnemyManager enemyManager;
    private PlayerManager playerManager;
    private EnemyBulletManager enemyBulletManager;
    private PlayerBulletManager playerBulletManager;
    private Texture playerLives;
    private BulletHellGame game;
    private float timeInSeconds = 0f;
    private Explosion explosion;
    private AssetHandler assetHandler;
    public GameSystem gameSystem;
    private final SpriteBatch spriteBatch;
    private SoundController soundController;
    private SoundManager soundManager;
    private GameWinScreen gameWinScreen;
    public ScoreManager scoreManager;
    private EnemyStrategyCheck enemyStrategyCheck;

    public GameObjectManager(BulletHellGame game, Renderer renderer, AssetHandler assetHandler) {
        this.assetHandler = assetHandler;
        soundManager = new SoundManager();
        soundController = new SoundController(soundManager);
        enemyBulletManager = new EnemyBulletManager(assetHandler, renderer);
        enemyManager = new EnemyManager(assetHandler, renderer, enemyBulletManager);
        playerManager = new PlayerManager(assetHandler, renderer, this);

        playerBulletManager = new PlayerBulletManager(assetHandler, renderer);
        this.playerLives = assetHandler.getAssetTexture("lives");
        this.scoreManager = new ScoreManager();
        this.enemyStrategyCheck = new EnemyStrategyCheck(enemyManager, enemyBulletManager);
        this.game = game;
        spriteBatch = new SpriteBatch();
        gameSystem = new GameSystem(game, spriteBatch);
        gameWinScreen = new GameWinScreen(game);
    }

    public void update(float deltaTime) {
        checkPlayerWon();
        timeInSeconds += deltaTime;
        enemyManager.update(timeInSeconds, deltaTime, this, getPlayer());

        enemyStrategyCheck.update(timeInSeconds);

        playerBulletManager.update(deltaTime);
        enemyBulletManager.update(deltaTime);
        if (explosion != null && !explosion.isFinished()) {
            explosion.update(deltaTime);
        } else {
            explosion = null;
        }
    }

    public void render(float deltaTime, SpriteBatch spriteBatch) {
        scoreManager.render(spriteBatch);
        playerManager.render(spriteBatch);
        enemyManager.renderEnemies(deltaTime, spriteBatch);
        playerBulletManager.render(spriteBatch);
        enemyBulletManager.render(spriteBatch);
        renderLives(spriteBatch, getPlayer().getLives());
        if (explosion != null && !explosion.isFinished()) {
            explosion.draw(spriteBatch);
        }
    }

    private void checkPlayerWon() {
        //Add winning condition
        //last wave and all the enemies are dead and the time is not up and player's live > 0
        if (timeInSeconds > TimeUtils.convertToSeconds("3:00") && playerManager.getPlayer().getLives() > 0) {
            System.out.println("Player won - Game over");
            gameWinScreen.toWinScreen();
            soundController.stopMusic();
            soundController.playWinSound();
        } else if (timeInSeconds < TimeUtils.convertToSeconds("3:00") && timeInSeconds > TimeUtils.convertToSeconds("0:01")
                && enemyManager.getCurrentWave() == 3 && isEnemyListEmpty() && playerManager.getPlayer().getLives() > 0) {
            System.out.println("Player won - Game over");
            gameWinScreen.toWinScreen();
            soundController.stopMusic();
            soundController.playWinSound();
        }

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

    public EnemyBulletManager getEnemyBulletManager() {
        return enemyBulletManager;
    }

    private void addPlayerBullet(Event event) {
        getPlayerBulletManager().addBullet(event);
    }

    private void addPlayerBomb(Event event) {
        getPlayerBulletManager().addBomb(event);
    }

    private void addEnemyBullet(Event event) {
        getEnemyBulletManager().addBullet(event);
    }

    private void renderLives(SpriteBatch spriteBatch, int lives) {
        for (int i = 0; i < lives; i++) {
            spriteBatch.draw(playerLives, Gdx.graphics.getWidth() - 50f / 2f * (i + 1) - 30, Gdx.graphics.getHeight() - 30, 30, 30);
        }
    }

    private boolean isEnemyListEmpty() {
        return isListEmpty("gruntA") &&
                isListEmpty("midBoss") &&
                isListEmpty("gruntB") &&
                isListEmpty("finalBoss");
    }

    private boolean isListEmpty(String key) {
        List<Enemy> list = enemyManager.getEnemyList().get(key);
        return list == null || list.isEmpty();
    }

    private void playerCollidedWithEnemy() {
        playerManager.getPlayer().resetPosition();
        getEnemyBulletManager().clearBullets();
    }

    private void enemyBulletHitPlayer(Event event) {
        playerManager.getPlayer().resetPosition();
        getEnemyBulletManager().clearBullets();
    }

    private void gameOver(Event event) {
        playerManager.getPlayer().reset();
        getEnemyBulletManager().clearBullets();
        playerBulletManager.clearBullets();
        enemyManager.getEnemyList().clear();
        timeInSeconds = 0f;
        game.setScreen(new GameOverScreen(game));
    }

    private void gameWin(Event event) {
        //checkPlayerWon();
        playerManager.getPlayer().reset();
        getEnemyBulletManager().clearBullets();
        playerBulletManager.clearBullets();
        enemyManager.getEnemyList().clear();
        timeInSeconds = 0f;

    }


    private void explosion(Event event) {
        if (explosion == null || explosion.isFinished()) {
            Entity entity = (Entity) event.getSource();
            explosion = new Explosion(assetHandler, entity.getPosition());
        }
    }


    @Override
    public void onNotify(IObservable observable, Event event) {
        switch (event.getType()) {
            case PLAYER_SHOOT:
                addPlayerBullet(event);
                soundController.playPlayerShootSound();
                break;
            case PLAYER_SHOOT_BOMB:
                addPlayerBomb(event);
                soundController.playPlayerShootSound();
                break;
            case ENEMY_SHOOT:
                addEnemyBullet(event);
                soundController.playEnemyShootSound();
                break;
            case PLAYER_COLLIDED_ENEMY:
                playerCollidedWithEnemy();
                break;
            case ENEMY_BULLET_HIT_PLAYER:
                soundController.playExplosionSound();
                enemyBulletHitPlayer(event);
                break;
            case EXPLOSION:
                soundController.playExplosionSound();
                explosion(event);
                break;
            case GAME_OVER:
                gameOver(event);
                soundController.stopMusic();
                soundController.playLostSound();
                break;
            default:
                break;
        }
    }
}