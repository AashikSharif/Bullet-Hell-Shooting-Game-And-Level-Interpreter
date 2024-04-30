package com.bullethell.game.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.BulletHellGame;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.systems.enemies.EnemyManager;
import com.bullethell.game.utils.Renderer;
import com.bullethell.game.screens.*;
import com.bullethell.game.utils.ScrollableBackground;

public class GameSystem {
    private Settings settings;
    private final AssetHandler assetHandler = new AssetHandler();
    private ScrollableBackground background;
    private Renderer renderer;
    private final BulletHellGame game;
    private GameObjectManager gom;
    private GameEventManager gem;
    private final SpriteBatch spriteBatch;
    private EnemyManager enemyManager;
    public GameSystem(BulletHellGame game, SpriteBatch spriteBatch) {
        this.game = game;
        this.spriteBatch = spriteBatch;
    }

    public void init() {
        loadSettings();
        assetHandler.load(settings.getAssets());
        background = new ScrollableBackground(assetHandler, 100);
        renderer = new Renderer();
        gom = new GameObjectManager(game, renderer, assetHandler);
        gem = new GameEventManager(gom);
        enemyManager = new EnemyManager(assetHandler, renderer);
    }

    public void update(float time) {
        gom.update(time);
        gem.update(time, assetHandler);
//        checkPlayerWon();
        background.update(time);
    }
    public void render(float time) {
        update(time);
        background.render(spriteBatch);
        gom.render(time, spriteBatch);
    }

    private void loadSettings() {
        settings = Settings.getInstance();
    }
    public void toMainMenu(){
        game.setScreen(new GameOverScreen(game));
    }
    //winning condition, need winning screen changes
//    public void checkPlayerWon() {
//        //Add winning condition
//        if(gom.getEnemyManager().currentWave==3 &&  (gom.getEnemyManager().getEnemyList().get("midBoss").isEmpty())) {
//            System.out.println("Player won - Game over");
//            game.setScreen(new GameWinScreen(game));
//        }
//    }

//    public void checkPlayerWon(boolean nextEnemy) {
//        //Add winning condition
//        //Check why the !nextEnemy condition is always false???
//        if(gom.getEnemyManager().currentWave==3 && nextEnemy) {
//            System.out.println("Player won - Game over");
//            game.setScreen(new GameWinScreen(game));
//        }
//    }
}
