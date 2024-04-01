package com.bullethell.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bullethell.game.BulletHellGame;
import com.bullethell.game.settings.PlayerSettings;
import com.bullethell.game.settings.Settings;
import com.bullethell.game.utils.JsonUtil;
import jdk.tools.jmod.Main;

import java.util.HashMap;

public class OptionsScreen implements Screen {

    private final BulletHellGame game;
    private final Texture background;
    private final Stage stage;
    private final Skin skin;
    private Settings settings;

    private final HashMap<String, String> controlMappings = new HashMap<>();

    public OptionsScreen(BulletHellGame game) {
        this.game = game;
        this.background = new Texture("bg.png");
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("skin/glassy/skin/glassy-ui.json"));

        Gdx.input.setInputProcessor(stage);

        loadMenu();
        loadSettings();
        initializeControlMappings();
        displayKeyBinds();
    }


    private void loadMenu() {
        float windowHeight = Gdx.graphics.getHeight();
        float windowWidth = Gdx.graphics.getWidth();

        createControlButton("Up", 200, 600);
        createControlButton("Down", 200, 500);
        createControlButton("Left", 200, 400);
        createControlButton("Right", 200, 300);
        createControlButton("Slow Mode", 200, 200);

        createTextField("Up",600,600,"Up");
        createTextField("Down",600,500,"Down");
        createTextField("Left",600,400,"Left");
        createTextField("Right",600,300,"Right");
        createTextField("Slow Mode",600,200,"Slow Mode");

        // Save Button
        TextButton saveButton = new TextButton("Save", skin, "small");
        saveButton.setSize(200, 50);
        saveButton.setPosition(windowWidth - 250, 100);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Save controlMappings to a file
                saveControlMappings();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        //Back to Main Menu
        TextButton backToMenu = new TextButton("Back to Main Menu", skin, "small");
        backToMenu.setSize(200, 50);
        backToMenu.setPosition(windowWidth - 500, 100);
        backToMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toMainMenu();
            }
        });

        stage.addActor(saveButton);
        stage.addActor(backToMenu);


    }

    private void createControlButton(final String control, float x, float y) {
        TextButton button = new TextButton(control, skin, "small");
        int constantSize = 50;
        button.setSize(constantSize *4, constantSize);
        button.setPosition(x, y);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                captureKeyInput(control);
            }
        });

        stage.addActor(button);
    }
    private void createTextField(final String fieldName, float x, float y, String key){
        TextField field = new TextField(fieldName,skin);
        int constantSize = 50;
        field.setSize(constantSize*4,constantSize);
        field.setPosition(x,y);
        field.setText(controlMappings.get(key));

        stage.addActor(field);
    }

    private void captureKeyInput(final String controlName) {
        final InputProcessor originalInputProcessor = Gdx.input.getInputProcessor();
        InputProcessor keyCaptureProcessor = new InputAdapter() {
            @Override
            public boolean keyUp(int keycode) {
                controlMappings.put(controlName, Input.Keys.toString(keycode));
                Gdx.app.log("Control Mappings", controlMappings.toString());
                Gdx.input.setInputProcessor(originalInputProcessor);
                return true;
            }
        };
        Gdx.input.setInputProcessor(keyCaptureProcessor);
    }
    private void loadSettings() {
        JsonUtil jsonUtil = new JsonUtil();
        settings = jsonUtil.deserializeJson("settings/settings.json", Settings.class);
    }
    private void saveControlMappings() {
        // Implement saving logic here, potentially using Json to serialize controlMappings
        PlayerSettings ps = settings.getPlayerSettings();
        ps.setMoveUp(controlMappings.get("Up"));
        ps.setMoveDown(controlMappings.get("Down"));
        ps.setMoveLeft(controlMappings.get("Left"));
        ps.setMoveRight(controlMappings.get("Right"));
        ps.setShoot(controlMappings.get("shoot"));
        ps.setNormalSpeed(Float.parseFloat(controlMappings.get("normalSpeed")));
        ps.setSlowSpeed(Float.parseFloat(controlMappings.get("slowSpeed")));
//        ps.setNormalSpeed(settings.getPlayerSettings().getNormalSpeed());
//        ps.setSlowSpeed(settings.getPlayerSettings().getSlowSpeed());
//        ps.setShoot(settings.getPlayerSettings().getShoot());
        settings.setPlayerSettings(ps);

        saveSettings(settings);
    }

    private void initializeControlMappings() {
        if (settings != null && settings.getPlayerSettings() != null) {
            PlayerSettings playerSettings = settings.getPlayerSettings();
            controlMappings.put("Up", playerSettings.getMoveUp());
            controlMappings.put("Down", playerSettings.getMoveDown());
            controlMappings.put("Left", playerSettings.getMoveLeft());
            controlMappings.put("Right", playerSettings.getMoveRight());
            controlMappings.put("slowMode", playerSettings.getSlowMode());
            controlMappings.put("shoot", playerSettings.getShoot());
            controlMappings.put("normalSpeed", playerSettings.getNormalSpeed() + "");
            controlMappings.put("slowSpeed", playerSettings.getSlowSpeed() + "");
        }
    }

    private void displayKeyBinds(){

    }



    private void saveSettings(Settings settings) {
        JsonUtil jsonUtil = new JsonUtil();
        jsonUtil.save(settings, "settings/settings.json");
    }

    private void toMainMenu(){
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        skin.dispose();
    }
}
