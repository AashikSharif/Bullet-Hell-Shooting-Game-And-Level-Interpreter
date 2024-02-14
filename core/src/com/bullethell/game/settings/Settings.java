package com.bullethell.game.settings;

import java.util.Map;

public class Settings {
    private GlobalSettings global;
    private PlayerSettings playerSettings;

    private Map<String, String> assets;

    public Settings () {}

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
}
