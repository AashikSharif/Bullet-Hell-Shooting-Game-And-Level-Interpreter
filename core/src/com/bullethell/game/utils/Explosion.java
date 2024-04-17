package com.bullethell.game.utils;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.systems.AssetHandler;

public class Explosion {
    private static final int FRAME_COLS = 7, FRAME_ROWS = 1;
    private Animation<TextureRegion> explosionAnimation;
    private float stateTime;
    private Vector2 position;
    private boolean finished = false;

    public Explosion(AssetHandler assetHandler, Vector2 position) {
        Texture texture = assetHandler.getAssetTexture("explosion");
        this.position = position;
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        TextureRegion[] explosionFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                explosionFrames[index++] = tmp[i][j];
            }
        }

        explosionAnimation = new Animation<>(0.025f, explosionFrames);
        stateTime = 0f;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        if (explosionAnimation.isAnimationFinished(stateTime)) {
            finished = true;
        }
    }

    public void draw(SpriteBatch batch) {
        if (!finished) {
            TextureRegion currentFrame = explosionAnimation.getKeyFrame(stateTime, false);
            batch.draw(currentFrame, position.x, position.y);
        }
    }

    public boolean isFinished() {
        return finished;
    }
}

