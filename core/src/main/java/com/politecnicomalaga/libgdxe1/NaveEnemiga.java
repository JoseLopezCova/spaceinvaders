package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NaveEnemiga extends Nave {
    public NaveEnemiga(float x, float y, float velocidad, int lado, boolean vivo) {
        super(x, y, velocidad, lado, new Texture("mouse.png"),  vivo);
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
}
