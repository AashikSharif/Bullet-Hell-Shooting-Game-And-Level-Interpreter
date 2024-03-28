//package com.bullethell.game.screens;
//
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Camera;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.utils.viewport.StretchViewport;
//import com.badlogic.gdx.utils.viewport.Viewport;
//import com.bullethell.game.BulletHellGame;
//import com.bullethell.game.systems.GameSystem;
//import com.bullethell.game.systems.ScoringSystem;
//import com.bullethell.game.updates.GameUpdate;
//import com.sun.org.apache.xpath.internal.operations.Or;
//
//public class LayoutScreen extends GameUpdate {
//    private final Camera cameraBackground;
//
//    private final Viewport viewportBackground;
//    private final Texture background;
//    private final Texture heart;
//    //private final Texture infinity;
//    private int score;
//    private int heartCount;
//    private final BitmapFont font0, font1;
//
//    private SpriteBatch sbatch;
//    private String mode;
//    private GameSystem observer;
//    private final Skin skin;
//    private final Stage stage;
//    private int hpYposition;
//
//    public LayoutScreen(ScoringSystem sb){
//        this.sb = sb;
//        cameraBackground = new OrthographicCamera();
//        viewportBackground = new StretchViewport(836,820);
//        this.background = new Texture("bg.png");
//        this.heart = new Texture("heart.png");
//        this.score = sb.getScore();
//        this.heartCount = sb.getLives();
//        this.sbatch = new SpriteBatch();
//        this.mode = "Default";
//        this.skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));
//        this.stage = new Stage(this.viewportBackground);
//        this.hpYposition = 820-(140+ 50)-50;
//        this.font0 = new BitmapFont();
//        this.font1 = new BitmapFont();
//        this.font0.setColor(0, 0, 0, 1);
//        this.font0.getData().setScale(2f);
//        this.font1.setColor(1,1,1,1);
//        this.font1.getData().setScale(2f);
//    }
//
//    @Override
//    public void updateScore() {
//        this.score = sb.getScore();
//    }
//
//    @Override
//    public void updateHealth() {
//        this.heartCount = sb.getLives();
//
//    }
//
//    public void renderBackground(){
//        sbatch.setProjectionMatrix(cameraBackground.combined);
//        Gdx.gl.glViewport(0,0, 836, 820);
//        sbatch.begin();
//        sbatch.draw(this.background, 0, 0, 836, 820);
//        font0.draw(sbatch, mode, 891, 800);
//        //font1.draw(sbatch, "HiScore: "+String.format("%08d", HiScore), Constant.WINDOW_WIDTH+15, Constant.WINDOW_HEIGHT-60);
//        font1.draw(sbatch, "Score: "+String.format("%08d", this.score), 851, 760);
//        font0.draw(sbatch, "HP: ", 851, this.hpYposition);
//        this.showLives();
//        sbatch.end();
//        stage.act();
//        stage.draw();
//    }
//    private void showLives(){
//        for(int i=0; i<this.heartCount; i++)
//            sbatch.draw(heart, 851+((i%6)*50),40);
//    }
//}
