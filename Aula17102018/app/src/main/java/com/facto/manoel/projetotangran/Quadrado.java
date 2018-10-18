package com.facto.manoel.projetotangran;

import javax.microedition.khronos.opengles.GL10;

public class Quadrado extends Geometria {

    public Quadrado(GL10 gl,int posX, int posY, int largura, int altura){
        super(gl,posX,posY, largura,altura);
    }

    public Quadrado(GL10 gl, int largura, int altura) {
        super(gl, largura, altura);
    }

    public void setCordenadas() {
        float [] quadrado = {
                -getLargura()/2,-getAltura()/2,
                -getLargura()/2,getAltura()/2,
                -getLargura()/2,getAltura()/2,
                getLargura()/2,getAltura()/2
        };
        this.buffer = criaBuffer(quadrado);
        super.getGl().glVertexPointer(2,GL10.GL_FLOAT,0, buffer);
    }
    @Override
    public void desenhar() {

//        super.getGl().glLoadIdentity();
//        super.getGl().glVertexPointer(2,GL10.GL_FLOAT,0, buffer);
        /*
        if(MainActivity.direcaoY == 0){
            posY = 0;
        }

        if(MainActivity.direcaoX == 0){
            posX = 0;
        }

        if(MainActivity.direcaoX == 100){
            posX += 5;
        }
        if(MainActivity.direcaoX == -100){
            posX -= 5;
        }
        if(MainActivity.direcaoY == 100){
            posY += 5;
        }
        if(MainActivity.direcaoY == -100){
            posY -= 5;
        }*/
        //super.getGl().glTranslatef(posX,posY,0);
        //super.getGl().glRotatef(getAngulo(),0,0,1);
        //super.getGl().glColor4f(getCor()[0],getCor()[1],getCor()[2],getCor()[3]);
        super.getGl().glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,4);

    }



}
