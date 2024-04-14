package com.bullethell.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Player;
import com.bullethell.game.settings.PlayerSettings;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.utils.EdgeDetector;

import java.util.List;

public class PlayerController {
    Player player;
    PlayerSettings playerSettings;
    EdgeDetector edgeDetector;

    private float coolDown = 0;
    private float timeSinceLastShot = 0;
    public PlayerController (Player player, PlayerSettings playerSettings) {
        this.player = player;
        this.playerSettings = playerSettings;
        this.coolDown = 0.2f;
        this.edgeDetector = new EdgeDetector(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void listen (List<Bullet> playerBullets, AssetHandler assetHandler, float time) {
        timeSinceLastShot += time;
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

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(playerSettings.getShoot())) && timeSinceLastShot >= coolDown) {
            timeSinceLastShot = 0;
            playerBullets.add(
                    new Bullet(
                            player.getPosition().x + (player.sprite.getWidth() / 2),
                            player.getPosition().y + player.sprite.getHeight(),
                            "bullet",
                            new Vector2(0, 5),
                            player.damage,
                            assetHandler
                    )
            );
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
