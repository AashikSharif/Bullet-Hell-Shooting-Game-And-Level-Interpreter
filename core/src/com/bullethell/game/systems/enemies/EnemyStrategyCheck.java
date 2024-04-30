package com.bullethell.game.systems.enemies;

import com.bullethell.game.settings.LevelInterpreter;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyStrategyCheck {
    private EnemyManager enemyManager;
    private EnemyBulletManager enemyBulletManager;

    private Map<LevelInterpreter.Enemy, LevelInterpreter.Strategy> lastAppliedStrategy = new HashMap<>();

    public EnemyStrategyCheck(EnemyManager enemyManager, EnemyBulletManager enemyBulletManager) {
        this.enemyManager = enemyManager;
        this.enemyBulletManager = enemyBulletManager;
    }

    public void update(float time) {

        LevelInterpreter levelInterpreter = Settings.getInstance().getLevelInterpreter();
        if (enemyManager.currentWave != -1) {
            LevelInterpreter.Wave wave = levelInterpreter.getWaves().get(enemyManager.currentWave);

            List<LevelInterpreter.Enemy> enemyWaves = wave.getEnemies();

            long currentTimeInSeconds = (long) time;

            for (LevelInterpreter.Enemy enemyWave : enemyWaves) {
                LevelInterpreter.Strategy currentStrategy = getCurrentStrategy(enemyWave.getStrategies(), currentTimeInSeconds);
                if (currentStrategy != null && shouldApplyStrategy(enemyWave, currentStrategy)) {
                    // Apply the strategy found for the current time
                    applyStrategy(enemyWave, currentStrategy.getStrategy());
                    lastAppliedStrategy.put(enemyWave, currentStrategy);
                }
            }

        }

    }

    private LevelInterpreter.Strategy getCurrentStrategy(List<LevelInterpreter.Strategy> strategies, long currentTime) {
        for (LevelInterpreter.Strategy strategy : strategies) {
            long startTime = TimeUtils.convertToSeconds(strategy.getStart());
            long endTime = TimeUtils.convertToSeconds(strategy.getEnd());

            if (currentTime >= startTime && currentTime <= endTime) {
                return strategy;
            }
        }
        return null; // No current strategy applies
    }

    private boolean shouldApplyStrategy(LevelInterpreter.Enemy enemy, LevelInterpreter.Strategy newStrategy) {
        LevelInterpreter.Strategy lastStrategy = lastAppliedStrategy.get(enemy);
        return lastStrategy == null || !lastStrategy.equals(newStrategy);
    }

    private void applyStrategy(LevelInterpreter.Enemy enemy, String strategy) {
        System.out.println("Applying strategy: " + strategy + " to enemy type: " + enemy.getType());
        enemyBulletManager.setCurrentStrategy(strategy);
    }

}
