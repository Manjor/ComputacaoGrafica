package br.edu.catolica_to.templatecg;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

//ESTA CLASSE IMPLEMENTA OS METODOS NECESSARIOS PARA
//UTILIZAR A BIBLIOTECA OPENGL NO DESENHO GRAFICO
//QUE SERA APRESENTADO NA TELA PELA SUPERFICIE DE DESENHO
class Renderizador implements GLSurfaceView.Renderer, View.OnTouchListener {

    float posX = 0;
    float direcao = 1;
    float posY = 0;
    float escala = 1;
    float angulo = 0;
    int largura;
    int altura;
    //vetor de coordenadas
    FloatBuffer buffer1;
    Triangulo triangulo ;







    //------------------------------------- INICIO METODOS OPENGL --------------------------------------------------------------


    //CHAMADO UMA VEZ QUANDO A SUPERFICIE DE DESENHO FOR CRIADA
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //CONFIGURA A COR DE LIMPEZA DA TELA (RGBA)
        gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f); //vermelha
    }






    //CHAMADO QUANDO A SUPERFICIE DE DESENHO FOR ALTERADA (QUANDO CRIA E QUANDO ROTACIONA A TELA)
    @Override
    public void onSurfaceChanged(GL10 gl, int largura, int altura) {

        this.largura = largura;
        this.altura = altura;
        //IMPRIME A LARGURA E ALTURA NO CONSOLE DO ANDROID STUDIO
        Log.i("INFO", largura + " " + altura);

        //CONFIGURANDO A AREA DE COORDENADAS DO PLANO CARTESIANO
        gl.glMatrixMode(gl.GL_PROJECTION); //APONTANDO O OPENGL PARA A MATRIZ DE PRODUCAO (usada para definir area de rendereizacao)
        gl.glLoadIdentity(); //SETANDO MATRIZ IDENTIDADE NA MATRIZ DE PRODUCAO
        gl.glOrthof(0f, largura,
                0f, altura,
                -1f, 1f); //DEFININDO O VOLUME DE RENDERIZACAO

        gl.glMatrixMode(GL10.GL_MODELVIEW); //APONTANDO O OPENGL PARA A MATRIZ DE TRANSFORMACOES GEOMETRICAS (tranzacao, rotacao e escala)
        gl.glLoadIdentity(); //para limpar o lixo de memoria e setar a matriz inicial

        //DESENHANDO...................................................................................

        //CONFIGURA A AREA DE VISUALIZAÇÃO NA TELA
        gl.glViewport(0, 0, largura, altura);
        //----------------------------------------------------------------------------------------------//
        float [] triaguloCabeca = {
                0,altura/3,
                largura/3,0,
                altura/2,largura/2
        };

        triangulo.setPos(triaguloCabeca);

        //CONVERTE PARA O VETOR DO OPENGL
        buffer1 = criaBuffer(triaguloCabeca);

        //SOLICITA AO OPENGL PERMISSAO PARA USAR O VETOR DE VERTICES
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        //REGISTRA O VETOR DE COORDENADAS NA OPENGL
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, buffer1);
        //FIM DESENHO.........................................
    }






    //CHAMADO N VEZES POR SEGUNDO PARA DESENHAR NA TELA
    //QUANTIDADE DE VEZES CHAMADO, DETERMINA O FPS
    //QUANTO MAIS COISAS HOUVER AQUI DENTRO MENOR A TAXA DE FPS
    @Override
    public void onDrawFrame(GL10 gl) {


        /*posX += direcao * 5.0;

        posY += escala *5.0;
        if(posX + largura/2 >= largura || posX <= 0){
            direcao *= -1;
        }
        if(posY + altura/2 >= altura || posY <= 0)
        {
            escala *= -1;
        }

        */

        //APLICA A COR DE LIMPEZA DE TELA A TODOS O BITS DO BUFER DE COR
        gl.glClear(gl.GL_COLOR_BUFFER_BIT);
        //Carrega novamente a identidade, para não ocorrer de acomular as translações
        gl.glLoadIdentity();
        //Faz uma translação no objeto, em,x , y e z

        gl.glTranslatef(largura/4,altura/4,0);
        gl.glRotatef(angulo,0,0,1);
        angulo += 5;
        //DESENHO A COR DO DESENHO
        triangulo.setCor(1,1,0,1);
        triangulo.Desenhar(3);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4); //a partir da posicao 0 desenha 4 vertices

        /*
        gl.glColor4f(0, 1,1.3f, 1); //cor blue
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 3, 3); //a partir da posicao 3 desenha 3 vertices*/




    }



    //------------------------------------- FIM METODOS OPENGL --------------------------------------------------------------











    //conversor de array de coordenadas java -> array de coordenadas openGL
    FloatBuffer criaBuffer(float[] coordenadas) {
        //ALOCA A QTD DE BYTES
        ByteBuffer vetBytes =
                ByteBuffer.allocateDirect(coordenadas.length * 4);

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
    public boolean onTouch(View view, MotionEvent motionEvent) {

        posX = (int)motionEvent.getX();
        posY = altura - (int)motionEvent.getY();

        return true;
    }
}

public class TelaPrincipal extends AppCompatActivity {

    //CRIA REFERENCIA PARA OBJETO DE DESENHO
    GLSurfaceView superficieDesenho = null;
    Renderizador render = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //VALIDA A VAR DE REFERENCIA PARA A SUPERFICIE
        superficieDesenho = new GLSurfaceView(this);

        //VALIDA A VAR DE REFERENCIA PARA O RENDERIZADOR
        render = new Renderizador();

        //ASSOCIA A SUPERFICIE DE DESENHO AO REDENDERIZADOR
        superficieDesenho.setRenderer(render);

        //PUBLICA A SUPERFICIE DE DESENHO NA TELA DO APP
        setContentView(superficieDesenho);
    }
}
