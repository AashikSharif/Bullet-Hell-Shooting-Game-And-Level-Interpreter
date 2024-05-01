package com.bullethell.game.entities;

import com.bullethell.game.controllers.IShootable;
import com.bullethell.game.systems.AssetHandler;

public class Boss extends Enemy implements IShootable {
    public Boss(float x, float y, AssetHandler assetHandler, String type) {

        super(x, y, assetHandler, type);
        if ((type.equals("midBoss") )) {
            this.setHealth(500);
        } else if (type.equals("finalBoss")){
            this.setHealth(1000);
        }
    }

    @Override
    public void shoot() {}
    @Override
    public int getScore()
    {
        if(getType().equals("midBoss")) return 500;
        else if(getType().equals("finalBoss")) return 1000;
        return 0;

    }

    public int getKillBonusScore()
    {
        if(getType().equals("gruntA")) return 5000;
        else if(getType().equals("gruntB")) return 10000;
        return 0;
    }
}
