package com.bullethell.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.controllers.PlayerController;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Entity;
import com.bullethell.game.entities.Player;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.JsonUtil;

import java.util.List;

public class GameSystem {
    private float time;

    private Settings settings;

    private AssetHandler assetHandler = new AssetHandler();

    private Texture background;

    private Player player;

    private PlayerController playerController;

    private List<Enemy> enemyList;

    public GameSystem() {
        init();
    }

    public void init() {
        this.time = 0;
        loadSettings();
        assetHandler.load(settings.getAssets());

        player = new Player(100, 100, assetHandler);
        playerController = new PlayerController(player, settings.getPlayerSettings());

        background = assetHandler.getAssetTexture("background");
    }

    public void update(float time) {
        this.time += time;
        playerController.listen();
    }

    public void render(SpriteBatch spriteBatch, float time) {
        // combined renders
        update(time);
        renderBackground(spriteBatch);
        renderEntity(spriteBatch, player);
    }

    private void renderEntity(SpriteBatch spriteBatch, Entity entity) {
        spriteBatch.draw(entity.sprite, entity.sprite.getX(), entity.sprite.getY());
    }

    private void renderBackground(SpriteBatch spriteBatch) {
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void renderEnemies() {
    }

    private void loadSettings() {
        JsonUtil jsonUtil = new JsonUtil();
        settings = jsonUtil.deserializeJson("settings/settings.json", Settings.class);
    }
}
