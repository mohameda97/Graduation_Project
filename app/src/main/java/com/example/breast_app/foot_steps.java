package com.example.breast_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class foot_steps extends AppCompatActivity implements SensorEventListener {

    TextView steps;
    SensorManager sensorManager;
    boolean Running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_steps);

        steps = findViewById(R.id.steps);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(getApplicationContext(), "sensor not found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        steps.setText(String.valueOf(event.values[0]));
        /*
        if (Utils.getBoolean(this,"first_time",true)){
            //check if it first time
            Utils.putLong(this,"reference",(long) event.values[0]);
            Utils.putBoolean(this,"first_time",false);
        }
        if (Running) {
            long displayValue = (long) event.values[0] - Utils.getLong(this,"reference");
            steps.setText(String.valueOf(displayValue));
        }
        */
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
