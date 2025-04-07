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

    private Batallon batallon;

    private NaveEnemiga enemigo;

    private Texture winimage;

    private Escuadron escuadron;
    private Texture endImage;
    //Aquí ponemos todas las Texture que necesitemos ahora mismo en el videojuego
    //Además todas las variables (son realmente atributos de Main) que necesitemos
    private int iPosXClicked;
    private int iPosYClicked;

    private boolean bganamos;

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
        batallon = new Batallon();
        batch = new SpriteBatch();
        player = new NaveAmiga(500, 50, 3, true, 0);
        endImage = new Texture("end.png");
        winimage = new Texture("img.png");
        escuadron = new Escuadron(8, 0, 650);
        batallon.agregarEscuadron(escuadron);
        escuadron = new Escuadron(8, 0, 600);
        batallon.agregarEscuadron(escuadron);
        escuadron = new Escuadron(8, 0, 550);
        batallon.agregarEscuadron(escuadron);

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
        if (player.getIdireccion() == 1) {
            player.avanzar();
        } else if (player.getIdireccion() == 2) {
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
                player.setIdireccion(1);
                ;
            }
            iPosYClicked = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (iPosYClicked > player.getY()) {

                if (player.getIdireccion() == 1) {
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
                if (disparo.estaFueraDePantalla()) {
                    disparosaliados.remove(disparo);
                    break;
                } else {
                    boolean salir = false;
                    for (Escuadron escuadron : batallon.getEscuadrones()) {
                        for (NaveEnemiga enemigo : escuadron.getEnemigos()) {
                            boolean borrar = enemigo.detectarcolisiondisparo(disparo.x, disparo.y);
                            if (borrar) {
                                escuadron.getEnemigos().remove(enemigo);
                                disparosaliados.remove(disparo);
                                salir = true;
                                break;
                            }
                        }
                    }
                    disparo.ascender();
                    if (salir) {
                        break;
                    }
                }
            }
            for (Disparo disparo : disparosenemigos) {
                if (disparo.estaFueraDePantalla()) {
                    disparosenemigos.remove(disparo);
                    break;
                } else {
                    player.detectarcolisiondisparo(disparo.x, disparo.y);
                    if (!player.isVivo()) {
                        bPerdemos=true;
                    }
                    disparo.descender();
                }
            }
        }
        //-------------------------------------------------

        for (Escuadron escuadron : batallon.getEscuadrones()) {
            boolean comprobar = false;
            for (NaveEnemiga enemigo : escuadron.getEnemigos()) {
                comprobar = enemigo.detectarcolicionpared(enemigo.getIdireccion());
                if (comprobar) {
                    if (enemigo.getIdireccion() != 1) {
                        enemigo.retroceder();
                    } else {
                        enemigo.avanzar();
                    }
                    if (enemigo.getIdireccion() == 1) {
                        for (int i = escuadron.getEnemigos().size() - 1; i > 0; i--) {
                            escuadron.getEnemigos().get(i).cambiarmovimiento(escuadron.getEnemigos().get(i).getIdireccion());
                        }
                    } else {
                        for (int i = 0; i < escuadron.getEnemigos().size() - 1; i++) {
                            escuadron.getEnemigos().get(i).cambiarmovimiento(escuadron.getEnemigos().get(i).getIdireccion());
                        }
                    }
                }
            }
        }



        icontador++;
        if (icontador==30){
            for (Escuadron escuadron : batallon.getEscuadrones()){
                for (NaveEnemiga enemigo : escuadron.getEnemigos()){
                    if (enemigo.getIdireccion()==1){
                        enemigo.avanzar();
                    } else {
                        enemigo.retroceder();
                    }
                    icontador=0;
                }
            }
        }


        // Generar disparos enemigos con cierta probabilidad
        if (!bPerdemos && !bganamos) {
            List<NaveEnemiga> navesEnemigas = new ArrayList<>();
            for (Escuadron escuadron : batallon.getEscuadrones()) {
                navesEnemigas.addAll(escuadron.getEnemigos());
            }
            for (Escuadron escuadron : batallon.getEscuadrones()){
                for (NaveEnemiga enemigo : escuadron.getEnemigos()){
                    boolean generar = enemigo.generardisparo(navesEnemigas);
                    if (generar){
                        if (enemigo.getIdireccion() == 1){
                            DisparoEnemigo disparoEnemigo = new DisparoEnemigo(enemigo.getX() + 15, enemigo.getY() -30, 4);
                            disparosenemigos.add(disparoEnemigo);
                        } else {
                            DisparoEnemigo disparoEnemigo = new DisparoEnemigo(enemigo.getX() +5, enemigo.getY() -30, 4);
                            disparosenemigos.add(disparoEnemigo);
                        }
                    }
                }
            }
        }
        int icont = 0;
        for (Escuadron escuadron : batallon.getEscuadrones()){
            if (escuadron.getEnemigos().size() == 0) {
                icont++;
            }
            if (icont == 3){
                bganamos = true;
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
        if (bganamos){
            batch.draw(winimage, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        }

        if (bPerdemos) {
            batch.draw(endImage, Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4);
        } else {
            for (Escuadron escuadron : batallon.getEscuadrones()){
                for (NaveEnemiga enemigo : escuadron.getEnemigos()) {
                    enemigo.dibujar(batch, 50, 40, enemigo.isVivo());
                }
            }
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
}
