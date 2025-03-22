package com.politecnicomalaga.android;

import com.badlogic.gdx.graphics.Texture;

public class Disparo extends ObjetoVolador {

    public Disparo(float x, float y, float velocidad, Texture texture) {
        super(x, y, velocidad, texture);
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
        if (getY() >= 500 || getY() <= 0){
            return true;
        }
        return false;
    }
}
