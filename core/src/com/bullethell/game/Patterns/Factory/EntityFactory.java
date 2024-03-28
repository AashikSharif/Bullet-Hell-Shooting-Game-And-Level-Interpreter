package com.bullethell.game.Patterns.Factory;

import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.*;
import com.bullethell.game.systems.AssetHandler;


public interface EntityFactory
{
    Entity createEntity(float x, float y, AssetHandler assetHandler, String type,Vector2 velocity, int damage, int lives);

}
