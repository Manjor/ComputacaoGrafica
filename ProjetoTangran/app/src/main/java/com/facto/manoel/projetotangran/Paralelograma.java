package com.facto.manoel.projetotangran;

import javax.microedition.khronos.opengles.GL10;

public class Paralelograma extends Geometria {



    public Paralelograma(GL10 gl,int posX, int posY, int largura, int altura){
        super(gl,posX,posY, largura,altura);
    }
    public void setCordenadas() {
        float [] paralelograma = {
                -getLargura()/2,-getAltura()/2,
                getLargura()/6,-getAltura()/2,
                -getLargura()/6,getAltura()/2,
                getLargura()/2,getAltura()/2
        };
        this.buffer = criaBuffer(paralelograma);
        //gl.glVertexPointer(2,GL10.GL_FLOAT,0, buffer);
    }
    @Override
    public void desenhar() {

        super.getGl().glLoadIdentity();
        super.getGl().glVertexPointer(2,GL10.GL_FLOAT,0, buffer);
        super.getGl().glTranslatef(posX,posY,0);
        super.getGl().glRotatef(getAngulo(),0,0,1);
        super.getGl().glRotatef(getAnguloy(),0,1,0);
        super.getGl().glColor4f(getCor()[0],getCor()[1],getCor()[2],getCor()[3]);
        super.getGl().glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,4);

    }


}
