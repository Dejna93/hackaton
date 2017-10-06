package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class MyGame extends Game {

    public SpriteBatch batch;
    public static int V_WIDTH = 800;
    public static int V_HEIGHT = 480;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
    }
}
