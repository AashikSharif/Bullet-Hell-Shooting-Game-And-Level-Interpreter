package com.bullethell.game.controllers;

import com.bullethell.game.entities.Enemy;
import com.bullethell.game.movements.MovementQueue;
import com.bullethell.game.utils.Pair;
import jdk.javadoc.internal.doclets.toolkit.util.Utils;

import java.util.ArrayList;

public class MovementController {
    ArrayList<Pair<Enemy, MovementQueue>> enemyMovements;

    public MovementController() {
        enemyMovements = new ArrayList<>();
    }

    public void addEnemyMovement(Enemy enemy, MovementQueue queue) {
        enemyMovements.add(new Pair<>(enemy, queue));
    }

    public void update(float deltaTime) {
        for (Pair<Enemy, MovementQueue> pair : enemyMovements) {
            Enemy enemy = pair.getKey();
            MovementQueue queue = pair.getValue();
            queue.updatePosition(enemy, deltaTime);
        }
    }
}
