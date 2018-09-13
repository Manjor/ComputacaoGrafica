package br.edu.catolica_to.templatecg;

import javax.microedition.khronos.opengles.GL10;

public class Triangulo extends Geometria {

    GL10 gl ;

    public Triangulo(GL10 gl){
        this.gl = gl;
    }


    @Override
    public void Desenhar(int tamanho) {
        gl.glLoadIdentity();
        gl.glDrawArrays(GL10.GL_TRIANGLES,0,tamanho);
    }
}
