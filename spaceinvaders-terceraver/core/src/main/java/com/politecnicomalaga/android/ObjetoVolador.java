package com.politecnicomalaga.android;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ObjetoVolador {

    protected float x;
    protected float y;
    protected float velocidad;
    protected int lado;

    protected Texture texture;

    public ObjetoVolador(float x, float y, float velocidad, Texture texture) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.texture = texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public int getLado() {
        return lado;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }
    public void descender() {
        y -= velocidad;
    }

    public void ascender() {
        y += velocidad;
    }

    public void avanzar() {
        x += velocidad;
    }

    public void retroceder() {
        x -= velocidad;
    }

    public void dibujar(SpriteBatch batch, int ancho, int alto) {
        batch.draw(texture, x, y, ancho, alto);
    }

    public boolean colisionan(int x1, int y1, int x2, int y2, int lado){

        if (Math.abs(x1 - x2) < lado && Math.abs(y1 - y2) < lado) {
            return true;
        } else return false;
    }
}
