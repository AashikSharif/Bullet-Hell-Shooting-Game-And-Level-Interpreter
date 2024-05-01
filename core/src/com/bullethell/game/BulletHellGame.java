package com.bullethell.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.screens.MainMenuScreen;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.JsonUtil;

public class BulletHellGame extends Game {
    public SpriteBatch batch;

    public Settings settings;

    @Override
    public void create() {
        preflight();
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));

    }

    public Settings getSettings() {
        return settings;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();

    }

    private void preflight() {
        JsonUtil jsonUtil = new JsonUtil();
        settings = jsonUtil.deserializeJson("settings/settings.json", Settings.class);
    }
}