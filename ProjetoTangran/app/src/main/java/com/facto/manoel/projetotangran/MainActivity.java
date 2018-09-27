package com.facto.manoel.projetotangran;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    GLSurfaceView superficieDesenho = null;
    Renderizador render = null;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    public static float direcaoX = 0;
    public static float direcaoY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        superficieDesenho = new GLSurfaceView(this);
        render = new Renderizador();

        superficieDesenho.setRenderer(render);
        superficieDesenho.setOnTouchListener(render);
        setContentView(superficieDesenho);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
      protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];


        if(y < -2 ){
            direcaoY = -100;
        }
        if(y > 2){
            direcaoY = 100;
        }
        if(x < -2){
            Log.i("ACE", "Virando para ESQUERDA ");
            direcaoX = +100;
        }
        if(x > 2){
            Log.i("ACE", "Virando para ESQUERDA ");
            direcaoX = -100;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
