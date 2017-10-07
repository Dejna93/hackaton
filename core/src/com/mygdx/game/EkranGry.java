package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.enemies.Pocisk;
import com.mygdx.enemies.Eksplozja;
import com.mygdx.enemies.Czolg;
import com.mygdx.helpers.Kierunek;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class EkranGry implements Screen {

    private MojaGra game;
    private Viewport graViewport;
    private OrthographicCamera kamera;

    private Czolg gracz;
	private Czolg gracz2;

    private Array<Czolg> wrogieCzolgi = new Array<Czolg>();
    private Array<Pocisk> pociski = new Array<Pocisk>();
    private Array<Eksplozja> eksplozjaArray = new Array<Eksplozja>();

    private Texture strzalTexture;
    private Texture tloTexture;
    private Rectangle strzalPrzycisk;
    private Rectangle tlo_gry;

    private Sound wystrzal;

    public EkranGry(MojaGra game) {
        this.game = game;

        kamera = new OrthographicCamera(MojaGra.V_WIDTH, MojaGra.V_HEIGHT);
        kamera.setToOrtho(false);
        graViewport = new FillViewport(MojaGra.V_WIDTH, MojaGra.V_HEIGHT, kamera);
        wystrzal = Gdx.audio.newSound(Gdx.files.internal("grenade-launcher-daniel_simon.mp3"));
        tloTexture = new Texture("tlo_gry.jpg");
        strzalTexture = new Texture("shoot.png");
        tlo_gry = new Rectangle(0, 0, MojaGra.V_WIDTH, MojaGra.V_HEIGHT);
        strzalPrzycisk = new Rectangle(MojaGra.V_WIDTH - 100, 60, 64, 64);
        gracz = new Czolg(this.game.batch, false, true);
        gracz.setPosition(MojaGra.V_WIDTH / 2, MojaGra.V_HEIGHT / 2);
        for (int i = 0; i < 20; i++) {
            losujPozycjeCzolgu();
        }
        kamera.position.set(MojaGra.V_WIDTH / 2, MojaGra.V_HEIGHT / 2, 0);
    }

    public void odswiez(float dt) {
        obsluzAkcje(dt);
    }

    public void stworzCzolg(float x, float y) {
        Czolg tank = new Czolg(this.game.batch, true, false);
        tank.ustawPozycjeZKwadratu(new Rectangle(x, y, 16, 16));
        wrogieCzolgi.add(tank);
    }

    public void losujPozycjeCzolgu() {
        Random random = new Random();
        float x = (float) (random.nextInt(MojaGra.V_WIDTH + 16) - 32);
        float y = (float) (random.nextInt(MojaGra.V_HEIGHT + 16) - 32);
        stworzCzolg(x, y);
    }

    public void obsluzAkcje(float dt) {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            kamera.unproject(touchPos);
            Rectangle tankPos = gracz.getRectangle();

            if (strzalPrzycisk.contains(touchPos.x, touchPos.y)) {
                if (gracz.ostatniStrzal + 500 < TimeUtils.millis()) {
                    pociski.add(new Pocisk(game.batch, gracz.getKierunek(), gracz.getX() + 7.5f, gracz.getY() + 7.5f));
                    gracz.ostatniStrzal = TimeUtils.millis();
                }
            } else if ((Math.abs(touchPos.x - tankPos.getX())) > (Math.abs(touchPos.y - tankPos.getY()))) {
                if (touchPos.x > tankPos.getX()) {
                    gracz.porusz(Kierunek.PRAWO, dt, wrogieCzolgi);
                } else {
                    gracz.porusz(Kierunek.LEWO, dt, wrogieCzolgi);
                }
            } else {
                if (touchPos.y > tankPos.getY()) {
                    gracz.porusz(Kierunek.GORA, dt, wrogieCzolgi);
                } else {
                    gracz.porusz(Kierunek.DOL, dt, wrogieCzolgi);
                }
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
			gracz.porusz(Kierunek.LEWO, dt, wrogieCzolgi);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gracz.porusz(Kierunek.PRAWO, dt, wrogieCzolgi);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            gracz.porusz(Kierunek.GORA, dt, wrogieCzolgi);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            gracz.porusz(Kierunek.DOL, dt, wrogieCzolgi);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (gracz.ostatniStrzal + 500 < TimeUtils.millis()) {
                pociski.add(new Pocisk(game.batch, gracz.getKierunek(), gracz.getX() + 7.5f, gracz.getY() + 7.5f));
                wystrzal.play();
                gracz.ostatniStrzal = TimeUtils.millis();
            }
        }
    }

    @Override
    public void render(float delta) {
        odswiez(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(kamera.combined);

        game.batch.begin();
        game.batch.draw(tloTexture, tlo_gry.x, tlo_gry.y, tlo_gry.getWidth(), tlo_gry.getHeight());
        game.batch.end();

        game.batch.begin();
        game.batch.draw(strzalTexture, strzalPrzycisk.x, strzalPrzycisk.y, strzalPrzycisk.getWidth(), strzalPrzycisk.getHeight());
        game.batch.end();

        kamera.update();
        gracz.rysujCzolg();
        for (Czolg tank : wrogieCzolgi) {
            tank.rysujCzolg();
            tank.wylosujRuchCzolgu(delta, gracz);
        }

        Iterator<Pocisk> iter = pociski.iterator();
        while (iter.hasNext()) {
            Pocisk bullet = iter.next();
            if (bullet.czyJestWObszarzeGry(bullet.getRectangle(), -3)) {
                bullet.lotPocisku();
                bullet.rysujPocisk();
                Iterator<Czolg> tankIter = wrogieCzolgi.iterator();
                while (tankIter.hasNext()) {
                    Czolg tank = tankIter.next();
                    if (bullet.czyKolizja(bullet.getRectangle(), tank.getRectangle())){
                        utworzEksplozje(tank);
                        usunCzolg(tank);
                        usunPocisk(bullet);
                        losujPozycjeCzolgu();

                        break;
                    }
                }
            } else {
                pociski.removeIndex(pociski.indexOf(bullet, false));
            }
        }
        rysujEksplozje();
        game.batch.begin();
        game.batch.end();
    }

    private void usunCzolg(Czolg czolg){
        wrogieCzolgi.removeIndex(wrogieCzolgi.indexOf(czolg, false));
    }

    private void usunPocisk(Pocisk pocisk){
        pociski.removeIndex(pociski.indexOf(pocisk, false));
    }

    private void usunEksplozje(Eksplozja eksplozja){
        eksplozjaArray.removeIndex(eksplozjaArray.indexOf(eksplozja, false));
    }

    private void utworzEksplozje(Czolg czolg){
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
        graViewport.update(width, height, true);
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
        game.batch.dispose();
    }
}
