package com.example.myfirstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    ImageView compass_img;
    TextView txt_compass;
    int mAzimuth;
    private SensorManager mSensorManager;
    private Sensor mRotationV, mAccelerometer, mMagnetometer;
    boolean haveSensor = false, haveSensor2 = false;
    float[] rMat = new float[9];
    float[] orientation = new float[3];
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;

    static final float ALPHA = 0.25f; // if ALPHA = 1 OR 0, no filter applies.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        compass_img = (ImageView) findViewById(R.id.img_compasswhite);
        txt_compass = (TextView) findViewById(R.id.txt_azimuth);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer= MediaPlayer.create(getApplicationContext(), R.raw.plingpling);
        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.drang);

        start();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rMat, sensorEvent.values);
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + 360) % 360;
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(sensorEvent.values, 0, mLastAccelerometer, 0, sensorEvent.values.length);
            mLastAccelerometerSet = true;
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(sensorEvent.values, 0, mLastMagnetometer, 0, sensorEvent.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(rMat, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(rMat, orientation);
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + 360) % 360;
        }

        mAzimuth = Math.round(mAzimuth);
        compass_img.setRotation(-mAzimuth);

        String direction = "NW";

        if (mAzimuth >= 350 || mAzimuth <= 10){
            direction = "N";
            vibrator.vibrate(400);
            txt_compass.setTextColor(Color.rgb(143, 200, 50));
            mediaPlayer.start();
        }

        if (mAzimuth < 350 && mAzimuth > 280){
            direction = "NW";
            txt_compass.setTextColor(Color.WHITE);
        }

        if (mAzimuth <= 280 && mAzimuth > 260){
            direction = "W";
            txt_compass.setTextColor(Color.WHITE);
        }

        if (mAzimuth <= 260 && mAzimuth > 190){
            direction = "SW";
            txt_compass.setTextColor(Color.WHITE);
        }

        if (mAzimuth <= 190 && mAzimuth > 170){
            direction = "S";
            vibrator.vibrate(400);
            mediaPlayer2.start();
            txt_compass.setTextColor(Color.rgb(255, 87, 34));
        }

        if (mAzimuth <= 170 && mAzimuth > 100){
            direction = "SE";
            txt_compass.setTextColor(Color.WHITE);
        }

        if (mAzimuth <= 100 && mAzimuth > 80){
            direction = "E";
            txt_compass.setTextColor(Color.WHITE);
        }

        if (mAzimuth <= 80 && mAzimuth > 10) {
            direction = "NE";
            txt_compass.setTextColor(Color.WHITE);
        }

        txt_compass.setText("Direction: " + mAzimuth + "° " + direction);

    }

    protected float[] lowPass( float[] input, float[] output ) {
        if (output == null) return input;

        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }

        return output;
    }



    @Override
    public void onAccuracyChanged (Sensor sensor,int i){

    }

    public void start () {
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null) {
            if ((mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) || (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null)) {
                noSensorsAlert();
            } else {
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                haveSensor = mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
                haveSensor2 = mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_UI);
            }
        } else {
            mRotationV = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            haveSensor = mSensorManager.registerListener(this, mRotationV, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void noSensorsAlert () {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Your device doesn't support the Compass.")
                .setCancelable(false)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        alertDialog.show();
    }

    public void stop () {
        if (haveSensor && haveSensor2) {
            vibrator.cancel();
            mSensorManager.unregisterListener(this, mAccelerometer);
            mSensorManager.unregisterListener(this, mMagnetometer);

        } else {
            if (haveSensor)
                vibrator.cancel();
                mSensorManager.unregisterListener(this, mRotationV);
        }
    }

    @Override
    protected void onPause () {
        super.onPause();
        stop();
    }

    @Override
    protected void onResume () {
        super.onResume();
        start();
    }



}



