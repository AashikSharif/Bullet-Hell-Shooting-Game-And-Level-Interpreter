package com.bullethell.game.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.controllers.PlayerController;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.systems.collisions.CollisionDetection;
import com.bullethell.game.systems.score.ScoreManager;

public class GameEventManager {
    private PlayerController playerController;
    private ScoreManager scoreManager;
    private CollisionDetection collisionDetection;
    private SpriteBatch spriteBatch;

    private GameObjectManager gom;

    public GameEventManager(GameObjectManager gom, SpriteBatch spriteBatch) {
        this.collisionDetection = new CollisionDetection(gom, gom.scoreManager);
        this.spriteBatch = spriteBatch;
        this.gom = gom;
        playerController = new PlayerController(gom.getPlayer(), Settings.getInstance().getPlayerSettings());
    }

    public void update(float deltaTime, AssetHandler assetHandler) {
        gom.checkInversion(deltaTime);
        playerController.listen(assetHandler, deltaTime);
        collisionDetection.checkCollisions();
        
    }
}
