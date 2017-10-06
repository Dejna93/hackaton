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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.enemies.Pocisk;
import com.mygdx.enemies.Eksplozja;
import com.mygdx.enemies.Tank;
import com.mygdx.helpers.Kierunek;

import java.util.Iterator;
import java.util.Random;

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
    private Array<Pocisk> bullets = new Array<Pocisk>();
    private Array<Eksplozja> eksplozjaArray = new Array<Eksplozja>();

    private Texture shootButtonTexture;
    private Rectangle shootButton;

    public PlayScreen(MyGame game) {
        this.game = game;
        img = new Texture("badlogic.jpg");
        gameCamera = new OrthographicCamera(MyGame.V_WIDTH, MyGame.V_HEIGHT);
        gameCamera.setToOrtho(false);
        gamePort = new FillViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, gameCamera);

        shootButtonTexture = new Texture("shoot.png");
        shootButton = new Rectangle(MyGame.V_WIDTH - 100, 60, 64, 64);
        player = new Tank(this.game.batch);
        player.setPosition(MyGame.V_WIDTH / 2, MyGame.V_HEIGHT / 2);
        for (int i = 0; i < 4; i++) {
            createTankInRandomLocation();
        }
        gameCamera.position.set(MyGame.V_WIDTH / 2, MyGame.V_HEIGHT / 2, 0);
    }

    public void update(float dt) {
        handleKey(dt);
    }

    public void createTank(float x, float y) {
        Tank tank = new Tank(this.game.batch);
        tank.setPositionFromRectangle(new Rectangle(x, y, 16, 16));
        enemyTanks.add(tank);
    }

    public void createTankInRandomLocation() {
        Random random = new Random();
        float x = (float) (random.nextInt(MyGame.V_WIDTH + 16) - 32);
        float y = (float) (random.nextInt(MyGame.V_HEIGHT + 16) - 32);
        createTank(x, y);
    }

    public void handleKey(float dt) {
        boolean allClear = true;
        Rectangle endPosition;

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            gameCamera.unproject(touchPos);
            Rectangle tankPos = player.getRectangle();

            if (shootButton.contains(touchPos.x, touchPos.y)) {
                if (player.lastShotTime + 500 < TimeUtils.millis()) {
                    bullets.add(new Pocisk(game.batch, player.getDirection(), player.getX() + 7.5f, player.getY() + 7.5f));
                    player.lastShotTime = TimeUtils.millis();
                }
            } else if ((Math.abs(touchPos.x - tankPos.getX())) > (Math.abs(touchPos.y - tankPos.getY()))) {
                if (touchPos.x > tankPos.getX()) {
                    player.move(Kierunek.PRAWO, dt, enemyTanks);
                } else {
                    player.move(Kierunek.LEWO, dt, enemyTanks);
                }
            } else {
                if (touchPos.y > tankPos.getY()) {
                    player.move(Kierunek.GORA, dt, enemyTanks);
                } else {
                    player.move(Kierunek.DOL, dt, enemyTanks);
                }
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            player.move(Kierunek.LEWO, dt, enemyTanks);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.move(Kierunek.PRAWO, dt, enemyTanks);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.move(Kierunek.GORA, dt, enemyTanks);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.move(Kierunek.DOL, dt, enemyTanks);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (player.lastShotTime + 500 < TimeUtils.millis()) {
                bullets.add(new Pocisk(game.batch, player.getDirection(), player.getX() + 7.5f, player.getY() + 7.5f));
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

        game.batch.begin();
        game.batch.draw(shootButtonTexture, shootButton.x, shootButton.y, shootButton.getWidth(), shootButton.getHeight());
        game.batch.end();

        gameCamera.update();
        player.draw();
        for (Tank tank : enemyTanks) {
            tank.draw();
        }

        Iterator<Pocisk> iter = bullets.iterator();
        while (iter.hasNext()) {
            Pocisk bullet = iter.next();
            if (bullet.inGameArea(bullet.getRectangle(), -3)) {
                bullet.move();
                bullet.draw();
                Iterator<Tank> tankIter = enemyTanks.iterator();
                while (tankIter.hasNext()) {
                    Tank tank = tankIter.next();
                    if (bullet.isCollision(bullet.getRectangle(), tank.getRectangle())){
                        utworzEksplozje(tank);
                        usunCzolg(tank);
                        usunPocisk(bullet);
                        createTankInRandomLocation();
                        break;
                    }
                }
            } else {
                bullets.removeIndex(bullets.indexOf(bullet, false));
            }
        }
        rysujEksplozje();
        game.batch.begin();
        game.batch.end();
    }

    private void usunCzolg(Tank czolg){
        enemyTanks.removeIndex(enemyTanks.indexOf(czolg, false));
    }

    private void usunPocisk(Pocisk pocisk){
        bullets.removeIndex(bullets.indexOf(pocisk, false));
    }

    private void usunEksplozje(Eksplozja eksplozja){
        eksplozjaArray.removeIndex(eksplozjaArray.indexOf(eksplozja, false));
    }

    private void utworzEksplozje(Tank czolg){
        Vector2 srodek_czolgu = new Vector2();
        czolg.getRectangle().getCenter(srodek_czolgu);
        Eksplozja eksplozja = new Eksplozja(game.batch, srodek_czolgu.x, srodek_czolgu.y);
        eksplozjaArray.add(eksplozja);
    }

    private void rysujEksplozje(){
        Iterator<Eksplozja> iterator = eksplozjaArray.iterator();
        while(iterator.hasNext()){
            Eksplozja eksplozja = iterator.next();
            if (eksplozja.czy_trwa()){
                eksplozja.rysuj();
            }else{
                usunEksplozje(eksplozja);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height, true);
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
