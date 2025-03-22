package com.politecnicomalaga.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class NaveAmiga extends Nave {

    public NaveAmiga(float x, float y, float velocidad, boolean vivo, int idireccion) {
        super(x, y, velocidad, new Texture("player.png"), vivo, idireccion);
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
    public int getIdireccion() {
        return super.getIdireccion();
    }

    @Override
    public void setIdireccion(int idireccion) {
        super.setIdireccion(idireccion);
    }

    public void  detectarcolicionpared(){
        if ((x >= Gdx.graphics.getWidth()-50)){
            setX(Gdx.graphics.getWidth()-50);

        } else if (x <= 0) {
            setX(0);

        }
    }

    public void detectarcolisiondisparo(float fposxdisparo, float fposydisparo){
        if ((x + 50 >= fposxdisparo && x <= fposxdisparo + 20) &&
            (y + 40 >= fposydisparo && y <= fposydisparo + 20)) {
            setVivo(false);
        }
    }

}
