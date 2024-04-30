package com.bullethell.game.systems;

import com.bullethell.game.controllers.PlayerController;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.systems.collisions.CollisionDetection;
import com.bullethell.game.systems.score.ScoreManager;

public class GameEventManager {
    private PlayerController playerController;
    private ScoreManager scoreManager;
    private CollisionDetection collisionDetection;

    public GameEventManager(GameObjectManager gom) {
        this.collisionDetection = new CollisionDetection(gom, gom.scoreManager);
        playerController = new PlayerController(gom.getPlayer(), Settings.getInstance().getPlayerSettings());
    }

    public void update(float deltaTime, AssetHandler assetHandler) {
        playerController.listen(assetHandler, deltaTime);
        collisionDetection.checkCollisions();
    }
}
