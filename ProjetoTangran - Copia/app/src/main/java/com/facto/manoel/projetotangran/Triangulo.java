package com.facto.manoel.projetotangran;

import javax.microedition.khronos.opengles.GL10;

public class Triangulo extends Geometria {

    public Triangulo(GL10 gl,int posX, int posY, int largura, int altura){
        super(gl,posX,posY, largura,altura);
    }
    public void setCordenadas() {
        float [] triangulo1 = {
                -getLargura()/2,-getAltura()/2,
                getLargura()/2,-getAltura()/2,
                0,getAltura()/2
        };
        this.buffer = criaBuffer(triangulo1);
        //gl.glVertexPointer(2,GL10.GL_FLOAT,0, buffer);
    }
    @Override
    public void desenhar() {

        super.getGl().glLoadIdentity();
        super.getGl().glVertexPointer(2,GL10.GL_FLOAT,0, buffer);
        super.getGl().glTranslatef(posX,posY,0);
        super.getGl().glRotatef(getAngulo(),0,0,1);
        super.getGl().glColor4f(getCor()[0],getCor()[1],getCor()[2],getCor()[3]);
        super.getGl().glDrawArrays(GL10.GL_TRIANGLES,0,3);

    }




}
