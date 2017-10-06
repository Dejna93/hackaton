package com.mygdx.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import com.mygdx.helpers.Pomocnik;
import com.mygdx.helpers.Kierunek;
import com.mygdx.helpers.Obrot;


import java.util.Random;

/**
 * Created by d.holuj on 06-Oct-17.
 */

public class Czolg extends Pomocnik {

    Sprite czolgSprite;
    Texture czolgTexture;
    SpriteBatch batch;
    public long ostatniStrzal;

    int obecnyKierunek = Kierunek.GORA;
    private float zmianaCzasu;
    private float szybkosc = 100;
    private float czasOstatniegoLosowania;

    private boolean czyPrzeciwnik = false;

    public Czolg(SpriteBatch batch) {
        czolgTexture = new Texture("player.jpg");
        // Sprite dla gracza przyjmuje texture i 16x16 dlugosc i szerokosc
        czolgSprite = new Sprite(czolgTexture, 16, 16);
        this.batch = batch;
        ostatniStrzal = 0;
        this.zmianaCzasu = 0;
        czasOstatniegoLosowania = 0;
    }

    public void rysujCzolg() {
        batch.begin();
        czolgSprite.draw(batch);
        batch.end();
    }

    public void porusz(int kierunekRuchu, float delta, Array<Czolg> przeciwnicy) {
        this.zmianaCzasu = delta;
        Rectangle koncowaPozycja;
        boolean czyCzyMoznaRuszyc = true;
        if (kierunekRuchu == Kierunek.LEWO) {
            koncowaPozycja = poruszLewo();
            if (!czyJestWObszarzeGry(koncowaPozycja, -16)) {
                czyCzyMoznaRuszyc = false;
            }
            for (Czolg tank : przeciwnicy) {
                if (czyKolizja(koncowaPozycja, tank.getRectangle())) {
                    czyCzyMoznaRuszyc = false;
                    break;
                }
            }
            if (czyCzyMoznaRuszyc) {
                ustawPozycjeZKwadratu(poruszLewo());
            }
        } else if (kierunekRuchu == Kierunek.PRAWO) {
            koncowaPozycja = poruszPrawo();
            if (!czyJestWObszarzeGry(koncowaPozycja, -16)) {
                czyCzyMoznaRuszyc = false;
            }
            for (Czolg tank : przeciwnicy) {
                if (czyKolizja(koncowaPozycja, tank.getRectangle())) {
                    czyCzyMoznaRuszyc = false;
                    break;
                }
            }
            if (czyCzyMoznaRuszyc) {
                ustawPozycjeZKwadratu(poruszPrawo());
            }
        } else if (kierunekRuchu == Kierunek.GORA) {
            koncowaPozycja = poruszGora();
            if (!czyJestWObszarzeGry(koncowaPozycja, -16)) {
                czyCzyMoznaRuszyc = false;
            }
            for (Czolg tank : przeciwnicy) {
                if (czyKolizja(koncowaPozycja, tank.getRectangle())) {
                    czyCzyMoznaRuszyc = false;
                    break;
                }
            }
            if (czyCzyMoznaRuszyc) {
                ustawPozycjeZKwadratu(poruszGora());
            }
        } else if (kierunekRuchu == Kierunek.DOL) {
            koncowaPozycja = poruszDol();
            if (!czyJestWObszarzeGry(koncowaPozycja, -16)) {
                czyCzyMoznaRuszyc = false;
            }
            for (Czolg tank : przeciwnicy) {
                if (czyKolizja(koncowaPozycja, tank.getRectangle())) {
                    czyCzyMoznaRuszyc = false;
                    break;
                }
            }
            if (czyCzyMoznaRuszyc) {
                ustawPozycjeZKwadratu(poruszDol());
            }
        }
        zmienKierunek(kierunekRuchu);
    }

    public void wylosujRuchCzolgu(float deltaTime, Czolg czolg) {
        //int direction = Direction.LEFT + (int)(Math.random() * Direction.DOWN);
        Random rand = new Random();
        float procentDecyzji = rand.nextFloat() * 100;

        if (procentDecyzji <= 25) {
            Array<Czolg> czolgi = new Array<Czolg>();
            czolgi.add(czolg);
            int kierunek = (int) procentDecyzji % 4;
            porusz(kierunek, deltaTime, czolgi);
            czasOstatniegoLosowania = TimeUtils.millis();
        }

    }

    public void ustawPozycjeZKwadratu(Rectangle rect) {
        czolgSprite.setPosition(rect.x, rect.y);
    }

    public Rectangle poruszLewo() {
        return new Rectangle(czolgSprite.getX() - szybkosc * this.zmianaCzasu, czolgSprite.getY(), czolgSprite.getWidth(), czolgSprite.getHeight());
    }

    public Rectangle poruszPrawo() {
        return new Rectangle(czolgSprite.getX() + szybkosc * this.zmianaCzasu, czolgSprite.getY(), czolgSprite.getWidth(), czolgSprite.getHeight());
    }

    public Rectangle poruszGora() {
        return new Rectangle(czolgSprite.getX(), czolgSprite.getY() + szybkosc * this.zmianaCzasu, czolgSprite.getWidth(), czolgSprite.getHeight());
    }

    public Rectangle poruszDol() {
        return new Rectangle(czolgSprite.getX(), czolgSprite.getY() - szybkosc * this.zmianaCzasu, czolgSprite.getWidth(), czolgSprite.getHeight());
    }

    private void zmienKierunek(int kierunek) {
        this.obecnyKierunek = kierunek;
        if (kierunek == Kierunek.LEWO) {
            czolgSprite.setRotation(Obrot.LEWO);
        }
        if (kierunek == Kierunek.PRAWO) {
            czolgSprite.setRotation(Obrot.PRAWO);
        }
        if (kierunek == Kierunek.GORA) {
            czolgSprite.setRotation(Obrot.GORA);
        }
        if (kierunek == Kierunek.DOL) {
            czolgSprite.setRotation(Obrot.DOL);
        }

    }

    public int getKierunek() {
        return obecnyKierunek;
    }

    public float getX() {
        return czolgSprite.getX();
    }

    public float getY() {
        return czolgSprite.getY();
    }

    public void setPosition(int x, int y) {
        czolgSprite.setPosition(x, y);
    }

    public Rectangle getRectangle() {
        return new Rectangle(czolgSprite.getX(), czolgSprite.getY(), czolgSprite.getWidth(), czolgSprite.getHeight());
    }

}
