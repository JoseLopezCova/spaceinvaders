package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.politecnicomalaga.libgdxe1.Disparo;

public class DisparoEnemigo extends Disparo {
    public DisparoEnemigo(float x, float y, float velocidad, int lado) {
        super(x, y, velocidad, lado, new Texture("disparoenemigo.png"));
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
    public void dibujar(SpriteBatch batch) {
        super.dibujar(batch);
    }
}
