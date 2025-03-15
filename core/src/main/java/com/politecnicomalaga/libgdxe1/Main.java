package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*  AAR
    Esta es la clase de partida de todos los videojuegos Libgdx. Desde esta clase, que es llamada desde el lanzador
    de escritorio, de android, de html, etc... se llamarán a los demás objetos de la capa de modelo, vista o controlador
 */

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch; //Es la clase que representa una pantalla donde se pueden pintar imágenes
    private Texture image;  //Esta es una instancia/objeto imagen
    private Texture player;
    private Texture endImage;
    //Aquí ponemos todas las Texture que necesitemos ahora mismo en el videojuego
    //Además todas las variables (son realmente atributos de Main) que necesitemos
    private int iPosXClicked;
    private int iPosYClicked;

    private float iPosXImagen;
    private float iPosYImagen;

    private float fPosXPlayer;
    private float fPosYPlayer;
    private float fVelPlayer;

    private int iDireccion;  //0 para arriba, 1 para abajo, 2 para izquierda, 3 para derecha
    private boolean bGanamos;

    //Lista de disparos
    private List<DisparoAliado> disparosaliados;
    private List<DisparoEnemigo> disparosenemigos;

    // Instancia de la clase Fondo
    private Fondo fondo;

    @Override
    public void create() {
        disparosaliados = new ArrayList<>();
        disparosenemigos = new ArrayList<>();
        /* AAR
            Método de inicialización de los atributos. Hace la función de "constructor", pero sin serlo. El constructor
            ApplicationAdapter es el encargado de llamar a este método. Lo veremos en profundidad cuando estudiemos herencia
         */
        batch = new SpriteBatch();
        image = new Texture("mouse.png");
        player = new Texture("download.png");
        endImage = new Texture("end.png");
        iDireccion = 0;
        iPosXImagen = 200;
        iPosYImagen = 200;

        fPosXPlayer = 500;
        fPosYPlayer = 200;
        fVelPlayer = 6f;
        bGanamos = false;

        // Inicializar el fondo con las dimensiones de la pantalla
        fondo = new Fondo("fondo.jpeg", 5f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        /*  AAR
            Método corazón de todos los videojuegos Libgdx
            Aquí solemos tener:
             - control de entrada
             - simulación del mundo
             - control de cambios
             - dibujar el mundo

            Sabemos que se va a ejecutar una y otra vez, una y otra vez, hasta que alguién cierre la app
         */

        //------------------------------
        //Control de entrada
        //------------------------------
        if (Gdx.input.justTouched()) {
            // Si entramos aquí es que se ha tocado/clicado la pantalla entre el anterior "render" y este
            iPosXClicked = Gdx.input.getX();
            iPosYClicked = Gdx.input.getY();

            if (iPosXClicked < fPosXPlayer) {
                iDireccion = 2;
            } else if (iPosXClicked > fPosXPlayer) {
                iDireccion = 3;
            }
            iPosYClicked = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (iPosYClicked > fPosYPlayer) {
                DisparoAliado disparoAliado = new DisparoAliado(fPosXPlayer, fPosYPlayer + 20); // vamos para arriba
                disparosaliados.add(disparoAliado);
            }
            disparosaliados.removeIf(disparo -> disparo.getPosy() > 1080);
            disparosaliados.removeIf(disparo -> disparo.getPosy() < 0);

            if (bGanamos) {
                iDireccion = 0;
                iPosXImagen = 200;
                iPosYImagen = 200;

                fPosXPlayer = 300;
                fPosYPlayer = 300;
                fVelPlayer = 0.5f;
                bGanamos = false;
            }
        }

        //------------------------------
        //Simulación del mundo
        //------------------------------
        if (!bGanamos) {
            switch (iDireccion) {
                case 2:
                    fPosXPlayer -= fVelPlayer;
                    break;
                case 3:
                    fPosXPlayer += fVelPlayer;
                    break;
            }

            for (Disparo disparo : disparosaliados) {
                disparo.mover();
            }
            for (Disparo disparo : disparosenemigos) {
                disparo.mover();
            }

            // Evitar que el jugador se salga de la pantalla
            if (fPosXPlayer > Gdx.graphics.getWidth() - player.getWidth()) fPosXPlayer = Gdx.graphics.getWidth() - player.getWidth();
            if (fPosYPlayer > Gdx.graphics.getHeight() - player.getHeight()) fPosYPlayer = Gdx.graphics.getHeight() - player.getHeight();
            if (fPosXPlayer < 0) fPosXPlayer = 0;
            if (fPosYPlayer < 0) fPosYPlayer = 0;
        }

        // Cambiar posición de la imagen objetivo con cierta probabilidad
        if (!bGanamos && Math.random() > 0.99) {
            Random dado = new Random();
            iPosXImagen = dado.nextInt(Gdx.graphics.getWidth());
            iPosYImagen = dado.nextInt(Gdx.graphics.getHeight());
        }

        // Generar disparos enemigos con cierta probabilidad
        if (!bGanamos && Math.random() > 0.99) {
            DisparoEnemigo disparoEnemigo = new DisparoEnemigo(iPosXImagen, iPosYImagen - 20);
            disparosenemigos.add(disparoEnemigo);
        }

        //------------------------------
        //Control de cambios
        //------------------------------
        for (Disparo disparo : disparosaliados) {
            if (colisionan(disparo.getPosx(), disparo.getPosy(), iPosXImagen, iPosYImagen, image.getWidth())) {
                // Ganamos
                bGanamos = true;
            } else {
                disparo.mover();
            }
        }

        for (Disparo disparo : disparosenemigos) {
            if (colisionan(disparo.getPosx(), disparo.getPosy(), fPosXPlayer, fPosYPlayer, image.getWidth())) {
                // Ganamos
                bGanamos = true;
            } else {
                disparo.mover();
            }
        }

        //------------------------------
        // Dibujar
        //------------------------------

        // Limpiar la pantalla
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();

        // Dibujar el fondo
        fondo.dibujar(batch);

        // Dibujar otros elementos del juego
        if (bGanamos) {
            batch.draw(endImage, 80, 0);
        } else {
            batch.draw(image, iPosXImagen, iPosYImagen);
            batch.draw(player, fPosXPlayer, fPosYPlayer, 100, 100);
            for (Disparo disparo : disparosaliados) {
                disparo.dibujar(batch);
            }
            for (Disparo disparo : disparosenemigos) {
                disparo.dibujar(batch);
            }
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        player.dispose();
        endImage.dispose();
        fondo.dispose();
    }

    public boolean colisionan(float fPosX1, float fPosY1, float fPosX2, float fPosY2, float fLado) {
        return (Math.abs(fPosX1 - fPosX2) < fLado && Math.abs(fPosY1 - fPosY2) < fLado);
    }
}
