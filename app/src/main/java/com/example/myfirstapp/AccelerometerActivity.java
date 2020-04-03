package com.example.myfirstapp;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private TextView xText, yText, zText, tiltText;
    private View view;
    private long lastUpdate;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        view = findViewById(R.id.textView);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        lastUpdate = System.currentTimeMillis();

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.dring);
        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.pling);

        lastUpdate = System.currentTimeMillis();

        xText = (TextView) findViewById(R.id.x);
        yText = (TextView) findViewById(R.id.y);
        zText = (TextView) findViewById(R.id.z);
        tiltText = (TextView) findViewById(R.id.tilttext);

    }

    private float round(float f) {
        float newFloat = f * 10;
        newFloat = Math.round(newFloat);
        newFloat = newFloat / 10;
        return newFloat;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        float x = round(event.values[0]);
        float y = round(event.values[1]);
        float z = round(event.values[2]);

        xText.setText("X-position: " + Float.toString(x));
        yText.setText("Y-position: " + Float.toString(y));
        zText.setText("Z-position: " + Float.toString(z));

        if(x > 8 || x < -8) {

            vib.vibrate(500);

            if(x > 8) {
                view.setBackgroundColor(Color.rgb(143, 200, 50));
                tiltText.setText("LEFT");
                mediaPlayer.start();
            }

            if( x < -8){
                view.setBackgroundColor(Color.rgb(255, 193, 34));
                tiltText.setText("RIGHT");
                mediaPlayer2.start();
            }

        }else{
            view.setBackgroundColor(Color.TRANSPARENT);
            tiltText.setText("");
        }
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }





    }

