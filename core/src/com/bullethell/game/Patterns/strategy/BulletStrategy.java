package com.bullethell.game.Patterns.strategy;

import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.entities.Player;
import com.bullethell.game.systems.AssetHandler;

import java.util.List;

public interface BulletStrategy {
    List<Bullet> createBullets(Enemy enemy, Player player, AssetHandler assetHandler);
}

