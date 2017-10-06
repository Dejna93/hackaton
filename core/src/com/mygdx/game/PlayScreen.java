package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.enemies.Player;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class PlayScreen implements Screen {

    private Texture img;
    private MyGame game;
    private Viewport gamePort;
    private OrthographicCamera gameCamera;

    private Player player;


    public PlayScreen(MyGame game) {
        this.game = game;
        img = new Texture("badlogic.jpg");
        gameCamera = new OrthographicCamera();
        gamePort = new FillViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, gameCamera);
        player = new Player(this.game.batch);
    }

    public void update(float dt) {
        handleKey(dt);
    }

    public void handleKey(float dt) {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            gameCamera.unproject(touchPos);
//            if (leftButton.contains(new Vector2(touchPos.x,touchPos.y))){
//                Gdx.app.log("Mouse Event","Projected at " + touchPos.x + "," + touchPos.y);
//            }
        }
//        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) leftButton.x -= 200 * dt;
//        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) leftButton.x += 200 * dt;
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gameCamera.combined);

        gameCamera.update();
        player.draw();

        game.batch.begin();
        //game.batch.draw(img, leftButton.x, leftButton.y);
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
        img.dispose();
        game.batch.dispose();
    }
}
