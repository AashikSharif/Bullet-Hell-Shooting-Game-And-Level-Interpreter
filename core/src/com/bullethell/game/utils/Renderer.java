package com.bullethell.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bullethell.game.entities.Entity;

public class Renderer {
    public Renderer () {}
    public void renderEntity(SpriteBatch spriteBatch, Entity entity, boolean renderHitBox) {
        spriteBatch.draw(entity.sprite, entity.sprite.getX(), entity.sprite.getY());

        // for debugging
        if (renderHitBox) {
            // End the sprite batch before starting shape rendering
            spriteBatch.end();

            entity.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            entity.shapeRenderer.setColor(Color.BLUE);

            entity.shapeRenderer.rect(
                    entity.getHitbox().x,
                    entity.getHitbox().y,
                    entity.getHitbox().width,
                    entity.getHitbox().height
            );

            entity.shapeRenderer.end();

            spriteBatch.begin();
        }
    }

    public void renderBackground(SpriteBatch spriteBatch, Texture background) {
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
