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
    boolean isCheat;
    private static final float HITBOX_WIDTH = 30;
    private static final float HITBOX_HEIGHT = 30;
    public int damage;
    private int lives;
    private boolean bombUsed = false;
    private AssetHandler assetHandler;
    private int bombsUsed;
    private static final int MAX_BOMBS = 3;

    private static Player player;
    private Player(float x, float y, AssetHandler assetHandler, int damage, int lives) {
        super(x, y, "player", assetHandler);
        this.lives = lives; //Initialize the lives of the player
        this.damage = damage;
        this.bombsUsed = 0;
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
    public boolean canUseBomb() {
        return player.getLives() == 1 && bombsUsed < MAX_BOMBS;
    }
    public void useBomb() {
        if (canUseBomb()) {
            bombsUsed++;
            System.out.println("Bomb used: " + bombsUsed);
        } else {
            System.out.println("Cannot use bomb. Either maximum usage reached or health not at 1.");
        }
    }
    @Override
    public void shoot() {
        notifyObservers(new Event(Event.Type.PLAYER_SHOOT, this));
    }
    public void shootBomb() {
        notifyObservers(new Event(Event.Type.PLAYER_SHOOT_BOMB, this));
    }
    public void setBombUsed(boolean bombUsed) {
        this.bombUsed = bombUsed;
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
    public void cheatMode(boolean isCheat) {
        this.isCheat = isCheat;
        if(this.isCheat) {
            this.setLives(5);
        }
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
    public boolean isCheatMode(){
        return this.isCheat;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void resetPosition() {
        this.setPosition(new Vector2(Gdx.graphics.getWidth() / 2f - 66, 0) );
    }

    public void reset() {
        this.setLives(5);
        resetPosition();
    }

    @Override
    public void registerObserver(IObserver observer) {
        if (!this.getObservers().contains(observer)) {
            this.addObserver(observer);
        }
    }

    @Override
    public void removeObserver(IObserver observer) {
        this.getObservers().remove(observer);
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

