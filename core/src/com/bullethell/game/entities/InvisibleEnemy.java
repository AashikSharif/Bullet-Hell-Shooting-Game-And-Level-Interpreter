package com.bullethell.game.entities;

import com.bullethell.game.controllers.IShootable;
import com.bullethell.game.systems.AssetHandler;

public class InvisibleEnemy extends Enemy implements IShootable {

    public InvisibleEnemy(float x, float y, AssetHandler assetHandler, String type) {

        super(x, y, assetHandler, type);
    }
    @Override
    public void shoot() {

    }
}
