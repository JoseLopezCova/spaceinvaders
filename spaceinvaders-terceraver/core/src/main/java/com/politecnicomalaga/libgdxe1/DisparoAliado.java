package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.politecnicomalaga.libgdxe1.Disparo;

public class DisparoAliado extends Disparo {

    public DisparoAliado(float x, float y, float velocidad, int lado) {
        super(x, y,velocidad, lado, new Texture("disparoaliado.png"));
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
    public void dibujar(SpriteBatch batch) {
        super.dibujar(batch);
    }
}
