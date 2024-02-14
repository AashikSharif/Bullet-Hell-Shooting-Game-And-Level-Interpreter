package com.bullethell.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bullethell.game.screens.GameScreen;
import com.bullethell.game.screens.MainMenuScreen;
import com.bullethell.game.settings.GlobalSettings;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.JsonUtil;
import jdk.tools.jmod.Main;

import java.util.Objects;

public class BulletHellGame extends Game {
	public SpriteBatch batch;

	public Settings settings;


	@Override
	public void create () {
		preflight();
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));

	}
	public Settings getSettings(){
		return settings;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}


	private void preflight() {
		JsonUtil jsonUtil = new JsonUtil();
		settings = jsonUtil.deserializeJson("settings/settings.json", Settings.class);
	}
}
