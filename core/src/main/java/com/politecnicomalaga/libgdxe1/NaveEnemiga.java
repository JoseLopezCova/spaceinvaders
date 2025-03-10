package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NaveEnemiga extends Nave {
    private List<DisparoEnemigo> disparosEnemigos;
    private Random random;

    public NaveEnemiga(float posx, float posy, float velocidad) {
        super(posx, posy, velocidad, "nave_enemiga");
        this.disparosEnemigos = new ArrayList<>();
        this.random = new Random();
    }

    public void disparar() {
        if (random.nextFloat() > 0.99) { // Probabilidad baja de disparo
            disparosEnemigos.add(new DisparoEnemigo(getposx(), getposy() - 20));
        }
    }

    /*public void actualizarDisparos() {
        disparosEnemigos.removeIf(disparo -> disparo.getposy() < 0);
        for (DisparoEnemigo disparo : disparosEnemigos) {
            disparo.mover();
        }
    } */

    @Override
    public void dibujar(SpriteBatch batch) {
        super.dibujar(batch);
        for (DisparoEnemigo disparo : disparosEnemigos) {
            disparo.dibujar(batch);
        }
    }

    public List<DisparoEnemigo> getDisparosEnemigos() {
        return disparosEnemigos;
    }
}
