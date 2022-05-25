package com.example.cpen321_amberbhatt_m1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button favouriteCityButton;
    private Button locationButton;
    private Button surpriseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favouriteCityButton = findViewById(R.id.button_favourite_city);
        favouriteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favouriteCityIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(favouriteCityIntent);
            }
        });

        locationButton = findViewById(R.id.button_location);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean locationPermissionsChecked = checkLocationPermissions();

                if(locationPermissionsChecked) {
                    Intent locationsIntent = new Intent(MainActivity.this, LocationsActivity.class);
                    startActivity(locationsIntent);
                }
            }
        });

        surpriseButton = findViewById(R.id.button_surprise);
        surpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataTask getDataTask = new GetDataTask();
                getDataTask.execute();
                Intent surpriseIntent = new Intent(MainActivity.this, SurpriseActivity.class);
                startActivity(surpriseIntent);
            }
        });
    }

    private boolean checkLocationPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Location Permissions Received!", Toast.LENGTH_LONG).show();

            Intent favouriteCityIntent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(favouriteCityIntent);

            return true;
        } else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "we need these location permission to run!", Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(this)
                        .setTitle("Need Location Permissions")
                        .setMessage("We need the Location Permissions to mark your location on the map")
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(MainActivity.this, "We will need these location permission to run!", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        return false;
    }

}