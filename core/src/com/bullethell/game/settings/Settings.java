package com.bullethell.game.settings;

public class Settings {
    private GlobalSettings global;
    private PlayerSettings playerSettings;
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
}
