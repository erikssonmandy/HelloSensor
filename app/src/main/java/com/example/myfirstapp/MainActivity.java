package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the acceleromoter button */
    public void startAccelerometer(View view) {
        Intent intent = new Intent(getApplicationContext(), AccelerometerActivity.class);
        startActivity(intent);
    }

    public void startCompass(View view) {
        Intent intent = new Intent(getApplicationContext(), CompassActivity.class);
        startActivity(intent);
    }
}
