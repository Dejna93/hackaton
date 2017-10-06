package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by d.holuj on 06-Oct-17.
 */

public class GameLauncher implements Screen {


    private Texture background;
    private final String title = "ULTIMATE TANK SHOOTER";
    private  final String instruction_1 = "Press TANK for RAINING";
    private  final String instruction_2 = "BLOOOOOOOOD!!";
    private MyGame game;
    private Viewport gamePort;
    private OrthographicCamera gameCamera;
    BitmapFont wzialemToZNeta;


    public GameLauncher(MyGame game) {
        this.game = game;
        background = new Texture("background.png");
        gameCamera = new OrthographicCamera();
        gamePort = new FillViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, gameCamera);

        wzialemToZNeta = new BitmapFont(Gdx.files.internal("font.fnt"));
    }

    public void update() {
        handleKey();
    }

    public void handleKey() {
        if (Gdx.input.isTouched()) {
            game.setScreen(new PlayScreen(game));
//            if (leftButton.contains(new Vector2(touchPos.x,touchPos.y))){
//                Gdx.app.log("Mouse Event","Projected at " + touchPos.x + "," + touchPos.y);
//            }
        }
//        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) leftButton.x -= 200 * dt;
//        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) leftButton.x += 200 * dt;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gameCamera.combined);

        update();
        gameCamera.update();

        wzialemToZNeta.setColor(255,0,0,50);
        game.batch.begin();

        game.batch.draw(background, 0 - 400/2, 0 - 240/2 + 50);  // + 50 -> y offset
        wzialemToZNeta.draw(game.batch, title , -400 , 200);  // Gorny napis
        wzialemToZNeta.draw(game.batch, instruction_1, -400 , -100);  // Dolny napis 1/2
        wzialemToZNeta.draw(game.batch, instruction_2, -200 , -200);  // Dolny napis 2/2

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        game.batch.dispose();
    }
}
