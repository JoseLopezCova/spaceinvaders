package com.politecnicomalaga.libgdxe1;

import com.politecnicomalaga.libgdxe1.Disparo;

public class DisparoAliado extends Disparo {
    public DisparoAliado(float posx, float posy) {
        super(posx, posy, 7f, "disparoaliado.png"); // Velocidad positiva (sube)
    }

    @Override
    public void mover() {
        posy += velocidad; // Mueve el disparo hacia arriba
    }
}
