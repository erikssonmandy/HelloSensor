package com.example.myfirstapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {


    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private TextView xText, yText, zText;
    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        xText = (TextView)findViewById(R.id.x);
        yText = (TextView)findViewById(R.id.y);
        zText = (TextView)findViewById(R.id.z);

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

    @Override
    public void onSensorChanged(SensorEvent event) {
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.

        final float alpha = 0.8f;

        // Isolate the force of gravity with the low-pass filter.
        //gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        //gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        //gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        //linear_acceleration[0] = event.values[0];
       // linear_acceleration[1] = event.values[1] - gravity[1];
        //linear_acceleration[2] = event.values[2] - gravity[2];

        xText.setText("X: " + Math.round(event.values[0]));
        yText.setText("Y: " + Math.round(event.values[1]));
        zText.setText("Z: " + Math.round(event.values[2]));

    }
}
