package com.bullethell.game.movements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Enemy;

import java.util.function.Function;

public class Movements {

    float angle = 0;
    public Function<Enemy, Vector2> moveTo (String location) {
        switch (location) {
            case "screenHalf":
                return moveToScreenHalf;
            case "screenTop":
                return moveToScreenTop;
            case "screenLeft":
                return moveToScreenLeft;
            case "screenRight":
                return moveToScreenRight;
            case "screenCenter":
                return moveToScreenCenter;
        }
        return null;
    }

    Function<Enemy, Vector2> moveToScreenHalf = enemy -> new Vector2(enemy.getPosition().x, (float) Gdx.graphics.getHeight() / 2);

    Function<Enemy, Vector2> moveToScreenCenter = enemy -> new Vector2((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);

    Function<Enemy, Vector2> moveToScreenTop = enemy -> new Vector2(enemy.getPosition().x, (float) Gdx.graphics.getHeight());

    Function<Enemy, Vector2> moveToScreenLeft = enemy -> new Vector2(0 - enemy.sprite.getWidth(), enemy.getPosition().y);

    Function<Enemy, Vector2> moveToScreenRight = enemy -> new Vector2(Gdx.graphics.getWidth() + enemy.sprite.getWidth() / 2, enemy.getPosition().y);

    public Function<Enemy, Vector2> moveTo(float x, float y) {
        return enemy -> new Vector2(x, y);
    }

    public Function<Enemy, Vector2> moveToCircularPattern(float centerX, float centerY, float radius) {
        // Your logic to calculate the circular movement
        return enemy -> {
            // Example calculation for a point on the circle (simplified)
            angle += (float) (Math.PI * 0.09f) * Gdx.graphics.getDeltaTime(); // Determine based on game logic or time
            float x = (float) (centerX + radius * Math.cos(angle));
            float y = (float) (centerY + radius * Math.sin(angle));
            return new Vector2(x, y);
        };
    }

    public Function<Enemy, Vector2> moveToCircleEdge(float centerX, float centerY, float radius, int totalEnemies) {
        float angleIncrement = (float) ((2 * Math.PI) / totalEnemies);
        float angle = angleIncrement * Gdx.graphics.getDeltaTime();
        float x = (float) (centerX + radius * Math.cos(angle));
        float y = (float) (centerY + radius * Math.sin(angle));
        return enemy -> new Vector2(x, y);
    }
}
