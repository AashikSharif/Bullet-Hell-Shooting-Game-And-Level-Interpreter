package com.bullethell.game.entities;

import com.bullethell.game.controllers.IShootable;
import com.bullethell.game.systems.AssetHandler;

public class Grunt extends Enemy implements IShootable {

    private static final float HITBOX_WIDTH = 30;
    private static final float HITBOX_HEIGHT = 30;
    public Grunt(float x, float y, AssetHandler assetHandler, String type) {
        super(x, y, assetHandler, type);

        if (type.equals("gruntA")) {
            this.setHealth(50);
        } else if (type.equals("gruntB")){
            this.setHealth(150);
        }
    }

    @Override
    public void shoot() {}
    @Override
    public int getScore()
        {
            if(getType().equals("gruntA")) return 50;
            else if(getType().equals("gruntB")) return 100;
            return 0;
        }
        public int getKillBonusScore()
        {
            if(getType().equals("gruntA")) return 500;
            else if(getType().equals("gruntB")) return 1000;
            return 0;
        }
}
