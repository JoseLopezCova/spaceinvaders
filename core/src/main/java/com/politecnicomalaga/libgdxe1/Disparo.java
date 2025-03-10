package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Disparo {
    protected float posx;
    protected float posy;
    protected float velocidad;
    protected Texture texture;

    // Constructor genérico para el disparo
    public Disparo(float posx, float posy, float velocidad, String texturaPath) {
        this.posx = posx;
        this.posy = posy;
        this.velocidad = velocidad;
        this.texture = new Texture(texturaPath); // Cargar la textura
    }

    // Método para mover el disparo, que será implementado según el tipo de disparo
    public void mover() {
        posy += velocidad; // Por defecto, mueve hacia arriba, pero lo modificaremos en las subclases
    }

    // Método para dibujar el disparo en la pantalla
    public void dibujar(SpriteBatch batch) {
        batch.draw(texture, posx, posy, 40, 40); // Dibujar el disparo con un tamaño fijo
    }

    // Métodos Getter y Setter
    public float getPosx() {
        return posx;
    }

    public void setPosx(float posx) {
        this.posx = posx;
    }

    public float getPosy() {
        return posy;
    }

    public void setPosy(float posy) {
        this.posy = posy;
    }

    // Verifica si el disparo está fuera de la pantalla
    public boolean estaFueraDePantalla() {
        return posy > 1080 || posy < 0;
    }
}
