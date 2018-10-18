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
    int codCanguru = 0;
    int codCanguru2 = 0;
    int codCanguru3 = 0;
    int codCanguru4 = 0;
    int i;
    int direcao = 1;
    long inicio = System.currentTimeMillis();

    List<Geometria> geometrias = new ArrayList<>();
    GL10 gl10;
    List<FloatBuffer> floatBuffersList = new ArrayList<>();

    Activity vrAraActivity = null;
    Renderizador(Activity activity){
        this.vrAraActivity = activity;
    }
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(0.0f,0.0f,0.0f,0.0f);
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

        //Define a cordenada da primeira imagem.
        float vetOne[] = {0,0.25f,0,0,0.5f,0.25f,0.5f,0,0};
        float vetTwo[] = {0.5f,0.25f,0.5f,0,1,0.25f,1,0};
        float vetTree[] = {0,0.5f,0,0.25f,0.5f,0.5f,0.5f,0.25f};
        float vetFour[] = {0.5f,0.5f,0.5f,0.25f,1,0.5f,1,0.25f};
        float vetFive[] = {0,0.75f,0,0.5f,0.5f,0.75f,0.5f,0.5f};
        float vetSix[] = {0.5f,0.75f,0.5f,0.5f,1,0.75f,1,0.5f};
        float vetSeven[] = {0,1,0,0.75f,0.5f,1,0.5f,0.75f};
        float vetEight[] = {0.5f,1,0.5f,0.75f,1,1,1,0.75f};
        //open gl registra as coordenadas de textura na maquina
       // FloatBuffer texFloatBuffer = criaBuffer(vetCoordText);
        //gl10.glTexCoordPointer(2,GL10.GL_FLOAT,0,texFloatBuffer);
        FloatBuffer txt1 = criaBuffer(vetOne);
        FloatBuffer txt2 = criaBuffer(vetTwo);
        FloatBuffer txt3 = criaBuffer(vetTree);
        FloatBuffer txt4 = criaBuffer(vetFour);
        FloatBuffer txt5 = criaBuffer(vetFive);
        FloatBuffer txt6 = criaBuffer(vetSix);
        FloatBuffer txt7 = criaBuffer(vetSeven);
        FloatBuffer txt8 = criaBuffer(vetEight);


        floatBuffersList.add(txt1);
        floatBuffersList.add(txt2);
        floatBuffersList.add(txt3);
        floatBuffersList.add(txt4);
        floatBuffersList.add(txt5);
        floatBuffersList.add(txt6);
        floatBuffersList.add(txt7);
        floatBuffersList.add(txt8);

        this.gl10 = gl10;
        //this.geometrias.add(new Quadrado(gl10,(int)(largura * 0.5),(int)(altura * 0.5),0,0));
        //this.geometrias.add(new Quadrado(gl10,(int)(largura * 0.5),(int)(altura * 0.5),200,200));
        //this.geometrias.add(new Quadrado(gl10,(int)(largura * 0.75),(int)(altura * 0.75),100,100));
        this.geometrias.add(new Quadrado(gl10,800,800));
        this.geometrias.add(new Quadrado(gl10,800,800));
        this.geometrias.add(new Quadrado(gl10,800,800));
        this.geometrias.add(new Quadrado(gl10,800,800));
//        this.inverte(this.dado);
            gl10.glEnable(GL10.GL_TEXTURE_2D);
            gl10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl10.glEnable(GL10.GL_ALPHA_TEST);
            gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            this.codTextura = carregaTextura(gl10, R.mipmap.asb);
            this.codSol = carregaTextura(gl10,R.mipmap.sun);
            this.codTerra = carregaTextura(gl10,R.mipmap.terra);
            this.codLua = carregaTextura(gl10,R.mipmap.moon);
            this.codCanguru = carregaTextura(gl10,R.mipmap.canguru);


    }

    public void muda(List<FloatBuffer> list){
        if(this.i >= floatBuffersList.size()){
            this.i=-1;
        }else{
            gl10.glTexCoordPointer(2,GL10.GL_FLOAT,0,list.get(i));
            Log.i("INFO","Indice: " + this.i);
        }
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
        //geometrias.get(0).getGl().glColor4f(0.0f,0.0f,0.0f,0.0f);
        geometrias.get(0).getGl().glBindTexture(GL10.GL_TEXTURE_2D,codCanguru);
        geometrias.get(0).getGl().glTranslatef(this.anguloHora, altura/2,0);
        //geometrias.get(0).getGl().glRotatef(200,0,0,2);
        geometrias.get(0).desenhar();
//        gl10.glPushMatrix();
//        geometrias.get(1).getGl().glBindTexture(GL10.GL_TEXTURE_2D,codCanguru);
//        geometrias.get(1).getGl().glTranslatef(largura/3,altura/2,0);
//        geometrias.get(1).desenhar();
//
//        gl10.glPopMatrix();


        //gl10.glRotatef(this.anguloHora,0,0,2);
        //gl10.glLoadIdentity();
//        gl10.glPushMatrix();
//            //geometrias.get(1).getGl().glRotatef(0,0,0,1);
//            //geometrias.get().getGl().glColor4f(0.1f,0.6f,1.0f,0.5f);
//        geometrias.get(1).getGl().glBindTexture(GL10.GL_TEXTURE_2D,codTerra);
//            geometrias.get(1).getGl().glTranslatef(0 , 600,0);
//            geometrias.get(1).getGl().glRotatef(this.anguloHora,0,0,1);
//            geometrias.get(1).getGl().glScalef(0.7f,0.7f,0.7f);
//            geometrias.get(1).desenhar();
//            gl10.glPushMatrix();
//            //gl10.glLoadIdentity();
//                //geometrias.get(2).getGl().glRotatef(0,0,0,1);
//                //geometrias.get(2).getGl().glColor4f(1.1f,1.0f,1.0f,0.9f);
//                geometrias.get(2).getGl().glBindTexture(GL10.GL_TEXTURE_2D,codLua);
//                geometrias.get(2).getGl().glTranslatef(0 , 300,0);
//                geometrias.get(2).getGl().glRotatef(this.anguloHora,0,0,1);
//                geometrias.get(2).getGl().glScalef(0.7f,0.7f,0.7f);
//                geometrias.get(2).desenhar();
//
//                gl10.glPopMatrix();
//        gl10.glPopMatrix();

        if(System.currentTimeMillis() - this.inicio > 100){
            muda(floatBuffersList);
            this.i++;
            this.inicio = System.currentTimeMillis();
        }
        Log.i("INFO","Largura:" + this.largura);
        this.anguloHora += 30 * direcao;
        if(this.anguloHora + 500 >= this.largura || this.anguloHora == 0){
            this.direcao *= -1;

        }
    }
}
