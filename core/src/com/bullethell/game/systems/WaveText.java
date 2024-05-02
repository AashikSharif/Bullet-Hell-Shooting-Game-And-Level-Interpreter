package com.bullethell.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bullethell.game.settings.LevelInterpreter;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.TimeUtils;

public class WaveText {
    private static final float WAVE_TEXT_DURATION = 2.0f;

    private BitmapFont waveFont;
    private String waveText;
    private boolean showWaveText;
    private float waveTextTimer;

    public WaveText() {
        this.waveFont = new BitmapFont();
        this.waveFont.getData().setScale(2);
        this.waveFont.setColor(Color.WHITE);
        this.waveText = "";
        this.showWaveText = false;
        this.waveTextTimer = 0.0f;
    }

    public void update(float seconds) {
        checkWaveText(seconds);

        if (showWaveText) {
            waveTextTimer -= Gdx.graphics.getDeltaTime();
            if (waveTextTimer <= 0) {
                showWaveText = false;
                waveText = "";
            }
        }
    }

    public void render(SpriteBatch spriteBatch) {
        if (showWaveText) {
            GlyphLayout layout = new GlyphLayout(waveFont, waveText);
            float textWidth = layout.width;
            float textHeight = layout.height;
            float x = (Gdx.graphics.getWidth() - textWidth) / 2;
            float y = (Gdx.graphics.getHeight() + textHeight) / 2;
            waveFont.draw(spriteBatch, layout, x, y);
        }
    }

    private void checkWaveText(float seconds) {
        LevelInterpreter levelInterpreter = Settings.getInstance().getLevelInterpreter();
        for (int i = 0; i < levelInterpreter.getWaves().size(); i++) {
            LevelInterpreter.Wave wave = levelInterpreter.getWaves().get(i);
            if (seconds >= TimeUtils.convertToSeconds(wave.getStart()) && seconds <= TimeUtils.convertToSeconds(wave.getStart()) + 2) {
                waveText = "WAVE " + (i + 1);
                showWaveText = true;
                waveTextTimer = WAVE_TEXT_DURATION;
                break;
            }
        }
    }
}
