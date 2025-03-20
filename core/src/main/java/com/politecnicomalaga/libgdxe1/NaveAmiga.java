package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class NaveAmiga extends Nave {

    public NaveAmiga(float x, float y, float velocidad, int lado, boolean vivo) {
        super(x, y, velocidad, lado, new Texture("download.jpg"), vivo);
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
}
