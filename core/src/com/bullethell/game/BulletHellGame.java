package com.bullethell.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bullethell.game.Enemies.Enemy;
import com.bullethell.game.settings.GlobalSettings;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.JsonUtil;
import com.badlogic.gdx.math.MathUtils;

import java.util.Objects;
public class BulletHellGame extends Game implements InputProcessor {
	private Enemy[] typeB;
	SpriteBatch batch;
	Sprite sprite;
	Texture player;
	Texture backgroundTexture;
	int enemyPositionChange=-1;
	boolean movingLeft = false;
	boolean movingRight = false;
	boolean movingDown = false;
	boolean movingUp = false;
	boolean slowSpeed = false;
	String  enemyType;
	int numGridPoints = 8;  // Adjust as needed
	float angularSeparation = 2 * MathUtils.PI / numGridPoints;
	int timeInSec=0;
	int frameInSec = 0;
	private float centerX;
	private float centerY;
	private float radius = 60f;
	private float angularSpeed = MathUtils.PI;
	public static Settings settings;
	Sprite enemySprite;

	public BulletHellGame() {
		typeB = new Enemy[10]; // Replace 10 with desired number of enemies
	}

	@Override
	public void create () {

		preflight();
		batch = new SpriteBatch();
		backgroundTexture = new Texture("space_background.png");
		player = new Texture("player.png");

		// Create type B enemies with circular movement

		float centerX = Gdx.graphics.getWidth() / 2; // Center X coordinate of the circle
		float centerY = Gdx.graphics.getHeight() / 2; // Center Y coordinate of the circle
		float radius = 60f; // Radius of the circle
		float angularSpeed = MathUtils.PI / 2; // Angular speed in radians per second
		for (int i = 0; i < typeB.length; i++) {
			int gridIndex = i % numGridPoints;  // Assign grid positions cyclically
			float startAngle = gridIndex * angularSeparation;
			float x = centerX + radius * MathUtils.cosDeg(startAngle);
			float y = centerY + radius * MathUtils.sinDeg(startAngle);
			typeB[i] = new Enemy("B", centerX, centerY, radius, angularSpeed, x, y);
		}


/*
		typeB = new Enemy[10];
		for (int i = 0; i < typeB.length; i++) {
			typeB[i] = new Enemy("B", centerX, centerY, radius, angularSpeed);
		}

 */

		//Enemy Sprite
		for (Enemy enemy : typeB) {
			Sprite enemySprite = enemy.getSprite();

			// Set the position based on circular movement
			float startAngle = MathUtils.random(0, (float) Math.PI * 2);
			float x = centerX + radius * MathUtils.cosDeg(startAngle);
			float y = centerY + radius * MathUtils.sinDeg(startAngle);
			enemySprite.setPosition(x, y);

			// Set the height and width (assuming you have these values available)
			enemySprite.setSize(enemySprite.getWidth(), enemySprite.getHeight());
		}


		enemySprite.setPosition(Gdx.graphics.getWidth()/2 - enemySprite.getWidth()/2,
				Gdx.graphics.getHeight()/2 - enemySprite.getHeight()/2);
		Gdx.input.setInputProcessor(this);

		//player sprite
		/*
		sprite.setPosition(Gdx.graphics.getWidth()/2 - sprite.getWidth()/2,
				Gdx.graphics.getHeight()/2 - sprite.getHeight()/2);
		Gdx.input.setInputProcessor(this);

		 */


	}

	@Override
	public void render () {
		frameInSec++;
		if(frameInSec ==60) {timeInSec++; frameInSec=0;}

		GlobalSettings gs = settings.getGlobalSettings();
		float speedFactor = slowSpeed ? gs.getSlowSpeed() : gs.getNormalSpeed();

		//defining boundaries which prevents the player going out from the frame
		float currPositionY = sprite.getY();
		float currPositionX = sprite.getX();
		float enemyPositionX= enemySprite.getX();
		float enemyPositionY= enemySprite.getY();

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


		//checking for positions on X axis
		if(enemyPositionX < 0){
			enemyPositionX = 0; //left edge case
			enemyPositionChange*=-1;
		}else if(enemyPositionX + enemySprite.getWidth() > Gdx.graphics.getWidth()){
			enemyPositionX = Gdx.graphics.getWidth() - enemySprite.getWidth(); //right edge case
			enemyPositionChange*=-1;
		}
		enemySprite.setPosition(enemyPositionX+enemyPositionChange,enemyPositionY);


		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(sprite, sprite.getX(), sprite.getY());
		//System.out.println(timeInSec +" "+frameInSec);

		for (Enemy enemy : typeB) {
			Sprite enemySprite = enemy.getSprite();
			if (enemy.isAlive()) {
				float currentAngle = enemy.getCurrentAngle() + enemy.getAngularSpeed() * Gdx.graphics.getDeltaTime();
				currentAngle = currentAngle % (2 * MathUtils.PI);  // Keep angle within 0 to 2*PI

				float x = centerX + radius * MathUtils.cosDeg(currentAngle);
				float y = centerY + radius * MathUtils.sinDeg(currentAngle);
				//enemy.setPosition(x, y);

				batch.draw(enemy.getEnemyTexture(), enemy.getX(), enemy.getY());
			}
		}
	}


	@Override
	public void dispose () {
		batch.dispose();
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

