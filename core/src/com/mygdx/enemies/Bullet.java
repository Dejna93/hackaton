package com.mygdx.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.helpers.Direction;
import com.mygdx.helpers.GameHelper;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class Bullet extends GameHelper {

    private SpriteBatch batch;
    private int direction;
    private Sprite bulletSprite;

    public Bullet(SpriteBatch batch, int direction, float x, float y){
        this.batch = batch;
        this.direction = direction;
        bulletSprite = new Sprite(new Texture("bullet.jpg"), 3 ,3);
        bulletSprite.setPosition(x, y);
    }

    public void move(){
        if (direction == Direction.LEFT) {
            bulletSprite.setPosition(bulletSprite.getX() - 5, bulletSprite.getY());
        }
        if (direction == Direction.RIGHT) {
            bulletSprite.setPosition(bulletSprite.getX() + 5, bulletSprite.getY());
        }
        if (direction == Direction.UP) {
            bulletSprite.setPosition(bulletSprite.getX() , bulletSprite.getY() + 5);
        }
        if (direction == Direction.DOWN) {
            bulletSprite.setPosition(bulletSprite.getX() , bulletSprite.getY() - 5);
        }
    }
    public void draw()
    {
        batch.begin();
        bulletSprite.draw(batch);
        batch.end();
    }

    public float getX() {
        return bulletSprite.getX();
    }

    public float getY() {
        return bulletSprite.getY();
    }

    public Rectangle getRectangle()
    {
        return new Rectangle(getX(),getY(),bulletSprite.getWidth(), bulletSprite.getHeight());
    }

}
