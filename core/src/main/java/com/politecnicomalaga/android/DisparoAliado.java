package com.politecnicomalaga.android;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DisparoAliado extends Disparo {

    public DisparoAliado(float x, float y, float velocidad) {
        super(x, y,velocidad, new Texture("hbdcbh.png"));
    }

    @Override
    public void ascender() {
        super.ascender();
    }

    @Override
    public float getY() {
        return super.getY();
    }

    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public void dibujar(SpriteBatch batch, int ancho, int alto) {
        super.dibujar(batch, ancho, alto);
    }
}
