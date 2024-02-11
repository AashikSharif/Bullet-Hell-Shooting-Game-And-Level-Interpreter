package com.bullethell.game.Enemies;

import com.badlogic.gdx.graphics.Texture;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class Enemy {
    public Texture enemy;
    String type;

    int health;
    Dictionary<String, String> enemyImageName= new Hashtable<>();
    boolean isAlive = true;
    Enemy ()
    {
        enemyImageName.put("A","type A.png");
        enemyImageName.put("B","type B.png");
        enemyImageName.put("C","mid Boss.png");
        enemyImageName.put("D","Final Boss.png");
    }

    public Enemy(String type)
    {
        this();
        this.type = type;
        enemy = new Texture(enemyImageName.get(type));
    }

    int enemyHealth(Enemy Enemy, String Type, int current_health)
    {
        //Code for future reference on health, need to create
        // String bulletType and int bulletStrength methods
       /* if(isHit())
        {
            current_health -= bulletStrength(bulletType());
        }
        */

        return current_health;
    }

    void movement()
    {

    }

    boolean isColliding()
    {
    return false;  //default
    }

    //This method has to be created by player side
   /* boolean isShooting()
    {
    return false;  //default
    }
    */
}


class midBoss extends Enemy
{

}

class finalBoss extends Enemy
{

}