package com.bullethell.game.systems.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.Patterns.Factory.BossFactory;
import com.bullethell.game.Patterns.Factory.EntityFactory;
import com.bullethell.game.Patterns.Factory.GruntFactory;
import com.bullethell.game.controllers.MovementController;
import com.bullethell.game.entities.Enemy;
import com.bullethell.game.movements.CircularMovement;
import com.bullethell.game.movements.MovementQueue;
import com.bullethell.game.movements.Movements;
import com.bullethell.game.movements.TargetedMovement;
import com.bullethell.game.settings.LevelInterpreter;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Objects;

public class EnemySpawner {
    LevelInterpreter levelInterpreter;
    private EntityFactory gruntFactory;
    private EntityFactory bossFactory;
    public EnemySpawner (LevelInterpreter levelInterpreter) {
        this.levelInterpreter = levelInterpreter;
        gruntFactory = new GruntFactory();
        bossFactory = new BossFactory();
    }

    public ArrayList<Enemy> getSpawn(LevelInterpreter.Enemy e, AssetHandler assetHandler, MovementController mc) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        String type = e.getType();
        int count = e.getCount();

        if (Objects.equals(type, "gruntA")) {
            for (int i = 0; i < count; i++) {
                MovementQueue mq = new MovementQueue();
                Movements movements = new Movements();
                float leftXPlacement = 0; // Left side placement
                float rightXPlacement = (float) (Gdx.graphics.getWidth() - 50); // Right side placement

                boolean isLeftSide = i % 2 == 0;

                float xPosition = isLeftSide ? leftXPlacement : rightXPlacement;

                float yPosition = Gdx.graphics.getHeight() + (i * 75);


                Enemy enemy = (Enemy) gruntFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0, 1);
                enemies.add(enemy);

                System.out.println("Type A added");


                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenHalf") : movements.moveTo(enemy.getPosition().x, (float) Gdx.graphics.getHeight() / 2 - 75), 200));
                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenRight") : movements.moveTo("screenLeft"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenTop"), 200));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 200f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.9f; // Complete one full circle per second

                if (isLeftSide) {
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 200));
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 200));
                    mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 10), 210));
                    mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.001f), 14));
                }

                mc.addEnemyMovement(enemy, mq);
            }
        } else if (Objects.equals(type, "gruntB")) {
            for (int i = 0; i < count; i++) {
                MovementQueue mq = new MovementQueue();
                Movements movements = new Movements();
                float leftXPlacement = 0; // Left side placement
                float rightXPlacement = (float) (Gdx.graphics.getWidth() - 50); // Right side placement

                boolean isLeftSide = i % 2 == 0;

                float xPosition = isLeftSide ? leftXPlacement : rightXPlacement;

                float yPosition = Gdx.graphics.getHeight() + (i * 75);

                //Enemy enemy = new Enemy(xPosition, yPosition, assetHandler, type);
                Enemy enemy = (Enemy) gruntFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0, 1);
                enemies.add(enemy);

                System.out.println("Type B added");

                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenHalf") : movements.moveTo(enemy.getPosition().x, (float) Gdx.graphics.getHeight() / 2 - 75), 200));
                mq.addMovement(new TargetedMovement(isLeftSide ? movements.moveTo("screenRight") : movements.moveTo("screenLeft"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenTop"), 200));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 200f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.9f; // Complete one full circle per second

                if (isLeftSide) {
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 200));
                    mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 200));
                    mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 10), 200));
                    mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.001f), 14));
                }

                mc.addEnemyMovement(enemy, mq);
            }
        } else if (Objects.equals(type, "midBoss")) {

            for (int i = 0; i < count; i++) {
                MovementQueue mq = new MovementQueue();
                Movements movements = new Movements();

                float xPosition = (float) Gdx.graphics.getWidth() / 2;

                float yPosition =    Gdx.graphics.getHeight();

                //Enemy enemy = new Enemy(xPosition, yPosition, assetHandler, type);
                Enemy enemy = (Enemy) bossFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0, 1);
                enemies.add(enemy);

                mq.addMovement(new TargetedMovement(movements.moveTo("screenHalf"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenRight"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 200));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 150f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.9f; // Complete one full circle per second

                mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 1), 200));
                mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.001f), 1));

                mc.addEnemyMovement(enemy, mq);
            }

        } else if (Objects.equals(type, "finalBoss")) {
            for (int i = 0; i < count; i++) {
                MovementQueue mq = new MovementQueue();
                Movements movements = new Movements();

                float xPosition = (float) Gdx.graphics.getWidth() / 2;

                float yPosition = Gdx.graphics.getHeight();

                //Enemy enemy = new Enemy(xPosition, yPosition, assetHandler, type);
                Enemy enemy = (Enemy) bossFactory.createEntity(xPosition, yPosition, assetHandler,type, new Vector2(), 0, 1);
                enemies.add(enemy);

                mq.addMovement(new TargetedMovement(movements.moveTo("screenHalf"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenLeft"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenRight"), 200));
                mq.addMovement(new TargetedMovement(movements.moveTo("screenCenter"), 200));

                float centerX = Gdx.graphics.getWidth() / 2f;
                float centerY = Gdx.graphics.getHeight() / 2f;
                float radius = 150f; // Adjust this value to fit your game's scale
                float angularVelocity = 0.9f; // Complete one full circle per second

                mq.addMovement(new TargetedMovement(movements.moveToCircleEdge(centerX, centerY, radius, 1), 200));
                mq.addMovement(new CircularMovement(centerX, centerY, radius, angularVelocity + (i * 0.001f), 1));

                mc.addEnemyMovement(enemy, mq);
            }
        }

        return enemies;
    }

}
