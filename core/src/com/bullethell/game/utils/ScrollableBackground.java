package com.bullethell.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.systems.AssetHandler;

public class ScrollableBackground {
    private Texture texture;
    private float position1;
    private float position2;
    private float speed;

    public ScrollableBackground(AssetHandler assetHandler, float speed) {
        this.texture = assetHandler.getAssetTexture("background");
        this.position1 = 0;
        this.speed = speed;
        this.position2 = this.texture.getHeight();
    }

    public void update(float deltaTime) {
        position1 -= speed * deltaTime;
        position2 -= speed * deltaTime;

        // Reset codition
        if (position1 + texture.getHeight() <= 0) {
            position1 = position2 + texture.getHeight();
        }

        if (position2 + texture.getHeight() <= 0) {
            position2 = position1 + texture.getHeight();
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, 0, position1);
        batch.draw(texture, 0, position2);
    }

    public void dispose() {
        texture.dispose();
    }
}
