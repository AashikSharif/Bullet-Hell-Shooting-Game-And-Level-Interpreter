package com.bullethell.game.systems.inversion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class HorizontalInversion extends BaseInversion {
    public void applyInversion(SpriteBatch spriteBatch) {
        float halfWidth = Gdx.graphics.getWidth() / 2f;
        float halfHeight = Gdx.graphics.getHeight() / 2f;
        Matrix4 matrix = new Matrix4()
                .translate(halfWidth, halfHeight, 0)
                .scale(currentScale, 1, 1)
                .translate(-halfWidth, -halfHeight, 0);
        spriteBatch.setTransformMatrix(matrix);
    }
}
