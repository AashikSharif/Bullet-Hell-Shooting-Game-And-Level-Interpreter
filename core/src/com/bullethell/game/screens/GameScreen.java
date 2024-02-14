package com.bullethell.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bullethell.game.BulletHellGame;
import com.bullethell.game.settings.GlobalSettings;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.JsonUtil;

import java.util.Objects;

public class GameScreen implements Screen, InputProcessor {
    public SpriteBatch batch;
    public Sprite sprite;
    public Texture player;
    public Texture backgroundTexture;
    public boolean movingLeft = false;
    public boolean movingRight = false;
    public boolean movingDown = false;
    public boolean movingUp = false;
    public boolean slowSpeed = false;
    private Viewport viewport;
    private OrthographicCamera camera;
    public BulletHellGame game;

    public Settings settings;

    public GameScreen(BulletHellGame game) {
        this.game = game;
        // Initialize your game components here
        batch = new SpriteBatch();
        backgroundTexture = new Texture("space_background.png"); // Make sure to have a player.png in your assets
        player = new Texture("player.png");
        sprite = new Sprite(player);
        this.settings = game.getSettings();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

    }

    @Override
    public void show() {
        sprite.setPosition((float) Gdx.graphics.getWidth() /2 - sprite.getWidth()/2,
                (float) Gdx.graphics.getHeight() /2 - sprite.getHeight()/2);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        updatePlayerPosition(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //batch.draw(sprite, sprite.getX(), sprite.getY());
        batch.draw(sprite, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.draw(batch);
        batch.end();
    }

    private void updatePlayerPosition(float delta) {

        GlobalSettings gs = settings.getGlobalSettings();
        float speedFactor = slowSpeed ? gs.getSlowSpeed() : gs.getNormalSpeed();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        //defining boundaries which prevents the player going out from the frame
        float currPositionX = sprite.getX();
        float currPositionY = sprite.getY();

        if (movingLeft) {
            currPositionX -= speedFactor;
        }
        if (movingRight) {
            currPositionX += speedFactor;
        }
        if (movingUp) {
            currPositionY += speedFactor;
        }
        if (movingDown) {
            currPositionY -= speedFactor;
        }

        //checking for positions on X axis
        if(currPositionX < 0) {
            currPositionX = 0;
        } else if(currPositionX + sprite.getWidth() > screenWidth) {
            currPositionX = screenWidth - sprite.getWidth();
        }

        if(currPositionY < 0) {
            currPositionY = 0;
        } else if(currPositionY + sprite.getHeight() > screenHeight) {
            currPositionY = screenHeight - sprite.getHeight();
        }

        sprite.setPosition(currPositionX, currPositionY);
    }

    @Override
	public boolean keyUp(int keycode) {
		String key = Input.Keys.toString(keycode);
		GlobalSettings gs = settings.getGlobalSettings();

		if(Objects.equals(key, gs.getMoveLeft()))
			movingLeft = false;
		if(Objects.equals(key, gs.getMoveRight()))
			movingRight = false;
		if(Objects.equals(key, gs.getMoveUp()))
			movingUp = false;
		if(Objects.equals(key, gs.getMoveDown()))
			movingDown = false;
		if(Objects.equals(key, gs.getSlowMode()))
			slowSpeed = false;
		return false;
	}
    @Override
	public boolean keyDown(int keycode) {
		String key = Input.Keys.toString(keycode);
		GlobalSettings gs = settings.getGlobalSettings();

		if(Objects.equals(key, gs.getMoveLeft()))
			movingLeft = true;
		if(Objects.equals(key, gs.getMoveRight()))
			movingRight = true;
		if(Objects.equals(key, gs.getMoveUp()))
			movingUp = true;
		if(Objects.equals(key, gs.getMoveDown()))
			movingDown = true;
		if(Objects.equals(key, gs.getSlowMode()))
			slowSpeed = true;

		return false;
	}
    private void preflight() {
        JsonUtil jsonUtil = new JsonUtil();
        settings = jsonUtil.deserializeJson("settings/settings.json", Settings.class);
    }
	@Override
	public boolean keyTyped(char c) {
		return false;
	}

	@Override
	public boolean touchDown(int i, int i1, int i2, int i3) {
		return false;
	}

	@Override
	public boolean touchUp(int i, int i1, int i2, int i3) {
		return false;
	}

	@Override
	public boolean touchCancelled(int i, int i1, int i2, int i3) {
		return false;
	}

	@Override
	public boolean touchDragged(int i, int i1, int i2) {
		return false;
	}

	@Override
	public boolean mouseMoved(int i, int i1) {
		return false;
	}

	@Override
	public boolean scrolled(float v, float v1) {
		return false;
	}
    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
