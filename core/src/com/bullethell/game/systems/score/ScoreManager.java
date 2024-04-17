package com.bullethell.game.systems.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreManager {
    private int score = 0;
    private BitmapFont scoreFont;
    private String scoreText;

    public ScoreManager() {
        this.scoreFont = new BitmapFont();
        this.scoreText = "Score: 0";
        this.scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void increaseScore(int amount) {
        score += amount;
        updateScoreText();
    }

    public void resetScore() {
        score = 0;
        updateScoreText();
    }

    private void updateScoreText() {
        scoreText = "Score: " + score;
    }

    public void render(SpriteBatch spriteBatch) {
        scoreFont.draw(spriteBatch, scoreText, 10, Gdx.graphics.getHeight() - 20);
    }
}

