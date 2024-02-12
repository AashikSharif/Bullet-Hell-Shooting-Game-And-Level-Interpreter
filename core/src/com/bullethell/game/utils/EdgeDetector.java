package com.bullethell.game.utils;

import com.badlogic.gdx.math.Vector2;

public class EdgeDetector {
    private float minX, minY, maxX, maxY;

    public EdgeDetector (float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public boolean isInBounds(Vector2 position, float width, float height) {
        boolean withinXBounds = position.x >= minX && (position.x + width) <= maxX;
        boolean withinYBounds = position.y >= minY && (position.y + height) <= maxY;
        return withinXBounds && withinYBounds;
    }

    public Vector2 correctBounds(Vector2 position, float width, float height) {
        float newX = Math.max(minX, Math.min(position.x, maxX - width));
        float newY = Math.max(minY, Math.min(position.y, maxY - height));
        return new Vector2(newX, newY);
    }
}
