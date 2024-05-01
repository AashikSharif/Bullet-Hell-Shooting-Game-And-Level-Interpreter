package com.bullethell.game.movements;

import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Enemy;

public class CircularMovement implements MovementStrategy {
    private float centerX, centerY, radius, angularVelocity;
    private float angle;
    Enemy centerEnemy;

    public CircularMovement(float centerX, float centerY, float radius, float angularVelocity, int totalEnemies) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.angularVelocity = angularVelocity;
        this.angle = (float) ((2 * Math.PI) / totalEnemies);
    }

    @Override
    public void updatePosition(Enemy enemy, float deltaTime) {
        //angle += angularVelocity * deltaTime;
        angle = (angle + angularVelocity * deltaTime) % (float)(2 * Math.PI);
        float x = centerX + radius * (float)Math.cos(angle);
        float y = centerY + radius * (float)Math.sin(angle);
        enemy.setPosition(new Vector2(x, y));
        enemy.update();
    }
    public CircularMovement(Enemy centerEnemy, float radius, float angularVelocity, int totalEnemies) {
        this.centerEnemy = centerEnemy;
        this.radius = radius;
        this.angularVelocity = angularVelocity;
        this.angle = (float) ((2 * Math.PI) / totalEnemies);
    }

    @Override
    public boolean isCompleted() {
        //TODO: Fix this so it has a completion (maybe time based)
        return false;
    }
}
