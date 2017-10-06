package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.enemies.Bullet;
import com.mygdx.enemies.Tank;
import com.mygdx.helpers.Direction;

import java.util.Iterator;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class PlayScreen implements Screen {

    private Texture img;
    private MyGame game;
    private Viewport gamePort;
    private OrthographicCamera gameCamera;

    private Tank player;

    private Array<Tank> enemyTanks = new Array<Tank>();
    private Array<Bullet> bullets = new Array<Bullet>();

    public PlayScreen(MyGame game) {
        this.game = game;
        img = new Texture("badlogic.jpg");
        gameCamera = new OrthographicCamera();
        gamePort = new FillViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, gameCamera);


        player = new Tank(this.game.batch);
        createTank(100, 100);
        createTank(-200, -200);
    }

    public void update(float dt) {
        handleKey(dt);
    }

    public void createTank(float x, float y){
        Tank tank = new Tank(this.game.batch);
        tank.setPositionFromRectangle(new Rectangle(x,y,16,16));
        enemyTanks.add(tank);
    }
    private boolean isCollision(Rectangle a , Rectangle b)
    {
        return a.overlaps(b);
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
        boolean allClear = true;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            for (Tank tank: enemyTanks){
                if (isCollision(player.moveLeft(), tank.getRectangle())){
                    allClear = false;
                    break;
                }
            }
            if (allClear){
                player.move(Direction.LEFT, dt);
            }

        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            for (Tank tank: enemyTanks){
                if (isCollision(player.moveRight(), tank.getRectangle())){
                    allClear = false;
                    break;
                }
            }
            if(allClear){
                player.move(Direction.RIGHT, dt);
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            for (Tank tank: enemyTanks) {
                if (isCollision(player.moveUp(), tank.getRectangle())){
                    allClear = false;
                    break;
                }
            }
            if(allClear){
                player.move(Direction.UP, dt);
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            for (Tank tank: enemyTanks) {
                if (isCollision(player.moveDown(), tank.getRectangle())){
                    allClear = false;
                    break;
                }
            }
            if(allClear){
                player.move(Direction.DOWN, dt);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            if (player.lastShotTime + 500 < TimeUtils.millis()){
                bullets.add(new Bullet(game.batch, player.getDirection(), player.getX() , player.getY()));
                player.lastShotTime = TimeUtils.millis();
            }
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gameCamera.combined);

        gameCamera.update();
        player.draw();
        //tank1.move(move)
        for (Tank tank: enemyTanks){
            tank.draw();
        }

        Iterator<Bullet> iter = bullets.iterator();
        while(iter.hasNext()) {
            Bullet bullet = iter.next();
            if (bullet.getX() >= Gdx.graphics.getBackBufferWidth() * -1 && bullet.getY() >= Gdx.graphics.getBackBufferHeight() * -1 && bullet.getX() <= Gdx.graphics.getBackBufferWidth() && bullet.getY() <= Gdx.graphics.getBackBufferHeight()){
                bullet.move();

                bullet.draw();
            }else{
                bullets.removeIndex(bullets.indexOf(bullet, false));
            }
        }

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
