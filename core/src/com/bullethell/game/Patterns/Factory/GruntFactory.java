package com.bullethell.game.Patterns.Factory;

import com.bullethell.game.entities.Entity;
import com.bullethell.game.entities.Grunt;
import com.bullethell.game.systems.AssetHandler;

public class GruntFactory {
    Entity createEntity(int x, int y, AssetHandler assetHandler, String type)
    {
        return new Grunt(x, y, assetHandler, type);
    }
}
