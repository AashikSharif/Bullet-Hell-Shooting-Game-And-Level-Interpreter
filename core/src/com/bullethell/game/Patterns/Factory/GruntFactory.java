package com.bullethell.game.Patterns.Factory;

import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Entity;
import com.bullethell.game.entities.Grunt;
import com.bullethell.game.systems.AssetHandler;

public class GruntFactory  implements EntityFactory{
    @Override
    public Entity createEntity(float x, float y, AssetHandler assetHandler, String type, Vector2 velocity, int damage)
    {
        return new Grunt(x, y, assetHandler, type);
    }
}
