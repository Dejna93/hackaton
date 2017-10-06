package com.mygdx.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class Pomocnik {
    public boolean czyJestWObszarzeGry(Rectangle rec, float offset){
        return rec.getX() >= 0 && rec.getY() >= 0 && rec.getX() <= Gdx.graphics.getBackBufferWidth() + offset && rec.getY() <= Gdx.graphics.getBackBufferHeight() + offset;
    }
    public boolean czyKolizja(Rectangle a , Rectangle b)
    {
        return a.overlaps(b);
    }

}
