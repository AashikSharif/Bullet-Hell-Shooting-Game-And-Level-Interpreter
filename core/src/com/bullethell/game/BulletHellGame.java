package com.bullethell.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bullethell.game.Enemies.Enemy;
import com.bullethell.game.Enemies.BasicEnemies;
import com.bullethell.game.controllers.PlayerController;
import com.bullethell.game.entities.Player;
import com.bullethell.game.settings.GlobalSettings;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.JsonUtil;
import java.util.Objects;
public class BulletHellGame extends Game {
	SpriteBatch batch;
	SpriteBatch enemyBatch;

	Sprite sprite;
	Sprite enemySprite[];
	Sprite midBossSprite;
	Sprite finalBossSprite;
//	Texture player;
	Texture backgroundTexture;
	int enemyPositionChange=-1;
	boolean movingLeft = false;
	boolean movingRight = false;
	boolean movingDown = false;
	boolean movingUp = false;
	boolean slowSpeed = false;
	String  enemyType;

	int timeInSec=0;
	int frameInSec = 0;
	Enemy[] typeA; //25 type A enemies
	Enemy []typeB;
	Enemy midBoss;
	Enemy finalBoss;
	public static Settings settings;

	Player player;
	PlayerController playerController;

	@Override
	public void create () {
		preflight();
		batch = new SpriteBatch();
		player = new Player(100, 100);
		playerController = new PlayerController(player, settings.getPlayerSettings());

		enemyBatch = new SpriteBatch();
		backgroundTexture = new Texture("space_background.png");
//		player = new Texture("player.png");

		enemySprite =new Sprite[25];
        typeA = new Enemy[25];
        typeB = new Enemy[10];  //10 type B enemies
		midBoss = new Enemy("C");
		finalBoss = new Enemy("D");


		for (int i=0,j=(-1);i<25;i++)
		{
			if(i%5==0) j++;
			typeA[i]=new Enemy("A");
			enemySprite[i] = new Sprite(typeA[i].enemy,200,200);
			enemySprite[i].setPosition((Gdx.graphics.getWidth()/10)*(i%5) - enemySprite[i].getWidth()/2,
					Gdx.graphics.getHeight()/10*(10-j%5+1) - enemySprite[i].getHeight()/2);

			System.out.println(enemySprite[i].getWidth()+"  "+enemySprite[i].getHeight());

		}

		for(int i=0;i<10;i++) {
			typeB[i] = new Enemy("B");
		}


		midBossSprite = new Sprite(midBoss.enemy,200,200);
		finalBossSprite = new Sprite(finalBoss.enemy,200,200);

		midBossSprite.setPosition(Gdx.graphics.getWidth()/2 - midBossSprite.getWidth()/2,
				Gdx.graphics.getHeight() - midBossSprite.getHeight()/2);
		finalBossSprite.setPosition(Gdx.graphics.getWidth()/2 - finalBossSprite.getWidth()/2,
				Gdx.graphics.getHeight() - finalBossSprite.getHeight()/2);

//		sprite = new Sprite(player);
//		Gdx.input.setInputProcessor(this);


		//player sprite
//		sprite.setPosition(Gdx.graphics.getWidth()/2 - sprite.getWidth()/2,
//				Gdx.graphics.getHeight()*20/100 - sprite.getHeight()/2);
//		Gdx.input.setInputProcessor(this);

	}

	@Override
	public void render () {
		frameInSec++;
		if(frameInSec ==60) {timeInSec++; frameInSec=0;}

		GlobalSettings gs = settings.getGlobalSettings();
		float speedFactor = slowSpeed ? gs.getSlowSpeed() : gs.getNormalSpeed();

		//defining boundaries which prevents the player going out from the frame
		float currPositionY = player.sprite.getY();
		float currPositionX = player.sprite.getX();

		ScreenUtils.clear(0, 0, 0, 1);


		enemyBatch.begin();
		enemyBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (timeInSec < 5)
		{
			float enemyPositionX= enemySprite[0].getX();
			float enemyPositionY= enemySprite[0].getY();
			//System.out.println("X: "+enemySprite[0].getX()+"\nY: "+Gdx.graphics.getHeight()+"\n");
			//checking for positions on X axis
			if(enemyPositionX < -180){
				//enemyPositionX = 0; //left edge case
				enemyPositionChange*=-1;
			}else if(enemySprite[0].getX()+180 +460 > Gdx.graphics.getWidth()) {
				//enemyPositionX = Gdx.graphics.getWidth() - enemySprite.getWidth(); //right edge case
				enemyPositionChange *= -1;
			}
			enemySprite[0].setPosition(enemyPositionX+enemyPositionChange,enemyPositionY);
			//System.out.println(timeInSec +" "+frameInSec +" XD");
			//for typeA enemies
			enemyType =	 "A";

			for(int i=0,j=-1;i<25;i++)
			{
				if(i%5==0) j++;
				enemyBatch.draw(enemySprite[i],enemyPositionX+enemyPositionChange+(i%5)*100,j*50+250);
				//getX()+150*(i%5),enemySprite[i].getY()+150*(j%5));
			}
		}
		//else enemySprite.getTexture().dispose();
		else if(timeInSec >= 5 && timeInSec <=10)
		{
			float enemyPositionX = midBossSprite.getX();
			float enemyPositionY = midBossSprite.getY();
			//for mid-boss
			enemyType = "C";
			enemyBatch.draw(midBossSprite,enemyPositionX,enemyPositionY);
		}
		else if(timeInSec >10 && timeInSec<15)
		{
			//for typeB enemies
			enemyType = "B";
		}
		else if(timeInSec  >=15 && timeInSec <20)
		{
			enemyType = "D";
			//for Final Boss code

			float enemyPositionX = finalBossSprite.getX();
			float enemyPositionY = finalBossSprite.getY();
			//for mid-boss
			enemyType = "C";
			enemyBatch.draw(finalBossSprite,enemyPositionX,enemyPositionY);
		}

		enemyBatch.end();




		playerController.listen();

		//checking for positions on X axis
//		if(currPositionX < 0){
//			currPositionX = 0; //left edge case
//		}else if(currPositionX + player.sprite.getWidth() > Gdx.graphics.getWidth()){
//			currPositionX = Gdx.graphics.getWidth() - player.sprite.getWidth(); //right edge case
//		}
//
////		checking for positions for Y axis
//		if(currPositionY < 0){
//			currPositionY = 0;//bottom edge case
//		}
//		else if(currPositionY + player.sprite.getHeight() > Gdx.graphics.getHeight()){
//			currPositionY = Gdx.graphics.getHeight() - player.sprite.getHeight(); //top edge case
//		}
//
//		player.setPosition(new Vector2(currPositionX, currPositionY));


		batch.begin();
		batch.draw(player.sprite, player.sprite.getX(), player.sprite.getY(),100,100);
		batch.end();

	}
	
	@Override	
	public void dispose () {
		batch.dispose();
		enemyBatch.dispose();
		backgroundTexture.dispose();
	}

	private void preflight() {
		JsonUtil jsonUtil = new JsonUtil();
		settings = jsonUtil.deserializeJson("settings/settings.json", Settings.class);
	}
}
