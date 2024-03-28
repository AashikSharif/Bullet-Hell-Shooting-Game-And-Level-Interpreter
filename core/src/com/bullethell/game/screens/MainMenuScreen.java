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

    private final BulletHellGame game;
    private final Texture background;
    private final Stage stage;
    private final Skin skin;

    //transition
    private boolean fadeOut;
    private float fadeOutTime = 5.0f;
    private float fadeTimer = 5.0f;


    public MainMenuScreen(BulletHellGame game) {
        this.game = game;
        //this.background = new Texture("space_background.png");
        this.background = new Texture("menu2.jpeg");
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("skin/glassy/skin/glassy-ui.json"));

        // Set the stage as the input processor
        //Gdx.input.setInputProcessor(stage);
        loadMenu();

    }

    public void loadMenu() {
        int size = 50;
        float windowHeight = Gdx.graphics.getHeight();
        float windowWidth = Gdx.graphics.getWidth();


        //start button
        final TextButton startGameButton = new TextButton("Start", skin,"small");
        startGameButton.setSize(size*5, size*2);
        startGameButton.setPosition((windowWidth - startGameButton.getWidth()) / 2, windowHeight - windowHeight/4);
        startGameButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) { /* further, instead of starting the game directly we can
                                                                       make the player to select difficulty mode */
                // Change to the game screen
                fadeOut = true;
                game.setScreen(new GameScreen(game));
            }
        });


        //options button
        final TextButton optionsButton = new TextButton("Options",skin,"small");
        optionsButton.setSize(size*7, size*2);
        optionsButton.setPosition((windowWidth - optionsButton.getWidth())/2,windowHeight-windowHeight/2.4f);
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("clicked");
                ToOption(); //change this to options screen then from there to mode screen
            }
        });


        //exit button
        final TextButton exitButton = new TextButton("Exit",skin,"small");
        exitButton.setSize(size*3,size*2);
        exitButton.setPosition((windowWidth - exitButton.getWidth())/2, windowHeight - windowHeight/1.7f);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        // Add the button to the stage
        stage.addActor(startGameButton);
        stage.addActor(optionsButton);
        stage.addActor(exitButton);
    }

    private void ToOption() {
        game.setScreen(new OptionsScreen(game));
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

//        game.batch.draw(background, 0, 0, (float) Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.batch.draw(background, 0, 0, 1280, 720);
        game.batch.end();
        // Check if fading out
        if (fadeOut) {
            // Increment fade timer
            fadeTimer -= delta;


            // Calculate alpha value based on fade progress
            float alpha = 1.0f - Math.min(fadeTimer / fadeOutTime, 1.0f);
            // Set the color with the calculated alpha
            game.batch.setColor(0, 0, 0, alpha);

            // Draw a black rectangle covering the entire screen
            game.batch.begin();
            game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            game.batch.end();

            // Check if fade-out is complete
            if (fadeTimer <= 0) {
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
