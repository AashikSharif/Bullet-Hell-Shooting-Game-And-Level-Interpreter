package com.bullethell.game.Patterns.Factory;

import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Entity;
import com.bullethell.game.systems.AssetHandler;

public class BulletFactory implements EntityFactory {
    @Override
    public Entity createEntity(float x, float y, AssetHandler assetHandler, String entity, Vector2 velocity, int damage, int lives)
    {
        return new Bullet(x, y, entity, velocity,damage,assetHandler);
    }
}