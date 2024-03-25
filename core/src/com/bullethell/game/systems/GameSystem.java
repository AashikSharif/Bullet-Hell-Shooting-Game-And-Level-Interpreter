package com.bullethell.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private AssetHandler assetHandler = new AssetHandler();

    private Texture background;

    private Player player;

    private PlayerController playerController;

    private List<Bullet> playerBullets;

    private Map<String, ArrayList<Enemy>> enemyList;

    private MovementController mc;

    private float timeInSeconds = 0f;
    private EntityFactory playerFactory;
    private EntityFactory bulletFactory;
    private EntityFactory gruntFactory;
    private EntityFactory bossFactory;

    public GameSystem() {
        playerFactory = new PlayerFactory();
        bulletFactory = new BulletFactory();
        gruntFactory = new GruntFactory();
        bossFactory = new BossFactory();
    }

    public void init() {
        loadSettings();
        assetHandler.load(settings.getAssets());


        enemyList = new HashMap<>();
        // Create player using factory
        player = (Player) playerFactory.createEntity((float) Gdx.graphics.getWidth() / 2 - 66, 0, assetHandler,"Player", new Vector2(), 0);
        //player = new Player((float) Gdx.graphics.getWidth() / 2 - 66, 0, assetHandler);
        playerBullets = new ArrayList<>();
        playerController = new PlayerController(player, settings.getPlayerSettings());

        background = assetHandler.getAssetTexture("background");
        mc = new MovementController();
    }

    public void update(float time) {
        timeInSeconds += time;
        if (timeInSeconds <= 48 && enemyList.get("gruntA") == null) {
            addEnemies(14, "gruntA");
        } else if (timeInSeconds > 48 && timeInSeconds <= 75 && enemyList.get("midBoss") == null) {
            enemyList.remove("gruntA");
            addEnemies(1, "midBoss");
        } else if (timeInSeconds > 75 && timeInSeconds <= 92 && enemyList.get("gruntB") == null) {
            enemyList.remove("midBoss");
            addEnemies(14, "gruntB");
        } else if (timeInSeconds > 92 && enemyList.get("finalBoss") == null) {
            enemyList.remove("gruntB");
            addEnemies(1, "finalBoss");
        }

        playerController.listen(playerBullets, assetHandler, time);
        updatePlayerBullets();
        moveEnemies(time);
        checkPlayerCollision();
        checkBulletCollision();
    }
    private void checkPlayerCollision(){
        List<Enemy> allEnemies = new ArrayList<>();
        enemyList.values().forEach(allEnemies::addAll);
        Iterator<Enemy> enemyIterator = allEnemies.iterator();
        while(enemyIterator.hasNext()){
            Enemy enemy = enemyIterator.next();
            if(player.getHitbox().overlaps(enemy.getHitbox())){
                System.out.println("player and enemy have collision!");
                enemyIterator.remove();
                break;
            }
        }
    }
    private void checkBulletCollision(){
        for(Iterator<Bullet> bulletIterator = playerBullets.iterator(); bulletIterator.hasNext();){
            Bullet bullet = bulletIterator.next();
            boolean bulletRemoved = false;
            for(List<Enemy> enemies : enemyList.values()){
                for(Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();){
                    Enemy enemy = enemyIterator.next();
                    if(bullet.getHitbox().overlaps(enemy.getHitbox())){
                        System.out.println("Bullet hit detected!");
                        bulletIterator.remove();
                        enemyIterator.remove();
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
    public void render(SpriteBatch spriteBatch, float time) {
        // combined renders
        update(time);
        renderBackground(spriteBatch);
        renderEntity(spriteBatch, player); // render player
        renderEnemies(spriteBatch, time);
        renderPlayerBullets(spriteBatch);
    }

    private void moveEnemies(float time) {
        ArrayList<Enemy> enemies;
        if (timeInSeconds <= 48 && enemyList.get("gruntA") != null) {
            enemies = enemyList.get("gruntA");
        } else if (timeInSeconds > 48 && timeInSeconds <= 75) {
            enemies = enemyList.get("midBoss");
        } else if (timeInSeconds > 75 && timeInSeconds <= 92) {
            enemies = enemyList.get("gruntB");
        } else {
            enemies = enemyList.get("finalBoss");
        }

        if (enemies != null && !enemies.isEmpty()) {
            for (Enemy enemy : enemies) {
                mc.update(time);

            }
        }
    }

    private void renderEntity(SpriteBatch spriteBatch, Entity entity) {
        spriteBatch.draw(entity.sprite, entity.sprite.getX(), entity.sprite.getY());
    }

    private void renderBackground(SpriteBatch spriteBatch) {
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void renderEnemies(SpriteBatch spriteBatch, float time) {
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

                //Enemy enemy = new Enemy(xPosition, yPosition, assetHandler, type);
                Enemy enemy = (Enemy) gruntFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0);
                enemies.add(enemy);


                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenHalf") : movements.moveTo(enemy.getPosition().x, (float) Gdx.graphics.getHeight() / 2 - 75), 10));
                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenRight") : movements.moveTo("screenLeft"), 10));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenTop"), 10));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 200f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.09f; // Complete one full circle per second

                if (isLeftSide) {
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 10));
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 10));
                    mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 10), 20));
                    mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.0001f), 14));
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

                //Enemy enemy = new Enemy(xPosition, yPosition, assetHandler, type);
                Enemy enemy = (Enemy) gruntFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0);
                enemies.add(enemy);


                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenHalf") : movements.moveTo(enemy.getPosition().x, (float) Gdx.graphics.getHeight() / 2 - 75), 10));
                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenRight") : movements.moveTo("screenLeft"), 10));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenTop"), 10));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 200f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.09f; // Complete one full circle per second

                if (isLeftSide) {
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 10));
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 10));
                    mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 10), 20));
                    mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.0001f), 14));
                }

                mc.addEnemyMovement(enemy, mq);
            }
        } else if (Objects.equals(type, "midBoss")) {

            for (int i = 0; i < count; i++) {
                MovementQueue mq = new MovementQueue();
                Movements movements = new Movements();

                float xPosition = Gdx.graphics.getWidth() / 2;

                float yPosition = Gdx.graphics.getHeight();

                //Enemy enemy = new Enemy(xPosition, yPosition, assetHandler, type);
                Enemy enemy = (Enemy) bossFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0);
                enemies.add(enemy);

                mq.addMovement(new TargetedMovement(movements.moveTo("screenHalf"), 50));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 50));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenRight"), 50));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 50));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 150f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.09f; // Complete one full circle per second

                mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 1), 100));
                mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.0001f), 1));

                mc.addEnemyMovement(enemy, mq);
            }

        } else if (Objects.equals(type, "finalBoss")) {
            for (int i = 0; i < count; i++) {
                MovementQueue mq = new MovementQueue();
                Movements movements = new Movements();

                float xPosition = Gdx.graphics.getWidth() / 2;

                float yPosition = Gdx.graphics.getHeight();

                //Enemy enemy = new Enemy(xPosition, yPosition, assetHandler, type);
                Enemy enemy = (Enemy) bossFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0);
                enemies.add(enemy);

                mq.addMovement(new TargetedMovement(movements.moveTo("screenHalf"), 50));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 50));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenRight"), 50));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 50));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 150f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.09f; // Complete one full circle per second

                mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 1), 100));
                mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.0001f), 1));

                mc.addEnemyMovement(enemy, mq);
            }
        }

        enemyList.put(type, enemies);
    }
}
