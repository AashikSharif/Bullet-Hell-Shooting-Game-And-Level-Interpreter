package com.bullethell.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.Patterns.observer.IObservable;
import com.bullethell.game.Patterns.observer.IObserver;
import com.bullethell.game.controllers.IControllable;
import com.bullethell.game.controllers.IShootable;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.utils.Event;

import java.util.List;

public class Player extends Entity implements IControllable, IShootable, IObservable {
    boolean isSlow = false;
    private static final float HITBOX_WIDTH = 30;
    private static final float HITBOX_HEIGHT = 30;
    public int damage;
    private int lives;
    private static Player player;
    private Player(float x, float y, AssetHandler assetHandler, int damage, int lives) {
        super(x, y, "player", assetHandler);
        this.lives = lives; //Initialize the lives of the player
        this.damage = damage;
    }

    public static Player getInstance(float x, float y, AssetHandler assetHandler, int damage, int lives) //Singleton class implementation
    {
        if (player == null)
            player = new Player(x, y, assetHandler, damage, lives);
        return player;
    }

    public void update() {
        super.update();
    }

    @Override
    public void shoot() {
        notifyObservers(new Event(Event.Type.PLAYER_SHOOT));
    }
    @Override
    public void moveUp(float speedFactor) {
        this.position.y += speedFactor;
    }

    @Override
    public void moveDown(float speedFactor) {
        this.position.y -= speedFactor;
    }

    @Override
    public void moveLeft(float speedFactor) {
        this.position.x -= speedFactor;
    }

    @Override
    public void moveRight(float speedFactor) {
        this.position.x += speedFactor;
    }


    @Override
    public void slowMode(boolean isSlow) {
        this.isSlow = isSlow;
    }

    public void lostLive() {
        lives--;
    }

    public boolean isGameOver() {
        if (lives > 0) return false;
        return true;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public void registerObserver(IObserver observer) {
        if (!this.getObservers().contains(observer)) {
            this.addObserver(observer);
        }
    }

    @Override
    public void removeObserver(IObserver observer) {
        this.removeObserver(observer);
    }

    @Override
    public void notifyObservers(Event event) {
        if (this.getObservers().isEmpty()) {
            return;
        }

        for (IObserver observer : this.getObservers()) {
            observer.onNotify(this, event);
        }
    }
}

