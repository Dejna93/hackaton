package com.mygdx.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;


public class Eksplozja {

    private SpriteBatch batch;
    private long czas_utworzenia;
    private Sprite sprite;

    public Eksplozja(SpriteBatch batch, float x, float y){
        this.batch = batch;
        this.czas_utworzenia = TimeUtils.millis();
        sprite = new Sprite(new Texture("eksplozja.png"), 30 ,30);
        sprite.setPosition(x, y);
    }

    public void rysuj()
    {
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    public boolean czy_trwa(){
        return (czas_utworzenia + 2000 > TimeUtils.millis());
    }
}
