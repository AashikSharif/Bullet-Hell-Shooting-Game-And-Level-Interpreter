package com.bullethell.game.systems.Enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.controllers.MovementController;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.settings.LevelInterpreter;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.utils.Renderer;
import com.bullethell.game.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnemyManager {
    public EnemySpawner enemySpawner;
    private Renderer renderer;
    private LevelInterpreter levelInterpreter;
    private Map<String, ArrayList<Enemy>> enemyList;
    private MovementController movementController;
    public int currentWave = -1;

    public EnemyManager (LevelInterpreter levelInterpreter, Renderer renderer) {
        enemyList = new HashMap<>();
        enemySpawner = new EnemySpawner(levelInterpreter);
        this.levelInterpreter = levelInterpreter;
        this.renderer = renderer;
        this.movementController = new MovementController();
    }

    public Map<String, ArrayList<Enemy>> getEnemyList() {
        return enemyList;
    }

    public MovementController getMovementController() {
        return movementController;
    }

    public void update (float seconds, float deltaTime, AssetHandler assetHandler, SpriteBatch spriteBatch) {
        removeOldEnemies(seconds);
        checkWaveAndSpawn(seconds, assetHandler);
        moveEnemies(deltaTime);
//        renderEnemies(deltaTime, spriteBatch);
    }

    private void checkWaveAndSpawn(float seconds, AssetHandler assetHandler) {

        for (int i = 0; i < levelInterpreter.getWaves().size(); i++) {
            LevelInterpreter.Wave wave = levelInterpreter.getWaves().get(i);
            if (seconds >= TimeUtils.convertToSeconds(wave.getStart()) && seconds <= TimeUtils.convertToSeconds(wave.getEnd())) {
                if (currentWave != i) {
                    System.out.println("Spawning for wave" + i);
                    spawnEnemies(wave, assetHandler);
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
                for (LevelInterpreter.Enemy enemy: wave.getEnemies()) {
                    if (enemyList.get(enemy.getType()) != null) {
                        enemyList.remove(enemy.getType());
                    }
                }
            }
        }
    }
    private void spawnEnemies(LevelInterpreter.Wave wave, AssetHandler assetHandler) {
        for (LevelInterpreter.Enemy enemy: wave.getEnemies()) {
            if (enemyList.get(enemy.getType()) == null) {
                enemyList.put(enemy.getType(), enemySpawner.getSpawn(enemy, assetHandler, movementController));
            }
        }
    }

    private void moveEnemies(float deltaTime) {
        if (currentWave != -1) {
            LevelInterpreter.Wave wave = levelInterpreter.getWaves().get(currentWave);
            for (LevelInterpreter.Enemy enemy: wave.getEnemies()) {
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
            for (LevelInterpreter.Enemy enemy: wave.getEnemies()) {
                String type = enemy.getType();
                ArrayList<Enemy> enemies = enemyList.get(type);

                if (enemies != null && !enemies.isEmpty()) {
                    for (Enemy e : enemies) {
                        renderer.renderEntity(spriteBatch, e, true);
                    }
                }
            }
        }
    }
}
