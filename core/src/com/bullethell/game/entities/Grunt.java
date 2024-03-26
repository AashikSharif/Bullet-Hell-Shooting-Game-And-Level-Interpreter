package com.bullethell.game.entities;

import com.bullethell.game.controllers.IShootable;
import com.bullethell.game.systems.AssetHandler;

public class Grunt extends Enemy implements IShootable {

    private static final float HITBOX_WIDTH = 30;
    private static final float HITBOX_HEIGHT = 30;
    public Grunt(float x, float y, AssetHandler assetHandler, String type) {
        super(x, y, assetHandler, type);
    }

    @Override
    public void shoot() {}
}
