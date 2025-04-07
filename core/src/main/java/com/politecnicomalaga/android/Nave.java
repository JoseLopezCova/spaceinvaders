package com.politecnicomalaga.android;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Nave extends ObjetoVolador {
    private  boolean vivo;

    private int idireccion;

    public Nave(float x, float y, float velocidad, Texture texture, boolean vivo, int idireccion) {
        super(x, y, velocidad, texture);
        this.idireccion = idireccion;
        this.vivo = vivo;
    }

    @Override
    public void dibujar(SpriteBatch batch, int ancho, int alto) {
        super.dibujar(batch, ancho, alto);
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public boolean isVivo() {
        return vivo;
    }

    public int getIdireccion() {
        return idireccion;
    }

    public void setIdireccion(int idireccion) {
        this.idireccion = idireccion;
    }

    @Override
    public float getVelocidad() {
        return super.getVelocidad();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    @Override
    public float getX() {
        return super.getX();
    }
}


