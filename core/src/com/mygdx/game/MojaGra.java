package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class MojaGra extends Game {

    public SpriteBatch batch;
    public static int V_WIDTH = 800;
    public static int V_HEIGHT = 480;
    private Music tloMuzyczne;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new EkranPoczatkowy(this));
        tloMuzyczne = Gdx.audio.newMusic(Gdx.files.internal("backgroundmusic.mp3"));
        tloMuzyczne.setLooping(true);
        tloMuzyczne.play();
    }
}
