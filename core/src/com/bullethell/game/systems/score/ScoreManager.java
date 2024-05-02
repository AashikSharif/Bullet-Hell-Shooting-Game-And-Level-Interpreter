package com.bullethell.game.systems.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.JsonUtil;


public class ScoreManager {
    private int score = 0;
    private int highScore = 0;

    private BitmapFont scoreFont;
    private String scoreText;
    private String highScoreText;


    public ScoreManager() {
        this.scoreFont = new BitmapFont();
        this.scoreText = "Score: 0";
        this.scoreFont.setColor(Color.WHITE);
        this.highScore = getHighScore();
        updateHighScoreText();
    }

    public void increaseScore(int amount) {
        score += amount;
        updateScoreText();


        if (score > highScore) {
            highScore = score;
            updateHighScoreText();
            setHighScore(this.highScore);
        }
    }

    public int getHighScore()
    {
        return Settings.getInstance().getHighScore();
    }
    public void setHighScore(int newHighScore)
    {
        Settings.getInstance().setHighScore(newHighScore);
    }
    public void saveHighScore(Settings settings)
    {
        JsonUtil jsonUtil = new JsonUtil();
        jsonUtil.save(settings, "settings/settings.json");

    }


    public void resetScore() {
        score = 0;
        updateScoreText();
    }

    private void updateScoreText() {
        scoreText = "Score: " + score;
    }

    private void updateHighScoreText() {
        highScoreText = "High Score: " + highScore;
    }
    public void render(SpriteBatch spriteBatch) {
        scoreFont.getData().setScale(1.5f);
        scoreFont.setColor(0.2f, 0.5f, 1.0f, 1.0f);

        scoreFont.draw(spriteBatch, scoreText, 10, Gdx.graphics.getHeight() - 20);
        scoreFont.setColor(0.8f, 0.9f, 1.0f, 1.0f);
        scoreFont.draw(spriteBatch, scoreText, 10, Gdx.graphics.getHeight() - 20);
        scoreFont.draw(spriteBatch, highScoreText, 10, Gdx.graphics.getHeight() - 40);


        scoreFont.getData().setScale(1.0f);

    }
}

