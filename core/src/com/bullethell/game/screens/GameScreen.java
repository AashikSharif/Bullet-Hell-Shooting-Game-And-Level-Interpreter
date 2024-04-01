package com.bullethell.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bullethell.game.BulletHellGame;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.systems.GameSystem;

public class GameScreen implements Screen {
    public SpriteBatch batch;
    final private Viewport viewport;

    public BulletHellGame game;
    public Settings settings;
    Sprite[] enemySprite;
    AssetHandler assetHandler = new AssetHandler();

    GameSystem gameSystem;

    public GameScreen(BulletHellGame game) {
        this.game = game;
        batch = new SpriteBatch();
        this.settings = game.getSettings();
        gameSystem = new GameSystem(game, batch);
        enemySprite = new Sprite[25];
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
    }

    @Override
    public void show() {
        gameSystem.init();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        gameSystem.render(delta);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        assetHandler.dispose();
    }
}
