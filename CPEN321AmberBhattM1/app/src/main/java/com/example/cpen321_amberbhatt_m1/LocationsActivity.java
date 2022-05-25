package com.example.cpen321_amberbhatt_m1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationsActivity extends AppCompatActivity implements LocationListener {

    private TextView current_city;
    private TextView manufacturer;
    private TextView model;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        String deviceModel = Build.MODEL;
        String deviceManufacturer = Build.MANUFACTURER;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

        manufacturer = findViewById(R.id.manufacturer_from_device);
        manufacturer.setText(deviceManufacturer);

        model = findViewById(R.id.model_from_device);
        model.setText(deviceModel);

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        current_city = findViewById(R.id.current_city_from_device);
        if(location != null) {
            try {
                Geocoder geocoder = new Geocoder(LocationsActivity.this, Locale.getDefault());
                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                current_city.setText(addressList.get(0).getLocality());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            current_city.setText("Something went wrong while getting the current city");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LocationsActivity.this, MainActivity.class));
        finish();
    }
}