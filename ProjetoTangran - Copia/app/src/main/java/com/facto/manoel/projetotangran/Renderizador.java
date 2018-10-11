package com.facto.manoel.projetotangran;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderizador implements GLSurfaceView.Renderer/* View.OnTouchListener*/{

    int largura;
    int altura;

    Quadrado quad;
    int posX = 100;
    int posY = 600;
    float angulo = 0;

    //float maxAngulo = 5f;
    boolean limite = false;
    float anguloSegundo = 0;
    float anguloMinuto = 0;
    float anguloHora = 0;

    float maxValue = 60;
    int codTextura = 0;
    int codSol = 0;
    int codTerra = 0;
    int codLua = 0;

    List<Geometria> geometrias = new ArrayList<>();
    GL10 gl10;

    Activity vrAraActivity = null;
    Renderizador(Activity activity){
        this.vrAraActivity = activity;
    }
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(1.0f,1.0f,0.0f,1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int largura, int altura) {

        this.largura = largura;
        this.altura = altura;

        gl10.glMatrixMode(gl10.GL_PROJECTION);
        gl10.glLoadIdentity(); //SETANDO MATRIZ IDENTIDADE NA MATRIZ DE PRODUCAO
        gl10.glOrthof(0.0f, largura,
                0.0f, altura,
                -1.0f, 1.0f); //DEFININDO O VOLUME DE RENDERIZACAO

        gl10.glMatrixMode(GL10.GL_MODELVIEW); //APONTANDO O OPENGL PARA A MATRIZ DE TRANSFORMACOES GEOMETRICAS (tranzacao, rotacao e escala)
        gl10.glLoadIdentity(); //para limpar o lixo de memoria e setar a matriz inicial

        gl10.glViewport(0,0,this.largura,this.altura);
        //Define as coordenadas de textura para a maquina.
        float vetCoordText[] = {0,1,0,0,1,1,1,0};

        //open gl registra as coordenadas de textura na maquina
        FloatBuffer texFloatBuffer = criaBuffer(vetCoordText);
        gl10.glTexCoordPointer(2,GL10.GL_FLOAT,0,texFloatBuffer);

        this.gl10 = gl10;
        //this.geometrias.add(new Quadrado(gl10,(int)(largura * 0.5),(int)(altura * 0.5),0,0));
        //this.geometrias.add(new Quadrado(gl10,(int)(largura * 0.5),(int)(altura * 0.5),200,200));
        //this.geometrias.add(new Quadrado(gl10,(int)(largura * 0.75),(int)(altura * 0.75),100,100));
        this.geometrias.add(new Quadrado(gl10,400,400));
        this.geometrias.add(new Quadrado(gl10,300,300));
        this.geometrias.add(new Quadrado(gl10,300,300));

            gl10.glEnable(GL10.GL_TEXTURE_2D);
            gl10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl10.glEnable(GL10.GL_ALPHA_TEST);
            gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            this.codTextura = carregaTextura(gl10, R.mipmap.asb);
            this.codSol = carregaTextura(gl10,R.mipmap.sun);
            this.codTerra = carregaTextura(gl10,R.mipmap.terra);
            this.codLua = carregaTextura(gl10,R.mipmap.moon);

    }
    //Metodo utlizido para carregar uma textura
    int carregaTextura(GL10 opengl,int codTextura){
        //Carrega a imagem na memoria
        Bitmap imagem = BitmapFactory.decodeResource(vrAraActivity.getResources(),codTextura);
        //Define um array para armazenamento dos ids de textura
        int[] idTextura = new int[1];
        //Gera as areas na gpu e cria um id para cada uma
        opengl.glGenTextures(1,idTextura,0);

        //Aponta a maquina open para uma das areas de memoria da gpu
        opengl.glBindTexture(GL10.GL_TEXTURE_2D,idTextura[0]);

        //Copia a imagem de RAM para a VRAM
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D,0,imagem,0);
        opengl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
        opengl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_NEAREST);
        //Aponta a Vram openGl para a nada.
        opengl.glBindTexture(GL10.GL_TEXTURE_2D,0);
        imagem.recycle();
        return idTextura[0];
    }

    //conversor de array de coordenadas java -> array de coordenadas openGL
    FloatBuffer criaBuffer(float[] coordenadas) {
        //ALOCA A QTD DE BYTES
        ByteBuffer vetBytes = ByteBuffer.allocateDirect(coordenadas.length * 4);

        vetBytes.order(ByteOrder.nativeOrder());

        //CRIA O FLOAT BUFFER A PARTIR DO BYTEBUF
        FloatBuffer buffer = vetBytes.asFloatBuffer();
        //limpa eventual lixo na memoria
        buffer.clear();
        //encaspula o array java no objeto float buffer
        buffer.put(coordenadas);
        //retira sobras da memoria
        buffer.flip();
        //retorna o objeto de coordenadas
        return buffer;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        gl10.glClear(gl10.GL_COLOR_BUFFER_BIT);
        gl10.glLoadIdentity();

        //geometrias.get(1).getGl().glRotatef(0,0,0,1);
        //geometrias.get(0).getGl().glColor4f(0.0f,0.0f,0.7f,0.6f);
        geometrias.get(0).getGl().glBindTexture(GL10.GL_TEXTURE_2D,codSol);
        geometrias.get(0).getGl().glTranslatef(largura/2, 1200,0);
        geometrias.get(0).getGl().glRotatef(this.anguloHora,0,0,2);

        geometrias.get(0).desenhar();
        //gl10.glLoadIdentity();
        gl10.glPushMatrix();
            //geometrias.get(1).getGl().glRotatef(0,0,0,1);
            //geometrias.get().getGl().glColor4f(0.1f,0.6f,1.0f,0.5f);
        geometrias.get(1).getGl().glBindTexture(GL10.GL_TEXTURE_2D,codTerra);
            geometrias.get(1).getGl().glTranslatef(0 , 600,0);
            geometrias.get(1).getGl().glRotatef(this.anguloHora,0,0,1);
            geometrias.get(1).getGl().glScalef(0.7f,0.7f,0.7f);
            geometrias.get(1).desenhar();
            gl10.glPushMatrix();
            //gl10.glLoadIdentity();
                //geometrias.get(2).getGl().glRotatef(0,0,0,1);
                //geometrias.get(2).getGl().glColor4f(1.1f,1.0f,1.0f,0.9f);
                geometrias.get(2).getGl().glBindTexture(GL10.GL_TEXTURE_2D,codLua);
                geometrias.get(2).getGl().glTranslatef(0 , 300,0);
                geometrias.get(2).getGl().glRotatef(this.anguloHora,0,0,1);
                geometrias.get(2).getGl().glScalef(0.7f,0.7f,0.7f);
                geometrias.get(2).desenhar();

                gl10.glPopMatrix();
        gl10.glPopMatrix();

        this.anguloHora+=3;



//        if(this.anguloMinuto > 60){
//            this.anguloHora+=1;
//            this.limite = false;
//        }
//        else if(this.angulo < -360){
//            this.limite = true;
//        }
//
//        if(this.limite){
//            this.angulo+=0.2f;
//        }
//        else{
//            this.angulo-=0.2f;
//        }

/*
        gl10.glPushMatrix();
            geometrias.get(1).getGl().glColor4f(1.0f,0.0f,0.8f,1.0f);
            //geometrias.get(1).getGl().glRotatef(0,0,0,1);
            geometrias.get(1).getGl().glTranslatef(largura - 1.0f , altura * 0.10f,0);
            //geometrias.get(1).getGl().glRotatef(0,0,0,1);
            //geometrias.get(1).getGl().glScalef(0.5f,0.5f,0.5f);
            //geometrias.get(1).setAngulo((int)this.angulo);
            geometrias.get(1).desenhar();
        /*synchronized (this) {
            for (Geometria geometria : this.geometrias) {

                geometria.getGl().glScalef(0.5f,0.5f,0.5f);
                geometria.desenhar();
            }
        }*/
            /*gl10.glPushMatrix();
            geometrias.get(2).getGl().glColor4f(0.0f,0.7f,0.8f,1.0f);
            geometrias.get(2).getGl().glRotatef(0,0,0,1);
            geometrias.get(2).getGl().glTranslatef((int)(largura + 10), (int) (altura * 0.22),0);
            geometrias.get(2).getGl().glRotatef(0,0,0,1);
            //geometrias.get(2).getGl().glScalef(0.5f,0.5f,0.5f);
            //geometrias.get(1).setAngulo((int)this.angulo);
            geometrias.get(2).desenhar();
            gl10.glPopMatrix();



            gl10.glPopMatrix();*/
    }


    int indiceObjeto = -1;
    int forma = -1;
    boolean ischecked = false;

/*
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
    }*/
}
