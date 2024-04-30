package com.bullethell.game.settings;

import java.util.List;
import java.util.Map;

public class LevelInterpreter {
    private String difficulty;
    private List<Wave> waves;

    private Map<String, DifficultySettings> difficultySettings;

    // getters and setters


    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<Wave> getWaves() {
        return waves;
    }

    public void setWaves(List<Wave> waves) {
        this.waves = waves;
    }

    public Map<String, DifficultySettings> getDifficultySettings() {
        return difficultySettings;
    }

    public void setDifficultySettings(Map<String, DifficultySettings> difficultySettings) {
        this.difficultySettings = difficultySettings;
    }

    public static class Wave {
        private String start;
        private String end;
        private List<Enemy> enemies;

        // getters and setters

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public List<Enemy> getEnemies() {
            return enemies;
        }

        public void setEnemies(List<Enemy> enemies) {
            this.enemies = enemies;
        }
    }

    public static class Enemy {
        private String type;
        private int count;
        private int lives;
        private int health;

        // getters and setters

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getLives() {
            return lives;
        }

        public void setLives(int lives) {
            this.lives = lives;
        }

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = health;
        }
    }

    public static class DifficultySettings {
        private int bulletSpeed;

        // Getters and setters
        public int getBulletSpeed() {
            return bulletSpeed;
        }

        public void setBulletSpeed(int bulletSpeed) {
            this.bulletSpeed = bulletSpeed;
        }
    }
}
