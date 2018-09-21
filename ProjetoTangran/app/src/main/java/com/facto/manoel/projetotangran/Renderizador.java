package com.facto.manoel.projetotangran;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderizador implements GLSurfaceView.Renderer, View.OnTouchListener {

    int largura;
    int altura;

    Quadrado quad;
    int posX = 100;
    int posY = 600;

    List<Geometria> geometrias = new ArrayList<>();
    GL10 gl10;
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(1.0f,0.0f,0.0f,1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int largura, int altura) {

        this.largura = largura;
        this.altura = altura;

        gl10.glMatrixMode(gl10.GL_PROJECTION);
        gl10.glLoadIdentity(); //SETANDO MATRIZ IDENTIDADE NA MATRIZ DE PRODUCAO
        gl10.glOrthof(0f, largura,
                0f, altura,
                -1f, 1f); //DEFININDO O VOLUME DE RENDERIZACAO

        gl10.glMatrixMode(GL10.GL_MODELVIEW); //APONTANDO O OPENGL PARA A MATRIZ DE TRANSFORMACOES GEOMETRICAS (tranzacao, rotacao e escala)
        gl10.glLoadIdentity(); //para limpar o lixo de memoria e setar a matriz inicial

        gl10.glViewport(0,0,largura,altura);


        this.gl10 = gl10;
        this.geometrias.add(new Quadrado(gl10,(int)(largura * 0.25),(int)(altura * 0.9),0,0));
        this.geometrias.add(new Quadrado(gl10,(int)(largura * 0.25),(int)(altura * 0.9),175,175));
        this.geometrias.add(new Triangulo(gl10,(int)(largura * 0.5),(int)(altura * 0.9),250,125));
        this.geometrias.add(new Paralelograma(gl10,(int)(largura * 0.75),(int)(altura * 0.9),300,100));
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);


    }
    @Override
    public void onDrawFrame(GL10 gl10) {

        gl10.glClear(gl10.GL_COLOR_BUFFER_BIT);
        gl10.glLoadIdentity();

        Log.i("INFO","Tamanho: " + geometrias.size());


        synchronized (this){
            for(Geometria geometria : this.geometrias){

                geometria.desenhar();

            }
        }



    }


    int indiceObjeto = -1;
    int forma = -1;
    boolean ischecked = false;


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        synchronized (this){
            if(event.getAction() == MotionEvent.ACTION_MOVE) {
                if (indiceObjeto == -1) return true;
                if(ischecked == true) {


                    //seto as possicoes X e Y do desenho
                    posX = (int) event.getX();
                    posY = altura - (int) event.getY();


                    this.geometrias.get(indiceObjeto).posX = posX;
                    this.geometrias.get(indiceObjeto).posY = posY;
                }
            }
            else if(event.getAction() == MotionEvent.ACTION_UP ){

                ischecked = false;
//            //seto as possicoes X e Y do desenho
//            posX = (int)event.getX();
//            posY = altura - (int)event.getY();
//            this.geometrias.add(new Quadrado(gl10,posX,posY,400,400));
            }
            else if(event.getAction() == MotionEvent.ACTION_DOWN) {
                //seto as possicoes X e Y do desenho
                posX = (int) event.getX();
                posY = altura - (int) event.getY();

                for (int i = geometrias.size() - 1; i >= 0; i--) {

                    if (event.getX() > geometrias.get(i).posX - geometrias.get(i).getLargura() / 2 && event.getX() < geometrias.get(i).posX + geometrias.get(i).getLargura() / 2 && (altura - event.getY()) > geometrias.get(i).posY - geometrias.get(i).getAltura() / 2 && (altura - event.getY()) < geometrias.get(i).posY + geometrias.get(i).getAltura() / 2) {

                        switch (i) {
                            case 0:
                                break;
                            case 1:
                                forma = 0;
                                geometrias.remove(0);
                                geometrias.add(0, new Quadrado(gl10, (int) (largura * 0.25), (int) (altura * 0.9), 205, 205));
                                break;
                            case 2:
                                forma = 1;
                                geometrias.remove(0);
                                this.geometrias.add(0, new Triangulo(gl10, (int) (largura * 0.5), (int) (altura * 0.9), 290, 145));
                                break;
                            case 3:
                                forma = 2;
                                geometrias.remove(0);
                                this.geometrias.add(0, new Paralelograma(gl10, (int) (largura * 0.75), (int) (altura * 0.9), 360, 120));
                                break;
                            default:
                                ischecked = true;
                                indiceObjeto = i;
                                break;
                        }
                        return true;
                    }
                }
                switch (forma) {
                    case 0:
                        this.geometrias.add(new Quadrado(gl10, (int) event.getX(), (int) (altura - event.getY()), 175, 175));
                        break;
                    case 1:
                        this.geometrias.add(new Triangulo(gl10, (int) event.getX(), (int) (altura - event.getY()), 250, 125));
                        break;
                    case 2:
                        this.geometrias.add(new Paralelograma(gl10, (int) event.getX(), (int) (altura - event.getY()), 300, 100));
                        break;
                }
            }
            return true;
        }
    }
}
