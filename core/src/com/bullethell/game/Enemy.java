package com.bullethell.game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class enemies extends Sprite {
    int type = 1;

    public int getType() {
        return type;
    }

    public enemies(TextureRegion region) {
        super(region);
    }
}
