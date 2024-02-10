package com.bullethell.game.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Enemy {
    private Texture enemyTexture;
    private String type;

    private Sprite enemySprite;
    private boolean isAlive;
    private float centerX;
    private float centerY;
    private float radius;
    private float angle;
    private float angularSpeed;

    //private float getCurrentAngle;

    //private float getAngularSpeed;
    private float x;
    private float y;

    //private float setPosition;

    public float getX() {
        return centerX; // Assuming 'centerX' is set correctly
    }

    public float getY() {
        return centerY; // Assuming 'centerX' is set correctly
    }

    public Sprite getSprite() {
        return enemySprite; // Assuming you have a Sprite variable named 'enemySprite' in your Enemy class
    }

    public float getCurrentAngle() {
        return angle;
    }

    public float getAngularSpeed() {
        // Implementation of the method
        return angularSpeed;
    }

    public Enemy(String type,float centerX, float centerY, float radius, float angularSpeed,float x,float y) {

        this(type); // Call the existing constructor to initialize common attributes
        setCenter(centerX, centerY);
        setRadius(radius);
        setAngularSpeed(angularSpeed);
    }


    public Enemy(String type){
        this.type = type;
        this.isAlive = true;

        if (type.equals("A")) {
            enemyTexture = new Texture("typeA.png");
            // or enemySprite = new Sprite(new Texture("typeA.png"));
        } else if (type.equals("B")) {
            enemyTexture = new Texture("typeB.png");
            // or enemySprite = new Sprite(new Texture("typeB.png"));
        } else if (type.equals("C")) {
            enemyTexture = new Texture("typeC.png");

        }
    }

    public Texture getEnemyTexture() {
        return enemyTexture;
    }

    // or public Sprite getEnemySprite() { return enemySprite; }



        /*
        this.type = type;
        this.isAlive = true;

        Dictionary<String, String> enemyImageName = new Hashtable<>();
        enemyImageName.put("A", "typeA.png");
        enemyImageName.put("B", "typeB.png");
        enemyImageName.put("C", "midBoss.png");
        enemyImageName.put("D", "finalBoss.png");

        this.enemyTexture = new Texture(enemyImageName.get(type));
    }

         */

    public void update(float delta) {
        angle += angularSpeed * delta;
        float x = centerX + radius * MathUtils.cos(angle);
        float y = centerY + radius * MathUtils.sin(angle);
        //setPosition(x,y);
        // Update the position or other logic...
    }

    public void setCenter(float centerX, float centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setAngularSpeed(float angularSpeed) {
        this.angularSpeed = angularSpeed;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}


class MidBoss extends Enemy {
    public MidBoss() {
        super("C"); // Initializes with type C (Mid Boss)
    }

    // Add any additional methods or logic specific to MidBoss...
}

class FinalBoss extends Enemy {
    public FinalBoss() {
        super("D"); // Initializes with type D (Final Boss)
    }

    // Add any additional methods or logic specific to FinalBoss...
}














