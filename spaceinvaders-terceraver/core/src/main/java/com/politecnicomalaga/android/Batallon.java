package com.politecnicomalaga.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;


public class Batallon {
    private List<Escuadron> escuadrones;

    public Batallon() {
        this.escuadrones = new ArrayList<>();
    }
    public void agregarEscuadron(Escuadron escuadron) {
        escuadrones.add(escuadron);
    }
    public List<Escuadron> getEscuadrones() {
        return escuadrones;
    }
}
