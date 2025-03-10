package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Nave {
    protected float posx;
    protected float posy;
    protected float velocidad;
    protected Texture texture;




    public Nave (float posx, float posy, float velocidad, String texturaPath) {
        this.posx = posx;
        this.posy = posy;
        this.velocidad = velocidad;
        this.texture = new Texture(texturaPath); // Cargar la textura
    }

    public void mover(int direccion) {
        switch (direccion) {
            case 2:
                posx -= velocidad;
                break;
            case 3:
                posx += velocidad;
                break;
        }
    }
    public void dibujar(SpriteBatch batch) {
        batch.draw(texture, posx, posy, 80, 80); // Dibujar nave con un tamaño fijo
    }

        public float getposx() { return posx; }
        public float getposy() { return posy; }
        public void setposx(float posx) { this.posx = posx; }
        public void setposy(float posy) { this.posy = posy; }
}


