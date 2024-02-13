package com.bullethell.game.entities;

import com.bullethell.game.controllers.IShootable;
import com.bullethell.game.systems.AssetHandler;

public class Boss extends Enemy implements IShootable {
    public Boss(float x, float y, AssetHandler assetHandler, String type) {
        super(x, y, assetHandler, type);
    }

    @Override
    public void shoot() {}
}

