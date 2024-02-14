package com.bullethell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bullethell.game.BulletHellGame;

public class MainMenuScreen implements Screen {

    private BulletHellGame game;
    private Texture background;
    private Stage stage;
    private Skin skin;

    //transition
    private boolean fadeOut;
    private float fadeOutTime = 5.0f;
    private float fadeTimer = 5.0f;

    public MainMenuScreen(BulletHellGame game) {
        this.game = game;
        this.background = new Texture("drake.jpeg");
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("skin/glassy/skin/glassy-ui.json"));

        // Set the stage as the input processor
        Gdx.input.setInputProcessor(stage);
        loadMenu();

    }

    public void loadMenu() {
        // Create a button
        TextButton startGameButton = new TextButton("Start Game", skin);
        startGameButton.setSize(200, 50);
        startGameButton.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f - 25);

        // Add a listener to the button
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change to the game screen
                fadeOut = true;
                game.setScreen(new GameScreen(game)); // You might need to adjust this call based on your game structure
            }
        });

        // Add the button to the stage
        stage.addActor(startGameButton);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 836, 820);
        game.batch.end();
        // Check if fading out
        if (fadeOut) {
            // Increment fade timer
            fadeTimer += v;

            // Calculate alpha value based on fade progress
            float alpha = Math.min(fadeTimer / fadeOutTime, 1.0f);

            // Set the color with the calculated alpha
            game.batch.setColor(0, 0, 0, alpha);

            // Draw a black rectangle covering the entire screen
            game.batch.begin();
            game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            game.batch.end();

            // Check if fade-out is complete
            if (fadeTimer >= fadeOutTime) {
                // Switch to the game screen
                game.setScreen(new GameScreen(game));
            }
        }
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        skin.dispose();
    }
}
