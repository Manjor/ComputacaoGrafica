package com.facto.manoel.acelerometro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    GLSurfaceView surfaceView = null;
    Renderizador render = null;

    SensorManager sensorManager;
    Sensor accelerometro;



    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //VALIDA A VAR DE REFERENCIA PARA A SUPERFICIE
        surfaceView = new GLSurfaceView(this);

        //VALIDA A VAR DE REFERENCIA PARA O RENDERIZADOR
        render = new Renderizador();
        //ASSOCIA A SUPERFICIE DE DESENHO AO REDENDERIZADOR
        surfaceView.setRenderer(render);
        //PUBLICA A SUPERFICIE DE DESENHO NA TELA DO APP
        setContentView(surfaceView);

        sensorManager = (SensorManager) getSystemService(Context.SEARCH_SERVICE);
        accelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener((SensorEventListener) this,accelerometro,SensorManager.SENSOR_DELAY_NORMAL);

        listaSensor();
    }

    public void listaSensor(){

        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor s: deviceSensors){
            Log.i("INFO", s.getName());
        }


    }

}
