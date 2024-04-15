package com.bullethell.game.utils;

public class Event {
    public enum Type {
        PLAYER_SHOOT,
    }

    private Type type;

    public Event(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
