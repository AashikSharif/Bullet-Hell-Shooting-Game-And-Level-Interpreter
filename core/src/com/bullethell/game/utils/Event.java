package com.bullethell.game.utils;

public class Event {
    public enum Type {
        PLAYER_SHOOT,
        ENEMY_SHOOT,
        PLAYER_COLLIDED_ENEMY,
        BULLET_HIT_ENEMY,
        ENEMY_BULLET_HIT_PLAYER,
        GAME_OVER,
    }

    private Type type;
    private Object source;
    private Object destination;

    public Event(Type type) {
        this.type = type;
    }

    public Event(Type type, Object source) {
        this.type = type;
        this.source = source;
    }
    public Event(Type type, Object source, Object destination) {
        this.type = type;
        this.source = source;
        this.destination = destination;
    }

    public Type getType() {
        return type;
    }

    public Object getSource() {
        return source;
    }

    public Object getDestination() {
        return destination;
    }
}
