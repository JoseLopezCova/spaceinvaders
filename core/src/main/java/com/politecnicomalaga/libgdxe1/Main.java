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

    @Override
    public void create() {
        disparosaliados =new ArrayList<>();
        disparosenemigos =new ArrayList<>();
        /* AAR
            Método de inicialización de los atributos. Hace la función de "constructor", pero sin serlo. El constructor
            ApplicationAdapter es el encargado de llamar a este método. Lo veremos en profundidad cuando estudiemos herencia
         */
        batch = new SpriteBatch();
        image = new Texture("mouse.png");
        player = new Texture("download.png");
        endImage = new Texture("end.png");
        iDireccion =0;
        iPosXImagen=200;
        iPosYImagen=200;

        fPosXPlayer=500;
        fPosYPlayer=200;
        fVelPlayer=6f;
        bGanamos = false;
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
            //Si entramos aquí es que se ha tocado/clicado la pantalla entre el anterior "render" y este
            iPosXClicked = Gdx.input.getX();
            iPosYClicked = Gdx.input.getY();


            if (iPosXClicked<fPosXPlayer) {
                iDireccion=2;
            } else if (iPosXClicked>fPosXPlayer) {
                iDireccion=3;
            }
            iPosYClicked = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (iPosYClicked > fPosYPlayer) {
                DisparoAliado disparoAliado = new DisparoAliado(fPosXPlayer, fPosYPlayer + 20);//vamos para arriba
                disparosaliados.add(disparoAliado);
            }
            disparosaliados.removeIf(disparo -> disparo.getPosy() > 1080);
            disparosaliados.removeIf(disparo -> disparo.getPosy() < 0);

            if (bGanamos) {
                iDireccion =0;
                iPosXImagen=200;
                iPosYImagen=200;

                fPosXPlayer=300;
                fPosYPlayer=300;
                fVelPlayer=0.5f;
                bGanamos = false;
            }
        }

        //------------------------------
        //Simulación del mundo
        //------------------------------
        //Dependiendo de la dirección, tenemos que actualizar las posiciones del jugador.
        if (!bGanamos) {
            switch (iDireccion) {
                case 2:
                    fPosXPlayer -= fVelPlayer;
                    break;
                case 3:
                    fPosXPlayer += fVelPlayer;
                    break;

            }
            for (Disparo disparo: disparosaliados) {
                disparo.mover();
            }
            for (Disparo disparo: disparosenemigos) {
                disparo.mover();
            }
            //Evitamos que se salga
            if (fPosXPlayer>Gdx.graphics.getWidth()-image.getWidth()) fPosXPlayer = Gdx.graphics.getWidth()-image.getWidth();
            if (fPosYPlayer>Gdx.graphics.getHeight()-image.getWidth()) fPosYPlayer = Gdx.graphics.getHeight()-image.getWidth();
            if (fPosXPlayer<0) fPosXPlayer = 0;
            if (fPosYPlayer<0) fPosYPlayer = 0;
        }

        //También simulamos el "cambio" o "salto" de la imagen a perseguir
        if (!bGanamos && Math.random()>0.99) {//0,1% de posibilidades de "saltar" en cada frame
            Random dado = new Random();
            iPosXImagen = dado.nextInt(Gdx.graphics.getWidth());
            iPosYImagen = dado.nextInt(Gdx.graphics.getHeight());
        }

        if (!bGanamos && Math.random()>0.99) {//0,1% de posibilidades de "saltar" en cada frame
            DisparoEnemigo disparoEnemigo = new DisparoEnemigo(iPosXImagen, iPosYImagen -20);
            disparosenemigos.add(disparoEnemigo);
        }
        //------------------------------
        //Control de cambios
        //------------------------------

        //Si han colisionado, hemos ganado
        for (Disparo disparo: disparosaliados) {
            if (colisionan(disparo.getPosx(),disparo.getPosy(),iPosXImagen,iPosYImagen, image.getWidth())) {
                //ganamos
                bGanamos = true;
            }
            else {
                disparo.mover();
            }
        }
        for (Disparo disparo: disparosenemigos) {
            if (colisionan(disparo.getPosx(),disparo.getPosy(),fPosXPlayer,fPosYPlayer, image.getWidth())) {
                //ganamos
                bGanamos = true;
            }
            else {
                disparo.mover();
            }
        }

        //------------------------------
        // Dibujar
        //------------------------------

        //Dibujar. Es donde hacemos que el "mundo" del videojuego muestre sus datos al jugador
        //clear. Se trata de limpiar la pantalla. Siempre antes de empezar a dibujar cualquier cosa
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        //Rutina típica de dibujado. Hay que llamar obligatoriamente a begin y a end
        batch.begin();

        //Aquí los draw...
        if(bGanamos) {
            batch.draw(endImage, 80, 0);
        } else {
            batch.draw(image, iPosXImagen, iPosYImagen);
            batch.draw(player, fPosXPlayer, fPosYPlayer, 100, 100);
            for(Disparo disparo : disparosaliados) {
                disparo.dibujar(batch);
            }
            for(Disparo disparo : disparosenemigos) {
                disparo.dibujar(batch);
            }
            for(Disparo disparo : disparosaliados) {
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
    }

    //Función colisiona. Determina cuando dos rectángulos están solapados en un espacio 2D
    public boolean colisionan(float fPosX1, float fPosY1, float fPosX2, float fPosY2, float fLado) {
        //Lado es el ancho y alto de los cuadrados que representan al jugador y a la imagen.
        //dos cuadrados se solapan parcial o totalmente si se solapan en el eje X y en el eje Y a la vez
        //un solapamiento en X implica que x1 y x2 no estén más lejos que el tamaño del lado
        //En Y, es lo mismo.
        return (Math.abs(fPosX1-fPosX2)<fLado && Math.abs(fPosY1-fPosY2)<fLado);
    }
}
