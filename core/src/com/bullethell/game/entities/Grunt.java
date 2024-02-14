package com.bullethell.game.entities;

import com.bullethell.game.controllers.IShootable;
import com.bullethell.game.systems.AssetHandler;

public class Grunt extends Enemy implements IShootable {
    public Grunt(float x, float y, AssetHandler assetHandler, String type) {
        super(x, y, assetHandler, type);
    }

    @Override
    public void shoot() {}
}
