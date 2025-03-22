package com.politecnicomalaga.android;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DisparoEnemigo extends Disparo {
    public DisparoEnemigo(float x, float y, float velocidad) {
        super(x, y, velocidad, new Texture("nbkk.png"));
    }

    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }

    @Override
    public void descender() {
        super.descender();
    }

    @Override
    public void dibujar(SpriteBatch batch, int ancho, int alto) {
        super.dibujar(batch, ancho, alto);
    }
}
