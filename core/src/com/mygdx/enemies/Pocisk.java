package com.mygdx.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.helpers.Kierunek;
import com.mygdx.helpers.Pomocnik;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class Pocisk extends Pomocnik {

    private SpriteBatch batch;
    private int kierunek;
    private Sprite bulletSprite;

    public Pocisk(SpriteBatch batch, int kierunek, float x, float y){
        this.batch = batch;
        this.kierunek = kierunek;
        bulletSprite = new Sprite(new Texture("bullet.jpg"), 3 ,3);
        bulletSprite.setPosition(x, y);
    }

    public void lotPocisku(){
        if (kierunek == Kierunek.LEWO) {
            bulletSprite.setPosition(bulletSprite.getX() - 5, bulletSprite.getY());
        }
        if (kierunek == Kierunek.PRAWO) {
            bulletSprite.setPosition(bulletSprite.getX() + 5, bulletSprite.getY());
        }
        if (kierunek == Kierunek.GORA) {
            bulletSprite.setPosition(bulletSprite.getX() , bulletSprite.getY() + 5);
        }
        if (kierunek == Kierunek.DOL) {
            bulletSprite.setPosition(bulletSprite.getX() , bulletSprite.getY() - 5);
        }
    }

    public void rysujPocisk()
    {
        batch.begin();
        bulletSprite.draw(batch);
        batch.end();
    }

    private float getX() {
        return bulletSprite.getX();
    }

    private float getY() {
        return bulletSprite.getY();
    }

    public Rectangle getRectangle()
    {
        return new Rectangle(getX(),getY(),bulletSprite.getWidth(), bulletSprite.getHeight());
    }

}
