package com.bullethell.game.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.BulletHellGame;
import com.bullethell.game.settings.Settings;
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
    private SpriteBatch spriteBatch;

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

    }

    public void update(float time) {
        gom.update(time);
        gem.update(time, assetHandler);
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
    private void checkPlayerWon(boolean nextEnemy) {
        //Add winning condition
        if(gom.getEnemyManager().currentWave == 3 && !nextEnemy) {
            System.out.println("Player won - Game over");
            game.setScreen(new GameWinScreen(game));
        }
    }
}
