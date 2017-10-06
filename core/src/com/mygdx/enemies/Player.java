package com.mygdx.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class Player {

    Sprite playerSprite;
    Texture playerTexture;
    SpriteBatch batch;

    public Player(SpriteBatch batch) {
        playerTexture = new Texture("player.jpg");
        // Sprite dla gracza przyjmuje texture i 16x16 dlugosc i szerokosc
        playerSprite = new Sprite(playerTexture, 16, 16);
        this.batch = batch;
    }

    public void draw()
    {
        batch.begin();
        playerSprite.draw(batch);
        batch.end();
    }
}
