package com.bullethell.game.systems;

import com.bullethell.game.controllers.PlayerController;
import com.bullethell.game.settings.Settings;

public class GameEventManager {
    private GameObjectManager gom;
    private PlayerController playerController;

    public GameEventManager(GameObjectManager gom) {
        this.gom = gom;
        playerController = new PlayerController(gom.getPlayer(), Settings.getInstance().getPlayerSettings());
    }

    public void update(float deltaTime, AssetHandler assetHandler) {
        playerController.listen(assetHandler, deltaTime);
    }
}
