package com.bullethell.game.settings;

import com.bullethell.game.utils.JsonUtil;

import java.util.Map;

public class Settings {
    private static Settings instance;
    private GlobalSettings global;
    private PlayerSettings playerSettings;
    private Map<String, String> assets;
    private LevelInterpreter levelInterpreter;
    private int highScore;

    public Settings () {}

    public static synchronized Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
            instance.loadSettings();
        }
        return instance;
    }

    private void loadSettings() {
        JsonUtil jsonUtil = new JsonUtil();
        instance = jsonUtil.deserializeJson("settings/settings.json", Settings.class);
        if (instance == null) {
            instance = new Settings();
        }
    }

    public GlobalSettings getGlobalSettings() {
        return global;
    }

    public void setGlobalSettings(GlobalSettings global) {
        this.global = global;
    }

    public PlayerSettings getPlayerSettings() {
        return playerSettings;
    }

    public void setPlayerSettings(PlayerSettings playerSettings) {
        this.playerSettings = playerSettings;
    }

    public Map<String, String> getAssets() {
        return assets;
    }

    public void setAssets(Map<String, String> assets) {
        this.assets = assets;
    }

    public LevelInterpreter getLevelInterpreter() {
        return levelInterpreter;
    }

    public void setLevelInterpreter(LevelInterpreter levelInterpreter) {
        this.levelInterpreter = levelInterpreter;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
