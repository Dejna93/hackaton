package com.mygdx.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.helpers.Kierunek;
import com.mygdx.helpers.GameHelper;
import com.mygdx.helpers.Obrot;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class Tank extends GameHelper{

    Sprite playerSprite;
    Texture playerTexture;
    SpriteBatch batch;
    public long lastShotTime;

    int currentState = Kierunek.GORA;
    private float deltaTime;
    private float speed = 100;

    public Tank(SpriteBatch batch) {
        playerTexture = new Texture("player.png");
        // Sprite dla gracza przyjmuje texture i 16x16 dlugosc i szerokosc
        playerSprite = new Sprite(playerTexture, 16, 16);
        this.batch = batch;
        lastShotTime = 0;
        this.deltaTime = 0;
    }

    public void draw() {
        batch.begin();
        playerSprite.draw(batch);
        batch.end();
    }

    public void move(int directionMove, float delta, Array<Tank> enemyTanks) {
        this.deltaTime = delta;
        Rectangle endPosition;
        boolean allClear = true;
        if (directionMove == Kierunek.LEWO) {
            endPosition = moveLeft();
            if (!inGameArea(endPosition, -16)){
                allClear = false;
            }
            for (Tank tank: enemyTanks){
                if (isCollision(endPosition, tank.getRectangle())){
                    allClear = false;
                    break;
                }
            }
            if (allClear){
                setPositionFromRectangle(moveLeft());
            }
        } else if (directionMove == Kierunek.PRAWO) {
            endPosition = moveRight();
            if (!inGameArea(endPosition, -16)){
                allClear = false;
            }
            for (Tank tank: enemyTanks){
                if (isCollision(endPosition, tank.getRectangle())){
                    allClear = false;
                    break;
                }
            }
            if (allClear){
                setPositionFromRectangle(moveRight());
            }
        } else if (directionMove == Kierunek.GORA) {
            endPosition = moveUp();
            if (!inGameArea(endPosition, -16)){
                allClear = false;
            }
            for (Tank tank: enemyTanks){
                if (isCollision(endPosition, tank.getRectangle())){
                    allClear = false;
                    break;
                }
            }
            if (allClear){
                setPositionFromRectangle(moveUp());
            }
        } else if (directionMove == Kierunek.DOL) {
            endPosition = moveDown();
            if (!inGameArea(endPosition, -16)){
                allClear = false;
            }
            for (Tank tank: enemyTanks){
                if (isCollision(endPosition, tank.getRectangle())){
                    allClear = false;
                    break;
                }
            }
            if (allClear){
                setPositionFromRectangle(moveDown());
            }
        }
        changeDirection(directionMove);
    }

    public void setPositionFromRectangle(Rectangle rect) {
        playerSprite.setPosition(rect.x, rect.y);
    }

    public Rectangle moveLeft() {
        return new Rectangle(playerSprite.getX() - speed * this.deltaTime, playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
    }

    public Rectangle moveRight() {
        return new Rectangle(playerSprite.getX() + speed * this.deltaTime, playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
    }

    public Rectangle moveUp() {
        return new Rectangle(playerSprite.getX(), playerSprite.getY() + speed * this.deltaTime, playerSprite.getWidth(), playerSprite.getHeight());
    }

    public Rectangle moveDown() {
        return new Rectangle(playerSprite.getX(), playerSprite.getY() - speed * this.deltaTime, playerSprite.getWidth(), playerSprite.getHeight());
    }

    private void changeDirection(int direction) {
        this.currentState = direction;
        if (direction == Kierunek.LEWO) {
            playerSprite.setRotation(Obrot.LEWO);
        }
        if (direction == Kierunek.PRAWO) {
            playerSprite.setRotation(Obrot.PRAWO);
        }
        if (direction == Kierunek.GORA) {
            playerSprite.setRotation(Obrot.GORA);
        }
        if (direction == Kierunek.DOL) {
            playerSprite.setRotation(Obrot.DOL);
        }

    }

    public int getDirection() {
        return currentState;
    }

    public float getX() {
        return playerSprite.getX();
    }

    public float getY() {
        return playerSprite.getY();
    }

    public void setPosition(int x, int y) {
        playerSprite.setPosition(x, y);
    }

    public Rectangle getRectangle() {
        return new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
    }

}
