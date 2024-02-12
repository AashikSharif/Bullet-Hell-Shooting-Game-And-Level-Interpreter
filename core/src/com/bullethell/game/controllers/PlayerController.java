package com.bullethell.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.bullethell.game.entities.Player;
import com.bullethell.game.settings.PlayerSettings;
import com.bullethell.game.utils.EdgeDetector;

public class PlayerController {
    Player player;
    PlayerSettings playerSettings;

    EdgeDetector edgeDetector;
    public PlayerController (Player player, PlayerSettings playerSettings) {
        this.player = player;
        this.playerSettings = playerSettings;
        this.edgeDetector = new EdgeDetector(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void listen () {
        // if slow key is pressed, activate slow mode

        boolean isSlow = Gdx.input.isKeyPressed(Input.Keys.valueOf(playerSettings.getSlowMode()));
        player.slowMode(isSlow);
        float speedFactor = isSlow ? playerSettings.getSlowSpeed() : playerSettings.getNormalSpeed();

        // Player movement

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(playerSettings.getMoveUp()))) {
            player.moveUp(speedFactor);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(playerSettings.getMoveDown()))) {
            player.moveDown(speedFactor);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(playerSettings.getMoveLeft()))) {
            player.moveLeft(speedFactor);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(playerSettings.getMoveRight()))) {
            player.moveRight(speedFactor);
        }

        // edge detection

        if (!edgeDetector.isInBounds(player.getPosition(), player.sprite.getWidth(), player.sprite.getHeight())) {

            player.setPosition(
                    edgeDetector.correctBounds(
                            player.getPosition(),
                            player.sprite.getWidth(),
                            player.sprite.getHeight())
            );
        }

        player.update();
    }

}
