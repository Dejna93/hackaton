package com.mygdx.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.helpers.Direction;
import com.mygdx.helpers.Rotation;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class Player {

    Sprite playerSprite;
    Texture playerTexture;
    SpriteBatch batch;

    int currentState = Direction.UP;

    public Player(SpriteBatch batch) {
        playerTexture = new Texture("player.jpg");
        // Sprite dla gracza przyjmuje texture i 16x16 dlugosc i szerokosc
        playerSprite = new Sprite(playerTexture, 16, 16);
        this.batch = batch;
    }

    public void draw() {
        batch.begin();
        playerSprite.draw(batch);
        batch.end();
    }

    public void move(int directionMove) {
        if (directionMove == Direction.LEFT) {
            playerSprite.setX(playerSprite.getX() - 16);
        }
        if (directionMove == Direction.RIGHT) {
            playerSprite.setX(playerSprite.getX() + 16);
        }
        if (directionMove == Direction.UP) {
            playerSprite.setY(playerSprite.getY() + 16);
        }
        if (directionMove == Direction.DOWN) {
            playerSprite.setY(playerSprite.getY() - 16);
        }
        changeDirection(directionMove);
    }

    private void changeDirection(int direction) {
        this.currentState = direction;
        if (direction == Direction.LEFT) {
            playerSprite.setRotation(Rotation.LEFT);
        }
        if (direction == Direction.RIGHT) {
            playerSprite.setRotation(Rotation.RIGHT);
        }
        if (direction == Direction.UP) {
            playerSprite.setRotation(Rotation.UP);
        }
        if (direction == Direction.DOWN) {
            playerSprite.setRotation(Rotation.DOWN);
        }

    }

}
