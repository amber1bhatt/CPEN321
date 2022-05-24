package com.example.cpen321_amberbhatt_m1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class LocationsActivity extends AppCompatActivity {

    private TextView manufacturer;
    private TextView model;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        String deviceModel = Build.MODEL;
        String deviceManufacturer = Build.MANUFACTURER;

        manufacturer = findViewById(R.id.manufacturer_from_device);
        manufacturer.setText(deviceManufacturer);

        model = findViewById(R.id.model_from_device);
        model.setText(deviceModel);

        Log.d("LocationsActivity", deviceModel);
        Log.d("LocationsActivity", deviceManufacturer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LocationsActivity.this, MainActivity.class));
        finish();
    }
}