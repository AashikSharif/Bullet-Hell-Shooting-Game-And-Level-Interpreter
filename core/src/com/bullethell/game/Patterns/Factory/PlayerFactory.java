package com.bullethell.game.Patterns.Factory;

import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Entity;
import com.bullethell.game.entities.Player;
import com.bullethell.game.systems.AssetHandler;

public class PlayerFactory implements EntityFactory
{
    @Override
    public Player createEntity(float x, float y, AssetHandler assetHandler, String type, Vector2 velocity, int damage, int lives)
    {
        return new Player(x,y,assetHandler,damage, lives /* Player Lives*/);
    }
}
