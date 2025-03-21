package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class Fondo {

    private Texture background;
    private float offsetX;
    private float velocidadX;
    private int screenWidth;
    private int screenHeight;

    public Fondo(String fondo, float velocidad, int screenWidth, int screenHeight) {
        this.background = new Texture(fondo);
        this.velocidadX = velocidad;
        this.offsetX = 0;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void moverIzquierda() {
        offsetX -= velocidadX;

        // Resetea si se pasa del límite negativo
        if (offsetX <= -background.getWidth()) {
            offsetX = 0;
        }
    }

    public void moverDerecha() {
        offsetX += velocidadX;

        // Resetea si se pasa del límite positivo
        if (offsetX >= background.getWidth()) {
            offsetX = 0;
        }
    }


    public void dibujar(SpriteBatch batch) {
        // Dibujar el fondo escalado para que ocupe toda la pantalla
        batch.draw(background, offsetX, 0, screenWidth, screenHeight);
        batch.draw(background, offsetX + background.getWidth(), 0, screenWidth, screenHeight);
    }

    public void dispose() {
        background.dispose();
    }
}
