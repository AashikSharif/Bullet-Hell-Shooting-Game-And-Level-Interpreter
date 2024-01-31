package com.bullethell.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.screens.PlayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
public class BulletHellGame extends Game {
	public SpriteBatch batch;
	private Texture texture;
	private enemies enemy_a;
	@Override
	public void create () {
		batch = new SpriteBatch();
		texture = new Texture("type A.png");
		TextureRegion region = new TextureRegion(texture);
		enemy_a = new enemies(region);
		enemy_a.setPosition(100, 100);
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		enemy_a.draw(batch);
		batch.end();
		//super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		texture.dispose();
	}

}
