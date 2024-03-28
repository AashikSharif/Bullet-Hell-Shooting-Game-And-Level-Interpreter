package com.bullethell.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bullethell.game.BulletHellGame;

public class ModeScreen implements Screen {
    private final BulletHellGame game;
    private final Texture background;
    private final Stage stage;
    private final Skin skin;

    ModeScreen(BulletHellGame game){
        this.game = game;
        //this.background = new Texture("space_background.png");
        this.background = new Texture("bg.png");
        this.stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/glassy/skin/glassy-ui.json"));

        // Set the stage as the input processor
        Gdx.input.setInputProcessor(stage);
        loadMenu();

    }


    public void loadMenu(){
        // need to implement the logic for this
        int size = 50;
        float windowHeight = Gdx.graphics.getHeight();
        float windowWidth = Gdx.graphics.getWidth();
    }

    private void backToMainMenu(){
        game.setScreen(new MainMenuScreen(game));
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, (float) Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

    }
}
