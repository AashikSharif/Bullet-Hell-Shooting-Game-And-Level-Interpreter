package com.bullethell.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.bullethell.game.screens.PlayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
public class BulletHellGame extends Game {
	private SpriteBatch batch;
	private Texture texture;
	private Texture bulletTexture;
	private Array<Bullet> bullets;
	private Enemy enemy_a;


	@Override
	public void create () {
		batch = new SpriteBatch();
		bulletTexture = new Texture("bullet.png");
		TextureRegion bulletRegion = new TextureRegion(bulletTexture);
		bullets = new Array<>();
		texture = new Texture("type A.png");
		TextureRegion region = new TextureRegion(texture);

		enemy_a = new Enemy(region);
		enemy_a.setPosition(100, 100);

		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {

			}

		}, 0, 1.2f);

		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		enemy_a.draw(batch);



		for (Bullet bullet : bullets) {
			bullet.update(Gdx.graphics.getDeltaTime());
			bullet.draw(batch);
		}
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		texture.dispose();
		bulletTexture.dispose();
	}


}
