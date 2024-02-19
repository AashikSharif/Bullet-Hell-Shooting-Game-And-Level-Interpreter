package com.bullethell.game.movements;

import com.badlogic.gdx.utils.Queue;
import com.bullethell.game.entities.Enemy;

public class MovementQueue implements MovementStrategy{

    private Queue<MovementStrategy> movements;
    private MovementStrategy currentMovement;

    public MovementQueue() {
        this.movements = new Queue<>();
    }
    public void addMovement(MovementStrategy movement) {
        movements.addLast(movement);
        if (currentMovement == null) {
            currentMovement = movements.first();
        }
    }

    @Override
    public void updatePosition(Enemy enemy, float deltaTime) {
        if (currentMovement == null) {
            return;
        }

        currentMovement.updatePosition(enemy, deltaTime);

        if (currentMovement.isCompleted()) {
            movements.removeFirst();
            currentMovement = movements.size > 0 ? movements.first() : null;
        }
    }

    @Override
    public boolean isCompleted() {
        return movements.size == 0;
    }
}
