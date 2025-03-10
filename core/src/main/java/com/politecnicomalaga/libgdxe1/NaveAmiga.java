package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class NaveAmiga extends Nave {
    private List<DisparoAliado> disparosAliados;

    public NaveAmiga(float posx, float posy, float velocidad) {
        super(posx, posy, velocidad,"nave_amiga");
        this.disparosAliados = new ArrayList<>();
    }

    public void disparar() {
        disparosAliados.add(new DisparoAliado(getposx(), getposy() + 20));
    }

   /* public void actualizarDisparos() {
        disparosAliados.removeIf(disparo -> disparo.getposy() > 1080);
        for (DisparoAliado disparo : disparosAliados) {
            disparo.mover();
        }
    }*/

    @Override
    public void dibujar(SpriteBatch batch) {
        super.dibujar(batch);
        for (DisparoAliado disparo : disparosAliados) {
            disparo.dibujar(batch);
        }
    }

    public List<DisparoAliado> getDisparosAliados() {
        return disparosAliados;
    }
}
