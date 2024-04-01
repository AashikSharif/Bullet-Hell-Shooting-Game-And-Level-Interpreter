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

public class GameWinScreen implements Screen {
    private final BulletHellGame game;
    private final Texture background;
    private final Stage stage;
    private final Skin skin;

    public GameWinScreen(BulletHellGame game) {
        this.game = game;
        this.background = new Texture("game_win.jpeg");
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("skin/glassy/skin/glassy-ui.json"));
        Gdx.input.setInputProcessor(stage);
        loadMenu();
    }

    private void loadMenu() {
        float windowHeight = Gdx.graphics.getHeight();
        float windowWidth = Gdx.graphics.getWidth();

        // Back to Main Menu
        TextButton backToMenu = new TextButton("Back to Main Menu", skin, "small");
        backToMenu.setSize(200, 50);
        backToMenu.setPosition(windowWidth - 500, 50);
        backToMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        //Exit Button
        TextButton exitGame = new TextButton("Exit Game",skin,"small");
        exitGame.setSize(200,50);
        exitGame.setPosition(windowWidth -250,50);
        exitGame.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y){
                System.exit(0);
            }

        });

        stage.addActor(backToMenu);
        stage.addActor(exitGame);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
//        game.batch.draw(background, 0, 0, (float) Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.batch.draw(background, 0, 0, 1280, 720);
        game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
        background.dispose();
        skin.dispose();
    }
}
