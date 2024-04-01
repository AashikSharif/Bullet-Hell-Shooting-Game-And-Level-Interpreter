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
import com.bullethell.game.entities.Player;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.systems.Enemies.EnemyManager;
import com.bullethell.game.utils.JsonUtil;
import com.bullethell.game.utils.Renderer;

import java.util.*;

public class GameSystem {
    private Settings settings;

    private final AssetHandler assetHandler = new AssetHandler();

    private Texture background;

    private Player player;

    private PlayerController playerController;

    private List<Bullet> playerBullets;

    private List<Bullet> enemyBullets = new ArrayList<>();

    private Map<String, ArrayList<Enemy>> enemyList;

    private MovementController mc;

    private float timeInSeconds = 0f;
    private EntityFactory playerFactory;
    private Renderer renderer;
    private int level = 0;

    private EnemyManager enemyManager;

    private SpriteBatch spriteBatch;

    public GameSystem(SpriteBatch spriteBatch) {
        playerFactory = new PlayerFactory();
        this.spriteBatch = spriteBatch;
    }

    public void init() {
        loadSettings();
        assetHandler.load(settings.getAssets());

        enemyList = new HashMap<>();
        // Create player using factory
        player = (Player) playerFactory.createEntity((float) Gdx.graphics.getWidth() / 2 - 66, 0, assetHandler,"Player", new Vector2(), 0, 5);
        playerController = new PlayerController(player, settings.getPlayerSettings());
        playerBullets = new ArrayList<>();


        background = assetHandler.getAssetTexture("background");
        mc = new MovementController();

        renderer = new Renderer();

        enemyManager = new EnemyManager(settings.getLevelInterpreter(), renderer);
    }

    public void update(float time) {
        timeInSeconds += time;

        enemyManager.update(timeInSeconds, time, assetHandler, spriteBatch);

        enemyList = enemyManager.getEnemyList();

        playerController.listen(playerBullets, assetHandler, time);
        updatePlayerBullets();
        checkPlayerCollision();
        checkBulletCollision();
        checkEnemyBulletPlayerCollision();
        updateEnemyBullets(time);
        enemyShoot(time);
    }

    //TODO: move this to separate collision detection class (deliverable 3)
    private void checkPlayerCollision() {
        List<Enemy> allEnemies = new ArrayList<>();
        enemyList.values().forEach(allEnemies::addAll);
        Iterator<Enemy> enemyIterator = allEnemies.iterator();
        while(enemyIterator.hasNext()){
            Enemy enemy = enemyIterator.next();
            if(player.getHitbox().overlaps(enemy.getHitbox())){

                player.lostLive(); //Decrement live for player

                System.out.println("player and enemy have collision! Remaining Lives = " + player.getLives());
                enemyIterator.remove();

                if ( !player.isGameOver()  ) {
                    player = (Player) playerFactory.createEntity((float) Gdx.graphics.getWidth() / 2 - 66, 0, assetHandler, "Player", new Vector2(), 0, player.getLives());//Spawn player again .
                    playerController = new PlayerController(player, settings.getPlayerSettings());
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

    //TODO: move this to separate collision detection class (deliverable 3)
    private void checkBulletCollision(){ //Player to enemy bullet collision
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

    //TODO: move this to separate collision detection class (deliverable 3)
    private void checkEnemyBulletPlayerCollision() {
        Iterator<Bullet> bulletIterator = enemyBullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (bullet.getHitbox().overlaps(player.getHitbox())) {
                System.out.println("get hit!");
                player.lostLive(); //Decrement live for player
                bulletIterator.remove();

                System.out.println("player and enemy bullet have collision! Remaining Lives = " + player.getLives());
                if ( !player.isGameOver()  ) {
                    player = (Player) playerFactory.createEntity((float) Gdx.graphics.getWidth() / 2 - 66, 0, assetHandler, "Player", new Vector2(), 0, player.getLives());//Spawn player again .
                    playerController = new PlayerController(player, settings.getPlayerSettings());
                }
                else if(player.isGameOver() )
                {
                    System.out.println("Player LOST - Game over");
                    System.exit(0);  //trigger for game over screen -- needs to be modified for the game over screen - PLAYER LOST
                }
                else if(  level == 4 && !bulletIterator.hasNext() ) //Add winning condition
                {
                    System.out.println("Player won - Game over");
                    System.exit(0);
                }

            }
        }
    }
    //TODO: move this to enemy management class (deliverable 3)
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
                    Bullet enemyBullet = new Bullet(bulletX, bulletY, "bullet", velocity, 1, assetHandler);

                    this.enemyBullets.add(enemyBullet);
                    enemy.resetShootTimer();
                }
            }
        }
    }

    //TODO: move this to enemy management class (deliverable 3)
    private void updateEnemyBullets(float deltaTime) {
        Iterator<Bullet> iterator = enemyBullets.iterator();
        while(iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
            if (bullet.getPosition().y < 0 || bullet.getPosition().y > Gdx.graphics.getHeight()) {
                iterator.remove();
            }
        }
    }
    private void renderEnemyBullets() {
        for (Bullet bullet : enemyBullets) {
            spriteBatch.draw(bullet.sprite, bullet.getPosition().x, bullet.getPosition().y);
        }
    }
    public void render(float time) {
        // combined renders
        update(time);
        renderer.renderBackground(spriteBatch, background);
        renderer.renderEntity(spriteBatch, player, true); // render player
        enemyManager.renderEnemies(time, spriteBatch);
        renderPlayerBullets();
        renderEnemyBullets();
    }

    private void loadSettings() {
        JsonUtil jsonUtil = new JsonUtil();
        settings = jsonUtil.deserializeJson("settings/settings.json", Settings.class);
    }

    private void renderPlayerBullets() {
        for (Bullet bullet : playerBullets) {
            renderer.renderEntity(spriteBatch, bullet, true);
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
}
