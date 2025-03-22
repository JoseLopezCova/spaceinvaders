package com.politecnicomalaga.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NaveEnemiga extends Nave {
    public NaveEnemiga(float x, float y, float velocidad, boolean vivo, int idireccion) {
        super(x, y, velocidad, new Texture("enemigo.png"),  vivo, idireccion);
    }

    @Override
    public void avanzar() {
        super.avanzar();
    }

    @Override
    public void retroceder() {
        super.retroceder();
    }


    public void dibujar(SpriteBatch batch, int ancho, int alto, boolean vivo) {
        if (vivo){
            super.dibujar(batch, ancho, alto);
        }
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
    public void setVivo(boolean vivo) {
        super.setVivo(vivo);
    }

    @Override
    public boolean isVivo() {
        return super.isVivo();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    @Override
    public int getIdireccion() {
        return super.getIdireccion();
    }

    @Override
    public void setIdireccion(int idireccion) {
        super.setIdireccion(idireccion);
    }
    public void  detectarcolicionpared(int idireccion){
        if ((x >= Gdx.graphics.getWidth()-40) || (x <= 0)){
            y -= 20;
            if (idireccion == 1){
                setIdireccion(2);
                x = getX()-20;
                setVelocidad(this.velocidad);
            } else {
                setIdireccion(1);
                x = getX()+20;
                setVelocidad(this.velocidad);
            }
        }
    }

    public void detectarcolisiondisparo(float fposxdisparo, float fposydisparo){
        if ((x + 50 >= fposxdisparo && x <= fposxdisparo + 20) &&
            (y + 40 >= fposydisparo && y <= fposydisparo + 20)) {
            setVivo(false);
        }
    }
}
