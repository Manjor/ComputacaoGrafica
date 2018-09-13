package com.facto.manoel.aula2208;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


//Esta classe implementa os metodos necessários para utilizar a biblioteca OpenGL
//No Desenho Gráfico, que será apresentado na tela pela superficie de desenho.
class Rederizador implements GLSurfaceView.Renderer{


    /** Implementa os Metodos da Interface GLSurfaceView.Renderer**/

    int fps = 0;
    long atual = 0;
    long inicio = 0;
    //onSurfaceCreated é chamado 1 vez quando a superficie de desenho foi criada.
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig config) {




        inicio = System.currentTimeMillis();
        atual = System.currentTimeMillis();
        //Configura a cor de limpeza no formato RGBA
        gl10.glClearColor(1.0f,0.0f,0.0f,1.0f);
    }

    //onSurfaceChanged é chamado quando a superficie de desenho é alterada
    //Parametros representam a nova largura e altura da tela.
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        Log.i("INFO",width + " " + height);
        //Configurando a Area de Coordenadas/Desenho do Plano Cartesiano.
        //Matrix de Projeção.
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //Carrega a Matrix identidade
        gl.glLoadIdentity();

        //Configurando o volume de Renderização
        gl.glOrthox(0,width,0,height,1,-1);

        //Configurando a matrix de trannferencias geometricas.
        //T
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }


    //onDrawFrame é chamado N vezes por segundo para desenhar na tela
    //Controle os FPS
    @Override
    public void onDrawFrame(GL10 gl) {

        //Aplica a cor de limpeza de tela a todos os bits do buffer de cor
        gl.glClear(gl.GL_COLOR_BUFFER_BIT);


        gl.glRotatef(45,1,0,0);

        atual = System.currentTimeMillis();
        fps++;

        if(atual - inicio > 1000){
            //Sorteia 3 Valores entre 0 e 1;
            float vermelho = (float) Math.random();
            float verde = (float) Math.random();
            float azul = (float) Math.random();

            gl.glClearColor(vermelho,verde,azul,1.0f);
            Log.i("INFO", "" + vermelho + " " + verde + " " + azul);

            inicio = System.currentTimeMillis();
            Log.i("INFO","FPS: " + fps);
            fps = 0;
        }



    }
    /** --- Fim da Sobrescrita dos métodos --- **/
}

public class TelaPrincipal extends AppCompatActivity {

    //Cria uma referência para o obejto de desenho
    GLSurfaceView superficieDesenho = null;
    //Cria uma referencia para o objeto de renderizador
    Rederizador render = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Valida a variavel de referência para a superficie.
        superficieDesenho = new GLSurfaceView(this);
        //Valida a variavel de referencia para o renderizador
        render = new Rederizador();

        //Associa o renderizador a superficie de desenho
        superficieDesenho.setRenderer(render);






        //Publica a superficie de desenho na tela do aplicativo
        setContentView(superficieDesenho);
    }
}
