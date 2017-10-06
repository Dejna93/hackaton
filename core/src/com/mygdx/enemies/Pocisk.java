package com.mygdx.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.helpers.Kierunek;
import com.mygdx.helpers.GameHelper;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class Pocisk extends GameHelper {

    private SpriteBatch batch;
    private int kierunek;
    private Sprite sprite;

    public Pocisk(SpriteBatch batch, int kierunek, float x, float y){
        this.batch = batch;
        this.kierunek = kierunek;
        sprite = new Sprite(new Texture("bullet.jpg"), 3 ,3);
        sprite.setPosition(x, y);
    }

    public void move(){
        if (kierunek == Kierunek.LEWO) {
            sprite.setPosition(sprite.getX() - 5, sprite.getY());
        }
        if (kierunek == Kierunek.PRAWO) {
            sprite.setPosition(sprite.getX() + 5, sprite.getY());
        }
        if (kierunek == Kierunek.GORA) {
            sprite.setPosition(sprite.getX() , sprite.getY() + 5);
        }
        if (kierunek == Kierunek.DOL) {
            sprite.setPosition(sprite.getX() , sprite.getY() - 5);
        }
    }

    public void draw()
    {
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    private float getX() {
        return sprite.getX();
    }

    private float getY() {
        return sprite.getY();
    }

    public Rectangle getRectangle()
    {
        return new Rectangle(getX(),getY(), sprite.getWidth(), sprite.getHeight());
    }

}
