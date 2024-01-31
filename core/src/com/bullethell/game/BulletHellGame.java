package com.bullethell.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class BulletHellGame extends Game implements InputProcessor {
	SpriteBatch batch;
	Sprite sprite;
	Texture player;
	boolean movingLeft = false;
	boolean movingRight = false;
	boolean movingDown = false;
	boolean movingUp = false;
	boolean slowSpeed = false;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Texture("player.png");
		sprite = new Sprite(player);
		sprite.setPosition(Gdx.graphics.getWidth()/2 - sprite.getWidth()/2,
				Gdx.graphics.getHeight()/2 - sprite.getHeight()/2);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		if(slowSpeed) {
			if (movingLeft)
				sprite.translateX(-1f);
			if (movingRight)
				sprite.translateX(1f);
			if (movingUp)
				sprite.translateY(1f);
			if (movingDown)
				sprite.translateY(-1f);
		}else{
			if (movingLeft)
				sprite.translateX(-5f);
			if (movingRight)
				sprite.translateX(5f);
			if (movingUp)
				sprite.translateY(5f);
			if (movingDown)
				sprite.translateY(-5f);
		}

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(sprite, sprite.getX(), sprite.getY());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.LEFT)
			movingLeft = true;
		if(keycode == Input.Keys.RIGHT)
			movingRight = true;
		if(keycode == Input.Keys.UP)
			movingUp = true;
		if(keycode == Input.Keys.DOWN)
			movingDown = true;
		if(keycode == Input.Keys.SPACE)
			slowSpeed = true;

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.LEFT)
			movingLeft = false;
		if(keycode == Input.Keys.RIGHT)
			movingRight = false;
		if(keycode == Input.Keys.UP)
			movingUp = false;
		if(keycode == Input.Keys.DOWN)
			movingDown = false;
		if(keycode == Input.Keys.SPACE)
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
}
