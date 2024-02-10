package com.bullethell.game.entities;

import com.badlogic.gdx.InputProcessor;
import com.bullethell.game.settings.PlayerSettings;

public class Player extends Entity implements InputProcessor {
    PlayerSettings playerSettings;
    public Player(float x, float y, PlayerSettings playerSettings) {
        super(x, y, "player.png");
        this.playerSettings = playerSettings;
    }

    public void update (float dT) {
        super.update(dT);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
