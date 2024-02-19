package com.bullethell.game.movements;

import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Enemy;
import java.util.function.Function;

public class TargetedMovement implements MovementStrategy {
    private Function<Enemy, Vector2> targetPositionCalculator;
    private float speed;
    private boolean isCompleted = false;

    public TargetedMovement(Function<Enemy, Vector2> targetPositionCalculator, float speed) {
        this.targetPositionCalculator = targetPositionCalculator;
        this.speed = speed;
    }

    @Override
    public void updatePosition(Enemy enemy, float deltaTime) {
        Vector2 currentPosition = enemy.getPosition();
        Vector2 targetPosition = targetPositionCalculator.apply(enemy);
        Vector2 direction = new Vector2(targetPosition.x - currentPosition.x, targetPosition.y - currentPosition.y).nor();
        Vector2 movement = new Vector2(direction.x * speed * deltaTime, direction.y * speed * deltaTime);

        Vector2 newPosition = currentPosition.add(movement);
        if (currentPosition.dst(targetPosition) <= movement.len() || newPosition.epsilonEquals(targetPosition, 0.1f)) {
            newPosition = targetPosition.cpy();
            isCompleted = true;
        }

        enemy.setPosition(newPosition);
        enemy.update();
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
}
