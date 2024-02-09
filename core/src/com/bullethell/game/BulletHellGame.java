package com.bullethell.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bullethell.game.settings.GlobalSettings;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.JsonUtil;
import java.util.Objects;

public class BulletHellGame extends Game implements InputProcessor {
	SpriteBatch batch;
	Sprite sprite;
	Texture player;
	Texture backgroundTexture;
	Texture bulletTexture;
	Texture texture;
	Enemy enemy_a;

	boolean movingLeft = false;
	boolean movingRight = false;
	boolean movingDown = false;
	boolean movingUp = false;
	boolean slowSpeed = false;

	public static Settings settings;


	@Override
	public void create () {
		preflight();
		batch = new SpriteBatch();

		bulletTexture = new Texture("bullet.png");

		texture = new Texture("typeA.png");
		TextureRegion region = new TextureRegion(texture);

		enemy_a = new Enemy(region);
		enemy_a.setPosition(100, 100);




		backgroundTexture = new Texture("space_background.png");
		player = new Texture("player.png");
		sprite = new Sprite(player);
		sprite.setPosition(Gdx.graphics.getWidth()/2 - sprite.getWidth()/2,
				Gdx.graphics.getHeight()/2 - sprite.getHeight()/2);
		Gdx.input.setInputProcessor(this);


	}

	@Override
	public void render () {

		GlobalSettings gs = settings.getGlobalSettings();
		float speedFactor = slowSpeed ? gs.getSlowSpeed() : gs.getNormalSpeed();
		float delta = Gdx.graphics.getDeltaTime();
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
		if(currPositionX < 0){
			currPositionX = 0; //left edge case
		}else if(currPositionX + sprite.getWidth() > Gdx.graphics.getWidth()){
			currPositionX = Gdx.graphics.getWidth() - sprite.getWidth(); //right edge case
		}

		//checking for positions for Y axis
		if(currPositionY < 0){
			currPositionY = 0;//bottom edge case
		}
		else if(currPositionY + sprite.getHeight() > Gdx.graphics.getHeight()){
			currPositionY = Gdx.graphics.getHeight() - sprite.getHeight(); //top edge case
		}

		sprite.setPosition(currPositionX, currPositionY);

		ScreenUtils.clear(0, 0, 0, 1);
		enemy_a.update();
		batch.begin();
		batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(sprite, sprite.getX(), sprite.getY());
		enemy_a.draw(batch);
		sprite.draw(batch);
		for (Bullet bullet : enemy_a.getBullets()) {
			bullet.draw(batch);
		}


		batch.end();
	}





	@Override
	public void dispose () {
		batch.dispose();
		texture.dispose();
		bulletTexture.dispose();
		player.dispose();
		backgroundTexture.dispose();
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

	private void preflight() {
		JsonUtil jsonUtil = new JsonUtil();
		settings = jsonUtil.deserializeJson("settings/settings.json", Settings.class);
	}
}
