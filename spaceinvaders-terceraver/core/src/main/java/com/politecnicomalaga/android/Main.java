package com.politecnicomalaga.android;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

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

    private int iDireccion;//0 para arriba, 1 para abajo, 2 para izquierda, 3 para derecha

    private int iDireccionenemigo;
    private boolean bPerdemos;
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
        player = new NaveAmiga(500, 50, 6, true, 0);
        endImage = new Texture("end.png");
        enemigo = new NaveEnemiga(500, 400, 6, true, 1);
        icontador =0;

        bPerdemos = false;



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

        player.detectarcolicionpared();
        if (player.getIdireccion() == 1){
            player.avanzar();
        }else if(player.getIdireccion() == 2) {
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
                player.setIdireccion(2);
            } else if (iPosXClicked > player.getX()) {
                player.setIdireccion(1); ;
            }
            iPosYClicked = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (iPosYClicked > player.getY()) {
                if (player.getIdireccion() == 1){
                    DisparoAliado disparoAliado = new DisparoAliado(player.getX() + 25, player.getY() + 30, 4);
                    disparosaliados.add(disparoAliado);
                } else {
                    DisparoAliado disparoAliado = new DisparoAliado(player.getX() - 5, player.getY() + 30, 4);
                    disparosaliados.add(disparoAliado);
                }
                 // vamos para arriba

            }
        }

        //------------------------------
        //Simulación del mundo
        //------------------------------
        if (!bPerdemos) {
            for (Disparo disparo : disparosaliados) {
                if (disparo.estaFueraDePantalla()){
                    disparosaliados.remove(disparo);
                    break;
                } else {
                    enemigo.detectarcolisiondisparo(disparo.x, disparo.y);
                    disparo.ascender();
                }
            }

            for (Disparo disparo : disparosenemigos) {
                if (disparo.estaFueraDePantalla()){
                    disparosenemigos.remove(disparo);
                    break;
                } else {
                    player.detectarcolisiondisparo(disparo.x, disparo.y);
                    if (!player.isVivo()){
                        bPerdemos = true;
                    }
                    disparo.descender();
                }

            }

        }

        // Cambiar posición de la imagen objetivo con cierta probabilidad

        enemigo.detectarcolicionpared(enemigo.getIdireccion());

        if (icontador==0){
            if (enemigo.getIdireccion()==1){
                enemigo.avanzar();
            } else {
                enemigo.retroceder();
            }
            icontador=0;
        }


        // Generar disparos enemigos con cierta probabilidad
        if (!bPerdemos && Math.random() > 0.99) {
            if (enemigo.getIdireccion() == 1){
                DisparoEnemigo disparoEnemigo = new DisparoEnemigo(enemigo.getX() + 15, enemigo.getY() -30, 4);
                disparosenemigos.add(disparoEnemigo);
            } else {
                DisparoEnemigo disparoEnemigo = new DisparoEnemigo(enemigo.getX() +5, enemigo.getY() -30, 4);
                disparosenemigos.add(disparoEnemigo);
            }
        }

        //------------------------------
        //Control de cambios
        //------------------------------



        //------------------------------
        // Dibujar
        //------------------------------

        // Limpiar la pantalla
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        //Dibujar
        batch.begin();


        fondo.dibujar(batch);


        if (bPerdemos) {
            batch.draw(endImage, 80, 0);
        } else {
            enemigo.dibujar(batch, 50, 40, enemigo.isVivo());
            player.dibujar(batch, 50, 40, player.isVivo());
            for (Disparo disparo : disparosaliados) {
                disparo.dibujar(batch, 20, 30);
            }
            for (Disparo disparo : disparosenemigos) {
                disparo.dibujar(batch, 20, 40);
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
