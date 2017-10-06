package com.mygdx.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class Explosion {

    private static final int FRAME_COLS = 6, FRAME_ROWS = 5;

    Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    Texture explosionTexture;
    float stateTime;

    public Explosion(float x, float y)
    {
        explosionTexture = new Texture("animation_sheet.png");
    }

}
