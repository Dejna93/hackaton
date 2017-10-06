package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class MyGame extends Game {

    public SpriteBatch batch;
    public static int V_WIDTH = 800;
    public static int V_HEIGHT = 480;
    private Music backgroundMusic;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new GameLauncher(this));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundmusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }
}
