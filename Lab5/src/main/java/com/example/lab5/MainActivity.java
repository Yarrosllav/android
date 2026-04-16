package com.example.lab5;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView tvAngleX, tvAngleY;
    private View bubble;

    private float maxMovement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAngleX = findViewById(R.id.tvAngleX);
        tvAngleY = findViewById(R.id.tvAngleY);
        bubble = findViewById(R.id.bubble);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        if (accelerometer == null) {
            Toast.makeText(this, "Акселерометр не знайдено", Toast.LENGTH_LONG).show();
        }

        float density = getResources().getDisplayMetrics().density;
        maxMovement = 120 * density;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (accelerometer != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x = event.values[0];
            float y = event.values[1];

            int angleX = (int) (x * 90 / 9.81);
            int angleY = (int) (y * 90 / 9.81);

            tvAngleX.setText("X: " + (-angleX) + "°");
            tvAngleY.setText("Y: " + angleY + "°");

            float deltaX = (x / 9.81f) * maxMovement;
            float deltaY = (y / 9.81f) * maxMovement;

            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            if (distance > maxMovement) {
                float ratio = (float) (maxMovement / distance);
                deltaX *= ratio;
                deltaY *= ratio;
            }

            bubble.setTranslationX(-deltaX);
            bubble.setTranslationY(deltaY);

            GradientDrawable bubbleShape = (GradientDrawable) bubble.getBackground();
            if (Math.abs(angleX) <= 2 && Math.abs(angleY) <= 2) {
                bubbleShape.setColor(Color.parseColor("#4CAF50")); // Зелений
            } else {
                bubbleShape.setColor(Color.parseColor("#FF5252")); // Червоний
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}