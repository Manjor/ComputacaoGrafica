package br.edu.catolica_to.templatecg;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public abstract class Geometria {

    private GL10 open;

    private float[] cor;
    private float[] pos;
    private float[] angulo;

    public GL10 getOpen() {
        return open;
    }

    public void setOpen(GL10 open) {
        this.open = open;
    }


    public float[] getCor() {
        return cor;
    }

    public void setCor(float corR, float corG, float corB, float corAlpha) {
        this.cor = new float[]{corR,corG,corB,corAlpha};
    }

    public float[] getPos() {
        return pos;
    }

    public void setPos(float[] pos) {
        FloatBuffer buffer = criaBuffer(pos);
        open.glVertexPointer(2,GL10.GL_FLOAT,0, buffer);
    }

    public float[] getAngulo() {
        return angulo;
    }

    public void setAngulo(float angulo, float x, float y, float z) {
        this.angulo = new float[]{angulo,x,y,z};
        open.glRotatef(this.angulo[0],this.angulo[1],this.angulo[2],this.angulo[3]);
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

    public abstract void Desenhar(int tamanho);
}
