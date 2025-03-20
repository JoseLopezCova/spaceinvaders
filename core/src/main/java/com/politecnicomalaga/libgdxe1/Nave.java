package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Nave extends ObjetoVolador {
    private  boolean vivo;

    public Nave(float x, float y, float velocidad, int lado, Texture texture, boolean vivo) {
        super(x, y, velocidad, lado, texture);
        this.vivo = vivo;
    }

    @Override
    public void avanzar() {
        super.avanzar();
    }

    @Override
    public void retroceder() {
        super.retroceder();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        super.dibujar(batch);
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public boolean isVivo() {
        return vivo;
    }
}


