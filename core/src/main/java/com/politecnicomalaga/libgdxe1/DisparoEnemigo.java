package com.politecnicomalaga.libgdxe1;

import com.politecnicomalaga.libgdxe1.Disparo;

public class DisparoEnemigo extends Disparo {
    public DisparoEnemigo(float posx, float posy) {
        super(posx, posy, -5f, "disparoenemigo.png"); // Velocidad negativa (baja)
    }

    @Override
    public void mover() {
        posy += velocidad; // Mueve el disparo hacia abajo
    }
}
