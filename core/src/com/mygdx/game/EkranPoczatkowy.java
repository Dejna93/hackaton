package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by d.holuj on 06-Oct-17.
 */

public class EkranPoczatkowy implements Screen {


    private Texture background;
    private final String tytul = "ULTIMATE TANK SHOOTER";
    private final String instrukcja_1 = "Press TANK for RAINING";
    private final String instrukcja_2 = "BLOOOOOOOOD!!";
    private MojaGra gra;
    private Viewport graViewport;
    private OrthographicCamera kamera;
    BitmapFont czcionka;


    public EkranPoczatkowy(MojaGra gra) {
        this.gra = gra;
        background = new Texture("background.png");
        kamera = new OrthographicCamera();
        graViewport = new FillViewport(MojaGra.V_WIDTH, MojaGra.V_HEIGHT, kamera);

        czcionka = new BitmapFont(Gdx.files.internal("font.fnt"));
    }

    public void odswiez() {
        obsluzAkcje();
    }

    public void obsluzAkcje() {
        if (Gdx.input.isTouched()) {
            gra.setScreen(new EkranGry(gra));
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gra.batch.setProjectionMatrix(kamera.combined);

        odswiez();
        kamera.update();

        czcionka.setColor(255,0,0,50);
        gra.batch.begin();

        gra.batch.draw(background, 0 - 400/2, 0 - 240/2 + 50);  // + 50 -> y offset
        czcionka.draw(gra.batch, tytul, -400 , 200);  // Gorny napis
        czcionka.draw(gra.batch, instrukcja_1, -400 , -100);  // Dolny napis 1/2
        czcionka.draw(gra.batch, instrukcja_2, -200 , -200);  // Dolny napis 2/2

        gra.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        graViewport.update(width, height);
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
        gra.batch.dispose();
    }
}
