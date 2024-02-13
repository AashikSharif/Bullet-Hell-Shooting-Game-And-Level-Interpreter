package com.bullethell.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bullethell.game.Enemies.Enemy;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.systems.GameSystem;

public class BulletHellGame extends Game {
    SpriteBatch batch;
    Sprite enemySprite[];
    Sprite midBossSprite;
    Sprite finalBossSprite;
    Texture backgroundTexture;
    int enemyPositionChange = -1;
    String enemyType;
    int timeInSec = 0;
    int frameInSec = 0;
    Enemy[] typeA; //25 type A enemies
    Enemy[] typeB;
    Enemy midBoss;
    Enemy finalBoss;
    AssetHandler assetHandler = new AssetHandler();

    GameSystem gameSystem;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameSystem = new GameSystem();

        enemySprite = new Sprite[25];
        typeA = new Enemy[25];
        typeB = new Enemy[10];  //10 type B enemies
        midBoss = new Enemy("C");
        finalBoss = new Enemy("D");


        for (int i = 0, j = (-1); i < 25; i++) {
            if (i % 5 == 0) j++;
            typeA[i] = new Enemy("A");
            enemySprite[i] = new Sprite(typeA[i].enemy, 200, 200);
            enemySprite[i].setPosition((Gdx.graphics.getWidth() / 10) * (i % 5) - enemySprite[i].getWidth() / 2,
                    Gdx.graphics.getHeight() / 10 * (10 - j % 5 + 1) - enemySprite[i].getHeight() / 2);

        }

        for (int i = 0; i < 10; i++) {
            typeB[i] = new Enemy("B");
        }


        midBossSprite = new Sprite(midBoss.enemy, 200, 200);
        finalBossSprite = new Sprite(finalBoss.enemy, 200, 200);

        midBossSprite.setPosition(Gdx.graphics.getWidth() / 2 - midBossSprite.getWidth() / 2,
                Gdx.graphics.getHeight() - midBossSprite.getHeight() / 2);
        finalBossSprite.setPosition(Gdx.graphics.getWidth() / 2 - finalBossSprite.getWidth() / 2,
                Gdx.graphics.getHeight() - finalBossSprite.getHeight() / 2);

    }

    @Override
    public void render() {
        frameInSec++;
        if (frameInSec == 60) {
            timeInSec++;
            frameInSec = 0;
        }


        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();

        gameSystem.render(batch, timeInSec);


        if (timeInSec < 5) {
            float enemyPositionX = enemySprite[0].getX();
            float enemyPositionY = enemySprite[0].getY();
            //System.out.println("X: "+enemySprite[0].getX()+"\nY: "+Gdx.graphics.getHeight()+"\n");
            //checking for positions on X axis
            if (enemyPositionX < -180) {
                //enemyPositionX = 0; //left edge case
                enemyPositionChange *= -1;
            } else if (enemySprite[0].getX() + 180 + 460 > Gdx.graphics.getWidth()) {
                //enemyPositionX = Gdx.graphics.getWidth() - enemySprite.getWidth(); //right edge case
                enemyPositionChange *= -1;
            }
            enemySprite[0].setPosition(enemyPositionX + enemyPositionChange, enemyPositionY);
            //System.out.println(timeInSec +" "+frameInSec +" XD");
            //for typeA enemies
            enemyType = "A";

            for (int i = 0, j = -1; i < 25; i++) {
                if (i % 5 == 0) j++;
                batch.draw(enemySprite[i], enemyPositionX + enemyPositionChange + (i % 5) * 100, j * 50 + 250);
            }
        } else if (timeInSec >= 5 && timeInSec <= 10) {
            float enemyPositionX = midBossSprite.getX();
            float enemyPositionY = midBossSprite.getY();
            //for mid-boss
            enemyType = "C";
            batch.draw(midBossSprite, enemyPositionX, enemyPositionY);
        } else if (timeInSec > 10 && timeInSec < 15) {
            //for typeB enemies
            enemyType = "B";
        } else if (timeInSec >= 15 && timeInSec < 20) {
            enemyType = "D";
            //for Final Boss code

            float enemyPositionX = finalBossSprite.getX();
            float enemyPositionY = finalBossSprite.getY();
            //for mid-boss
            enemyType = "C";
            batch.draw(finalBossSprite, enemyPositionX, enemyPositionY);
        }


        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
        assetHandler.dispose();
    }
}
