package com.facto.manoel.projetotangran;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderizador implements GLSurfaceView.Renderer {

    int largura;
    int altura;
    Triangulo tri ;
    Triangulo tri2;
    Triangulo tri3;
    Triangulo tri4;
    Triangulo tri5;
    Quadrado quad;
    Paralelograma para ;


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



        tri = new Triangulo(gl10,100,600,200,100);
        tri2 = new Triangulo(gl10,300,600,400,200);
        tri3 = new Triangulo(gl10,500,600,400,200);
        tri4 = new Triangulo(gl10, 300,515,200,100);
        int larguraQuadrado = (int)Math.sqrt(Math.pow(100,2)+ Math.pow(100,2));
        quad = new Quadrado(gl10,435,460,larguraQuadrado,larguraQuadrado);
        tri5 = new Triangulo(gl10,500,400,(int)Math.sqrt(Math.pow(200,2)+ Math.pow(200,2)),(int)Math.sqrt(Math.pow(200,2)+ Math.pow(200,2))/2 );
        //tri.setCordenadas(triangulo1);
        tri.setCor(0.4f,0.8f,0.7f,1.0f);
        tri3.setCor(0.2f,0.8f,1f,0.5f);
        tri4.setCor(0.3f,1.0f,0.2f,1.0f);
        quad.setCor(0.5f,1.0f,0.2f,1.0f);
        para = new Paralelograma(gl10,580,500,300,100);
        para.setCor(0.3f,0.8f,0.4f,0.6f);
        para.setAnguloy(180);

        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);




    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        gl10.glClear(gl10.GL_COLOR_BUFFER_BIT);
        gl10.glLoadIdentity();
        tri.desenhar();
        tri2.desenhar();
        tri3.setAngulo(180);
        tri3.desenhar();
        tri4.setAngulo(315);
        tri4.desenhar();
        quad.desenhar();
        tri5.setAngulo(270);
        tri5.desenhar();
        para.desenhar();


    }
}
