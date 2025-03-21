package com.politecnicomalaga.libgdxe1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
    private NaveAmiga player;

    private NaveEnemiga enemigo;
    private Texture endImage;
    //Aquí ponemos todas las Texture que necesitemos ahora mismo en el videojuego
    //Además todas las variables (son realmente atributos de Main) que necesitemos
    private int iPosXClicked;
    private int iPosYClicked;

    private float fVelPlayer;

    private int iDireccion;  //0 para arriba, 1 para abajo, 2 para izquierda, 3 para derecha
    private boolean bGanamos;
    private int Idireccion;

    //Lista de disparos
    private List<DisparoAliado> disparosaliados;
    private List<DisparoEnemigo> disparosenemigos;
    private int icontador;

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
        player = new NaveAmiga(500, 50, 6, 3, true);
        endImage = new Texture("end.png");
        iDireccion = 0;
        enemigo = new NaveEnemiga(100, 400, 6, 3, true);
        Idireccion = 0;
        icontador =0;

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



        if (Idireccion == 1){
            player.avanzar();
        }else if(Idireccion==2) {
            player.retroceder();
        }


        //------------------------------
        //Control de entrada
        //------------------------------
        if (Gdx.input.justTouched()) {
            // Si entramos aquí es que se ha tocado/clicado la pantalla entre el anterior "render" y este
            iPosXClicked = Gdx.input.getX();
            iPosYClicked = Gdx.input.getY();

            if (iPosXClicked < player.getX()) {
                Idireccion =2;
            } else if (iPosXClicked > player.getX()) {
                Idireccion = 1 ;
            }
            iPosYClicked = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (iPosYClicked > player.getY()) {
                DisparoAliado disparoAliado = new DisparoAliado(player.getX(), player.getY() + 20, 4, 3); // vamos para arriba
                disparosaliados.add(disparoAliado);
            }
            disparosaliados.removeIf(disparo -> disparo.getY() > 1080);
            disparosaliados.removeIf(disparo -> disparo.getY() < 0);
        }

        //------------------------------
        //Simulación del mundo
        //------------------------------
        if (!bGanamos) {

            for (Disparo disparo : disparosaliados) {
                disparo.ascender();
            }
            for (Disparo disparo : disparosenemigos) {
                disparo.descender();
            }

            // Evitar que el jugador se salga de la pantalla
            if (player.getX() > Gdx.graphics.getWidth() - player.lado) player.setX(Gdx.graphics.getWidth() - player.lado);
            if (player.getY() > Gdx.graphics.getHeight() - player.lado) player.setY(Gdx.graphics.getHeight() - player.lado);
            if (player.getX() < 0) player.setX(0);
            if (player.getY() < 0) player.setX(0);
        }

        // Cambiar posición de la imagen objetivo con cierta probabilidad
        icontador++;
        if (icontador==180){
            enemigo.avanzar();
            icontador=0;
        }


        // Generar disparos enemigos con cierta probabilidad
        if (!bGanamos && Math.random() > 0.99) {
            DisparoEnemigo disparoEnemigo = new DisparoEnemigo(enemigo.getX(), enemigo.getY() -20, 4, 2);
            disparosenemigos.add(disparoEnemigo);
        }

        //------------------------------
        //Control de cambios
        //------------------------------
        for (Disparo disparo : disparosaliados) {
            if (colisionan(disparo.getX(), disparo.getY(), player.getX(), player.getY(), player.lado)) {
                // Ganamos
                bGanamos = true;
            } else {
                disparo.ascender();

            }
        }

        for (Disparo disparo : disparosenemigos) {
            if (colisionan(disparo.getX(), disparo.getY(), enemigo.getX(), enemigo.getY(), enemigo.lado)) {
                // Ganamos
                bGanamos = true;
            } else {
                disparo.descender();
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
            enemigo.dibujar(batch);
            player.dibujar(batch);
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
        endImage.dispose();
        fondo.dispose();
    }

    public boolean colisionan(float fPosX1, float fPosY1, float fPosX2, float fPosY2, float fLado) {
        return (Math.abs(fPosX1 - fPosX2) < fLado && Math.abs(fPosY1 - fPosY2) < fLado);
    }
}
