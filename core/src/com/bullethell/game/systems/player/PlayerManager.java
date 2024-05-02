package com.bullethell.game.systems.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bullethell.game.Patterns.Factory.EntityFactory;
import com.bullethell.game.Patterns.Factory.PlayerFactory;
import com.bullethell.game.Patterns.observer.IObserver;
import com.bullethell.game.entities.Bullet;
import com.bullethell.game.entities.Player;
import com.bullethell.game.systems.AssetHandler;
import com.bullethell.game.utils.Renderer;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private Player player;
    private Renderer renderer;
    private AssetHandler assetHandler;
    private List<Bullet> playerBullets;

    public PlayerManager(AssetHandler assetHandler, Renderer renderer, IObserver observer) {
        this.renderer = renderer;
        this.assetHandler = assetHandler;
        this.playerBullets = new ArrayList<>();
        initializePlayer(observer);
    }

    private void initializePlayer(IObserver observer) {
        EntityFactory playerFactory = new PlayerFactory();
        player = (Player) playerFactory.createEntity(
                (float) Gdx.graphics.getWidth() / 2 - 66, 0, assetHandler, "Player", new Vector2(), 25, 5
        );
        player.addObserver(observer);
    }

    public void render(SpriteBatch spriteBatch) {
        renderer.renderEntity(spriteBatch, player, false);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Bullet> getPlayerBullets() {
        return playerBullets;
    }
}

