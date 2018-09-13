package com.facto.manoel.projetotangran;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    GLSurfaceView superficieDesenho = null;
    Renderizador render = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        superficieDesenho = new GLSurfaceView(this);
        render = new Renderizador();

        superficieDesenho.setRenderer(render);

        setContentView(superficieDesenho);

    }
}
