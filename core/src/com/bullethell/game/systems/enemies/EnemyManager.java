package com.bullethell.game.systems.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.Patterns.observer.IObserver;
import com.bullethell.game.controllers.MovementController;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Player;
import com.bullethell.game.settings.LevelInterpreter;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.utils.Event;
import com.bullethell.game.utils.Renderer;
import com.bullethell.game.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyManager {
    private Renderer renderer;
    public int currentWave = -1;
    public EnemySpawner enemySpawner;
    private LevelInterpreter levelInterpreter;
    private MovementController movementController;
    private Map<String, ArrayList<Enemy>> enemyList;
    private AssetHandler assetHandler;
    boolean isCollided = false;
    int time = 3, frames = 60, counter=0;

    public EnemyManager(AssetHandler assetHandler, Renderer renderer) {
        this.renderer = renderer;
        this.enemyList = new HashMap<>();
        this.assetHandler = assetHandler;
        this.movementController = new MovementController();
        this.levelInterpreter = Settings.getInstance().getLevelInterpreter();
        this.enemySpawner = new EnemySpawner(levelInterpreter);
    }

    public Map<String, ArrayList<Enemy>> getEnemyList() {
        return enemyList;
    }

    public MovementController getMovementController() {
        return movementController;
    }

    public void update(float seconds, float deltaTime, IObserver observer, Player player) {
        removeOldEnemies(seconds);
        checkWaveAndSpawn(seconds, assetHandler, observer);
        moveEnemies(deltaTime);
        shoot(deltaTime, player);
    }

    private void checkWaveAndSpawn(float seconds, AssetHandler assetHandler, IObserver observer) {

        for (int i = 0; i < levelInterpreter.getWaves().size(); i++) {
            LevelInterpreter.Wave wave = levelInterpreter.getWaves().get(i);
            if (seconds >= TimeUtils.convertToSeconds(wave.getStart()) && seconds <= TimeUtils.convertToSeconds(wave.getEnd())) {
                if (currentWave != i) {
                    System.out.println("Spawning for wave" + i);
                    spawnEnemies(wave, assetHandler, observer);
                    currentWave = i;
                    break;
                }
            }
        }
    }

    private void removeOldEnemies(float seconds) {
        for (int i = 0; i < levelInterpreter.getWaves().size(); i++) {
            LevelInterpreter.Wave wave = levelInterpreter.getWaves().get(i);
            if (TimeUtils.convertToSeconds(wave.getEnd()) <= seconds) {
                for (LevelInterpreter.Enemy enemy : wave.getEnemies()) {
                    if (enemyList.get(enemy.getType()) != null) {
                        enemyList.remove(enemy.getType());
                    }
                }
            }
        }
    }

    private void spawnEnemies(LevelInterpreter.Wave wave, AssetHandler assetHandler, IObserver observer) {
        for (LevelInterpreter.Enemy enemy : wave.getEnemies()) {
            if (enemyList.get(enemy.getType()) == null) {
                enemyList.put(enemy.getType(), enemySpawner.getSpawn(enemy, assetHandler, movementController, observer));
            }
        }
    }

    private void moveEnemies(float deltaTime) {
        if (currentWave != -1) {
            LevelInterpreter.Wave wave = levelInterpreter.getWaves().get(currentWave);
            for (LevelInterpreter.Enemy enemy : wave.getEnemies()) {
                String type = enemy.getType();
                ArrayList<Enemy> enemies = enemyList.get(type);

                if (enemies != null && !enemies.isEmpty()) {
                    movementController.update(deltaTime);
                }
            }
        }
    }

    public void renderEnemies(float deltaTime, SpriteBatch spriteBatch) {
        if (currentWave != -1) {
            LevelInterpreter.Wave wave = levelInterpreter.getWaves().get(currentWave);
            for (LevelInterpreter.Enemy enemy : wave.getEnemies()) {
                String type = enemy.getType();
                ArrayList<Enemy> enemies = enemyList.get(type);

                if (enemies != null && !enemies.isEmpty()) {
                    for (Enemy e : enemies) {
                        renderer.renderEntity(spriteBatch, e, false);
                    }
                }
            }
        }
    }

    private void shoot(float deltaTime, Player player) {
        for (ArrayList<Enemy> enemies : enemyList.values()) {
            for (Enemy enemy : enemies) {
                boolean isEnemyOnScreen = enemy.getPosition().y + enemy.sprite.getHeight() > 0 &&
                        enemy.getPosition().y < 720 &&
                        enemy.getPosition().x + enemy.sprite.getWidth() > 0 &&
                        enemy.getPosition().x < 1280;
                if (isEnemyOnScreen && enemy.isReadyToShoot(deltaTime)) {

                    if(!isCollided) {
                        enemy.shoot(new Event(Event.Type.ENEMY_SHOOT, enemy, player));

                        enemy.resetShootTimer();
                    }

                }
            }
            counter++;
        }
        if (counter == time * frames) {isCollided = false;}

    }

    public int getCurrentWave()
    {return currentWave;}
}
