package com.bullethell.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.Patterns.Factory.*;
import com.bullethell.game.controllers.MovementController;
import com.bullethell.game.controllers.PlayerController;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Entity;
import com.bullethell.game.entities.Player;
import com.bullethell.game.movements.CircularMovement;
import com.bullethell.game.movements.MovementQueue;
import com.bullethell.game.movements.Movements;
import com.bullethell.game.movements.TargetedMovement;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.JsonUtil;

import java.util.*;

public class GameSystem {
    private Settings settings;

    private final AssetHandler assetHandler = new AssetHandler();

    private Texture background;
    private Texture player_lives;
    private int score;
    private String yourScoreName;
    BitmapFont yourBitmapFontName;

    private Player player;

    private PlayerController playerController;

    private List<Bullet> playerBullets;

    private List<Bullet> enemyBullets = new ArrayList<>();

    private Map<String, ArrayList<Enemy>> enemyList;

    private MovementController mc;

    private float timeInSeconds = 0f;
    private final EntityFactory playerFactory;
    private final EntityFactory gruntFactory;
    private final EntityFactory bossFactory;
    boolean isCollided = false;
    int time = 3, frames = 60,counter=0;
    private int level = 0;

    public GameSystem() {
        playerFactory = new PlayerFactory();
        gruntFactory = new GruntFactory();
        bossFactory = new BossFactory();
    }

    public void init() {
        loadSettings();
        assetHandler.load(settings.getAssets());
        enemyList = new HashMap<>();
        // Create player using factory
        player = (Player) playerFactory.createEntity((float) Gdx.graphics.getWidth() / 2 - 66, 0, assetHandler,"Player", new Vector2(), 25, 5);
        playerController = new PlayerController(player, settings.getPlayerSettings());
        playerBullets = new ArrayList<>();

        background = assetHandler.getAssetTexture("background");
        player_lives = assetHandler.getAssetTexture("lives");

        mc = new MovementController();

        //Initialize score
        score = 0;
        yourScoreName = "score: 0";
        yourBitmapFontName = new BitmapFont();
    }

    public void update(float time) throws InterruptedException {
        timeInSeconds += time;
        if (timeInSeconds <= 48 && enemyList.get("gruntA") == null) {
            level++;
            addEnemies(20, "gruntA");
        } else if (timeInSeconds > 48 && timeInSeconds <= 75 && enemyList.get("midBoss") == null) {
            level++;
            enemyList.remove("gruntA");
            addEnemies(1, "midBoss");

        } else if (timeInSeconds > 75 && timeInSeconds <= 92 && enemyList.get("gruntB") == null) {
            level++;
            enemyList.remove("midBoss");
            addEnemies(14, "gruntB");

        } else if (timeInSeconds > 92 && enemyList.get("finalBoss") == null) {
            level++;
            enemyList.remove("gruntB");
            addEnemies(1, "finalBoss");

        }

        playerController.listen(playerBullets, assetHandler, time);
        updatePlayerBullets();
        moveEnemies(time);
        checkPlayerCollision();
        checkBulletCollision();
        checkEnemyBulletPlayerCollision();
        updateEnemyBullets();
        enemyShoot(time);
        //checkHighScore();
    }

    //private void checkHighScore() {
    //    if(score>)
    //}

    private void checkPlayerCollision()  { //Player collision with Enemy
        List<Enemy> allEnemies = new ArrayList<>();
        enemyList.values().forEach(allEnemies::addAll);
        Iterator<Enemy> enemyIterator = allEnemies.iterator();
        while(enemyIterator.hasNext()){
            Enemy enemy = enemyIterator.next();
            if(player.getHitbox().overlaps(enemy.getHitbox())){

                player.lostLive(); //Decrement live for player

                System.out.println("player and enemy have collision! Remaining Lives = " + player.getLives());
                enemyIterator.remove();

                if ( !player.isGameOver()) {
                    player.setPosition(new Vector2(Gdx.graphics.getWidth() / 2f - 66, 0) ); //Spawn player in new position
                    enemyBullets = new ArrayList<>();
                }
                else if(player.isGameOver() )
                {
                    System.out.println("Player LOST - Game over");
                    System.exit(0);  //trigger for game over screen -- needs to be modified for the game over screen - PLAYER LOST
                }
                else if(  level == 4 && !enemyIterator.hasNext() ) //Add winning condition
                {
                    System.out.println("Player won - Game over");
                    System.exit(0);
                }

            }
        }
    }
    private void checkBulletCollision(){ //Player bullet to enemy collision
        for(Iterator<Bullet> bulletIterator = playerBullets.iterator(); bulletIterator.hasNext();){
            Bullet bullet = bulletIterator.next();
            boolean bulletRemoved = false;
            for(List<Enemy> enemies : enemyList.values()){
                for(Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();){
                    Enemy enemy = enemyIterator.next();
                    if(bullet.getHitbox().overlaps(enemy.getHitbox())){

                        //updating score
                        score+=enemy.getScore();

                        bulletIterator.remove();
                        enemy.enemyHit(player.damage); //reducing  enemy health
                        System.out.println("Bullet hit detected! - "+enemy.getHealth());
                        if(enemy.getHealth()<=0) {
                            enemyIterator.remove();
                            score+=enemy.getKillBonusScore(); //update kill score
                        }
                        yourScoreName="Score: "+ score;

                        bulletRemoved = true;
                        break;
                    }
                }
                if(bulletRemoved){
                    break;
                }
            }
        }
    }
    private void checkEnemyBulletPlayerCollision() { //Enemy Bullet to player collision
        Iterator<Bullet> bulletIterator = enemyBullets.iterator();

            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                if (bullet.getHitbox().overlaps(player.getHitbox()) && !isCollided) {

                    //System.out.println("Player got hit by enemy Bullet!");
                    isCollided = true;
                    player.lostLive(); //Decrement live for player
                    bulletIterator.remove();
                    counter = 0;

                    System.out.println("player and enemy bullet have collision! Remaining Lives = " + player.getLives());
                    if (!player.isGameOver()) {
                        player.setPosition(new Vector2(Gdx.graphics.getWidth() / 2f - 66, 0) ); //Spawn player in new position
                        enemyBullets = new ArrayList<>();
                    } else if (player.isGameOver()) {
                        System.out.println("Player LOST - Game over");
                        System.exit(0);  //trigger for game over screen -- needs to be modified for the game over screen - PLAYER LOST
                    } else if (level == 4 && !enemyList.containsKey("finalBoss")) //Add winning condition
                    {
                        System.out.println("Player won - Game over");
                        System.exit(0);
                    }

                }
            }

    }
    private void enemyShoot(float deltaTime) {
        for (ArrayList<Enemy> enemies : enemyList.values()) {
            for (Enemy enemy : enemies) {
                boolean isEnemyOnScreen = enemy.getPosition().y + enemy.sprite.getHeight() > 0 &&
                        enemy.getPosition().y < 720 &&
                        enemy.getPosition().x + enemy.sprite.getWidth() > 0 &&
                        enemy.getPosition().x < 1280;
                if (isEnemyOnScreen && enemy.isReadyToShoot(deltaTime)) {
                    Vector2 direction = new Vector2(
                            player.getPosition().x + player.sprite.getWidth() / 2 - (enemy.getPosition().x + enemy.sprite.getWidth() / 2),
                            player.getPosition().y + player.sprite.getHeight() / 2 - (enemy.getPosition().y + enemy.sprite.getHeight() / 2)
                    ).nor();

                    float bulletSpeed = 3;
                    Vector2 velocity = direction.scl(bulletSpeed);

                    float bulletX = enemy.getPosition().x + (enemy.sprite.getWidth() / 2) - Bullet.HITBOX_WIDTH / 2;
                    float bulletY = enemy.getPosition().y - Bullet.HITBOX_HEIGHT;
                    Bullet enemyBullet = new Bullet(bulletX, bulletY, "bullet", velocity, 25, assetHandler);

                    if(!isCollided) {
                        this.enemyBullets.add(enemyBullet);
                        //System.out.println(counter);
                        enemy.resetShootTimer();
                    }

                }
            }
            counter++;
        }
        if (counter == time * frames) {isCollided = false;}

    }
    private void updateEnemyBullets() {
        Iterator<Bullet> iterator = enemyBullets.iterator();
        while(iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
            if (bullet.getPosition().y < 0 || bullet.getPosition().y > Gdx.graphics.getHeight()) {
                iterator.remove();
            }
        }
    }
    private void renderEnemyBullets(SpriteBatch spriteBatch) {
        for (Bullet bullet : enemyBullets) {
            spriteBatch.draw(bullet.sprite, bullet.getPosition().x, bullet.getPosition().y);
        }
    }
    public void render(SpriteBatch spriteBatch, float time) throws InterruptedException {
        // combined renders
        update(time);
        renderBackground(spriteBatch);
        renderEntity(spriteBatch, player); // render player
        renderEnemies(spriteBatch);
        renderPlayerBullets(spriteBatch);
        renderEnemyBullets(spriteBatch);
        renderLives(spriteBatch,player.getLives());
        renderScore(spriteBatch);
    }

    private void renderScore(SpriteBatch spriteBatch) {
        yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        yourBitmapFontName.draw(spriteBatch, yourScoreName,10, Gdx.graphics.getHeight()-20);

    }

    private void moveEnemies(float time) {
        ArrayList<Enemy> enemies;
        if (timeInSeconds <= 48 && enemyList.get("gruntA") != null) {
            enemies = enemyList.get("gruntA");
        } else if(timeInSeconds > 48 && timeInSeconds <= 75) {
            enemies = enemyList.get("midBoss");
        } else if (timeInSeconds > 75 && timeInSeconds <= 92) {
            enemies = enemyList.get("gruntB");
        } else {
            enemies = enemyList.get("finalBoss");
        }

        if (enemies != null && !enemies.isEmpty()) {
                mc.update(time);
        }
    }

    private void renderEntity(SpriteBatch spriteBatch, Entity entity) {
        spriteBatch.draw(entity.sprite, entity.sprite.getX(), entity.sprite.getY());

            // End the sprite batch before starting shape rendering
            spriteBatch.end();

            entity.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            entity.shapeRenderer.setColor(Color.BLUE);

            // Use the hitbox's position and size for rendering
            entity.shapeRenderer.rect(
                    entity.getHitbox().x,
                    entity.getHitbox().y,
                    entity.getHitbox().width,
                    entity.getHitbox().height
            );

            entity.shapeRenderer.end();

            spriteBatch.begin();
    }

    private void renderBackground(SpriteBatch spriteBatch) {
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    private void renderLives(SpriteBatch spriteBatch, int lives)
    {
        for(int i=0;i<lives; i++)
            //spriteBatch.draw(player_lives,i*100,i*100, 50, 50);
            spriteBatch.draw(player_lives,Gdx.graphics.getWidth()-50f/2f*(i+1)-30,Gdx.graphics.getHeight()-30, 30, 30);
        // spriteBatch.draw
    }

    private void renderEnemies(SpriteBatch spriteBatch) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        if (timeInSeconds < 48 && enemyList.get("gruntA") != null) {
            enemies = enemyList.get("gruntA");
        } else if (timeInSeconds > 48 && timeInSeconds <= 75 && enemyList.get("midBoss") != null) {
            enemies = enemyList.get("midBoss");
        } else if (timeInSeconds > 75 && timeInSeconds <= 92 && enemyList.get("gruntB") != null) {
            enemies = enemyList.get("gruntB");
        } else if (timeInSeconds > 92 && enemyList.get("finalBoss") != null) {
            enemies = enemyList.get("finalBoss");
        }

        for (Enemy enemy : enemies) {
            renderEntity(spriteBatch, enemy);
        }
    }

    private void loadSettings() {
        JsonUtil jsonUtil = new JsonUtil();
        settings = jsonUtil.deserializeJson("settings/settings.json", Settings.class);
    }

    private void renderPlayerBullets(SpriteBatch spriteBatch) {
        for (Bullet bullet : playerBullets) {
            renderEntity(spriteBatch, bullet);
        }
    }

    private void updatePlayerBullets() {
        List<Bullet> removeList = new ArrayList<>();
        for (Bullet bullet : playerBullets) {
            if (bullet.getPosition().y > Gdx.graphics.getHeight()) {
                removeList.add(bullet);
            }
            bullet.update();
        }
        if (!removeList.isEmpty()) {
            playerBullets.removeAll(removeList);
        }
    }

    private void addEnemies(int count, String type) {
        ArrayList<Enemy> enemies = new ArrayList<>();

        if (Objects.equals(type, "gruntA")) {
            for (int i = 0; i < count; i++) {
                MovementQueue mq = new MovementQueue();
                Movements movements = new Movements();
                float leftXPlacement = 0; // Left side placement
                float rightXPlacement = (float) (Gdx.graphics.getWidth() - 50); // Right side placement

                boolean isLeftSide = i % 2 == 0;

                float xPosition = isLeftSide ? leftXPlacement : rightXPlacement;

                float yPosition = Gdx.graphics.getHeight() + (i * 75);

                Enemy enemy = (Enemy) gruntFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0, 1);
                enemies.add(enemy);


                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenHalf") : movements.moveTo(enemy.getPosition().x, (float) Gdx.graphics.getHeight() / 2 - 75), 200));
                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenRight") : movements.moveTo("screenLeft"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenTop"), 200));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 200f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.9f; // Complete one full circle per second

                if (isLeftSide) {
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 200));
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 200));
                    mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 10), 210));
                    mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.001f), 14));
                }

                mc.addEnemyMovement(enemy, mq);
            }
        } else if (Objects.equals(type, "gruntB")) {
            for (int i = 0; i < count; i++) {
                MovementQueue mq = new MovementQueue();
                Movements movements = new Movements();
                float leftXPlacement = 0; // Left side placement
                float rightXPlacement = (float) (Gdx.graphics.getWidth() - 50); // Right side placement

                boolean isLeftSide = i % 2 == 0;

                float xPosition = isLeftSide ? leftXPlacement : rightXPlacement;

                float yPosition = Gdx.graphics.getHeight() + (i * 75);

                Enemy enemy = (Enemy) gruntFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0, 1);
                enemies.add(enemy);


                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenHalf") : movements.moveTo(enemy.getPosition().x, (float) Gdx.graphics.getHeight() / 2 - 75), 200));
                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenRight") : movements.moveTo("screenLeft"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenTop"), 200));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 200f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.9f; // Complete one full circle per second

                if (isLeftSide) {
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 200));
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 200));
                    mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 10), 200));
                    mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.001f), 14));
                }

                mc.addEnemyMovement(enemy, mq);
            }
        } else if (Objects.equals(type, "midBoss")) {

            for (int i = 0; i < count; i++) {
                MovementQueue mq = new MovementQueue();
                Movements movements = new Movements();

                float xPosition = (float) Gdx.graphics.getWidth() / 2;

                float yPosition =    Gdx.graphics.getHeight();

                Enemy enemy = (Enemy) bossFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0, 1);
                enemies.add(enemy);

                mq.addMovement(new TargetedMovement(movements.moveTo("screenHalf"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenRight"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 200));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 150f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.9f; // Complete one full circle per second

                mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 1), 200));
                mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.001f), 1));

                mc.addEnemyMovement(enemy, mq);
            }

        } else if (Objects.equals(type, "finalBoss")) {
            for (int i = 0; i < count; i++) {
                MovementQueue mq = new MovementQueue();
                Movements movements = new Movements();

                float xPosition = (float) Gdx.graphics.getWidth() / 2;

                float yPosition = Gdx.graphics.getHeight();

                Enemy enemy = (Enemy) bossFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0, 1);
                enemies.add(enemy);

                mq.addMovement(new TargetedMovement(movements.moveTo("screenHalf"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenRight"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 200));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 150f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.9f; // Complete one full circle per second

                mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 1), 200));
                mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.001f), 1));

                mc.addEnemyMovement(enemy, mq);
            }
        }

        enemyList.put(type, enemies);
    }
}
