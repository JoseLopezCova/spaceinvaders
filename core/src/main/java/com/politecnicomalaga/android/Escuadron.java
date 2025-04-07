package com.politecnicomalaga.android;

import java.util.ArrayList;
import java.util.List;

public class Escuadron {
    private List<NaveEnemiga> enemigos;
    private int iDireccion; // Indica si deben bajar y cambiar de dirección

    public Escuadron(int filas, float inicioX, float inicioY) {
        this.enemigos = new ArrayList<>();
        float espacioX = 100; // Espaciado horizontal entre enemigos
        for (int fila = 0; fila < filas; fila++) {
            float x = inicioX + espacioX;
            inicioX += espacioX;
            enemigos.add(new NaveEnemiga(x, inicioY, 6, true, 1));
        }
    }

    public List<NaveEnemiga> getEnemigos() {
        return enemigos;
    }

    public void mover() {
        for (NaveEnemiga enemigo : enemigos) {
            //enemigo.detectarcolicionpared();
        }
    }

}
