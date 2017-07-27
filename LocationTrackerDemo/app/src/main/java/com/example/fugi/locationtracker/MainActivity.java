package com.example.fugi.locationtracker;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    LocationRequest mLocationRequest = new LocationRequest().setInterval(10000).setFastestInterval(5000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    TextView tv;
    GoogleMap mMap;
    LatLng pos = new LatLng(100,100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.message);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this,1,Manifest.permission.ACCESS_FINE_LOCATION,true);
        }else{
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    for (Location location : locationResult.getLocations()) {
                        pos = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                }
            };
        }

        //updateValuesFromBundle(savedInstanceState);
    }
//    private void updateValuesFromBundle(Bundle savedInstanceState) {
//        // Update the value of mRequestingLocationUpdates from the Bundle.
//        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
//            mRequestingLocationUpdates = savedInstanceState.getBoolean(
//                    REQUESTING_LOCATION_UPDATES_KEY);
//        }
//
//        // ...
//
//        // Update UI to match restored state
//        updateUI();
//    }
    @Override
    protected void onResume() {
        super.onResume();
            startLocationUpdates();
    }

    private void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title("Marker"));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode != 1){
            return;
        }
    }
}
