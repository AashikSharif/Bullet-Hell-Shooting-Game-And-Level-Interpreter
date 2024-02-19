package com.bullethell.game.movements;

import com.bullethell.game.entities.Enemy;

public interface MovementStrategy {
    void updatePosition(Enemy enemy, float deltaTime);
    boolean isCompleted();
}
