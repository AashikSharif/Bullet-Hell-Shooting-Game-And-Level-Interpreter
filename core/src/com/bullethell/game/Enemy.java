package com.bullethell.game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends Sprite {
    int type = 1;

    public int getType() {
        return type;
    }
    public boolean isOnScreen(){
        // need code here
        return true;
    }
    public Enemy(TextureRegion region) {
        super(region);
    }
}
