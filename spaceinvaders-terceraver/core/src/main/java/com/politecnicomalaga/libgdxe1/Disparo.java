package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Disparo extends ObjetoVolador {

    public Disparo(float x, float y, float velocidad, int lado, Texture texture) {
        super(x, y, velocidad, lado, texture);
    }

    // Método para mover el disparo, que será implementado según el tipo de disparo


    // Métodos Getter y Setter


    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    // Verifica si el disparo está fuera de la pantalla
    public boolean estaFueraDePantalla() {
        return getX() > 1080 || getY() < 0;
    }
}
