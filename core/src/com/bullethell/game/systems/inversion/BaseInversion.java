package com.bullethell.game.systems.inversion;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public abstract class BaseInversion {
    protected boolean isInverted = false;
    protected boolean isAnimating = false;
    protected float transitionTime = 0f;
    protected float currentScale = 1f;
    protected static final float MAX_TRANSITION_DURATION = 1f;

    public void update(float deltaTime) {
        if (isAnimating) {
            transitionTime += deltaTime;
            float progress = Math.min(transitionTime / MAX_TRANSITION_DURATION, 1f);
            currentScale = isInverted ? -1 + 2 * progress : 1 - 2 * progress;

            if (transitionTime >= MAX_TRANSITION_DURATION) {
                isAnimating = false;
                currentScale = isInverted ? -1 : 1;
            }
        }
    }

    public abstract void applyInversion(SpriteBatch spriteBatch);

    public void resetInversion(SpriteBatch spriteBatch) {
        spriteBatch.setTransformMatrix(new Matrix4());
    }

    public void toggleInversion() {
        isInverted = !isInverted;
        isAnimating = true;
        transitionTime = 0;
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public boolean isInverted() {
        return isInverted;
    }
}

