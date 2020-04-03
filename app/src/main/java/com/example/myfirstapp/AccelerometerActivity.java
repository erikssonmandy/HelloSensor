package com.example.myfirstapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {


    private Sensor mAccelerometer;

    private SensorManager mSensorManager;
    private TextView xText, yText, zText;
    private boolean color = false;
    private View view;
    private long lastUpdate;
    private MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        view = findViewById(R.id.textView);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.pling);

        lastUpdate = System.currentTimeMillis();

        xText = (TextView) findViewById(R.id.x);
        yText = (TextView) findViewById(R.id.y);
        zText = (TextView) findViewById(R.id.z);




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

    private float round(float f) {
        float newFloat = f * 100;
        newFloat = Math.round(newFloat);
        newFloat = newFloat / 100;
        return newFloat;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.

        //final float alpha = 0.8f;

        // Isolate the force of gravity with the low-pass filter.
        //gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        //gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        //gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        //linear_acceleration[0] = event.values[0];
        // linear_acceleration[1] = event.values[1] - gravity[1];
        //linear_acceleration[2] = event.values[2] - gravity[2];

        float x = round(event.values[0]);
        float y = round(event.values[1]);
        float z = round(event.values[2]);

        xText.setText("X-position: " + Float.toString(x));
        yText.setText("Y-position: " + Float.toString(y));
        zText.setText("Z-position: " + Float.toString(z));



        if(x > 10 || x < -10) {

            vib.vibrate(10);
            view.setBackgroundColor(Color.BLUE);
            mediaPlayer.start();
        }

        if(y > 10 || y < -10){
            vib.vibrate(10);
            view.setBackgroundColor(Color.RED);
            mediaPlayer.start();

        }

        if(z > 10 || y < -10){
            vib.vibrate(10);
            view.setBackgroundColor(Color.GREEN);
            mediaPlayer.start();


        }





    }
}
