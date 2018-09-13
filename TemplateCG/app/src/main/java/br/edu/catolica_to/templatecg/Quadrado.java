package br.edu.catolica_to.templatecg;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class Quadrado extends Geometria {

    public Quadrado()
    {

    }


    @Override
    public void Desenhar(int tamanho) {
        GL10 open = super.getOpen();
        open.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,tamanho);
    }
}
