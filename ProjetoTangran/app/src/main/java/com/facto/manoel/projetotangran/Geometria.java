package com.facto.manoel.projetotangran;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public abstract class Geometria {

    private GL10 gl;
    private float[] cor = {0.5f,0.5f,0.5f,1f};
    private float[] cordenadas;
    private int angulo = 0;
    private int anguloy = 0;
    public FloatBuffer buffer;
    public int posX;
    public int posY;
    private int largura;

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAngulo() {
        return angulo;
    }

    public void setAngulo(int angulo) {
        this.angulo = angulo;
    }

    public int getAnguloy() {
        return anguloy;
    }

    public void setAnguloy(int anguloy) {
        this.anguloy = anguloy;
    }

    private int altura;

    public Geometria(GL10 gl,int posX, int posY, int largura, int altura){
        this.gl = gl; this.posX = posX; this.posY = posY; this.largura = largura;
        this.altura = altura;
        setCordenadas();
    }

    public GL10 getGl(){
        return this.gl;
    }
    public void setCor(float corR, float corG, float corB, float corAlpha) {
        this.cor = new float[]{corR,corG,corB,corAlpha};
    }
    public float[] getCor() {
        return cor;
    }
    public float[] getCordenadas() {
        return cordenadas;
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

    public abstract void desenhar();
    public abstract void setCordenadas();
}
