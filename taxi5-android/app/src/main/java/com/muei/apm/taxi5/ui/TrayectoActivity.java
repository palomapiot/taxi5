package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.muei.apm.taxi5.R;

import java.util.ArrayList;

public class TrayectoActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final String TAG = TrayectoActivity.class.getSimpleName();
    private static GoogleMap mMap;
    ArrayList markerPoints = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate()");
        } else {
            Log.d(TAG, "onCreate() with previousState");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trayecto);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(43.333024, -8.410868);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in FIC"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));


    }




    public void onConfirmButtonClick(View view) {
        Log.d(TAG, "Boton para cambio de actividad pulsado en Trayecto");
        Toast.makeText(getApplicationContext(), "Boton de confirmar pulsado", Toast.LENGTH_SHORT).show();
    }

    public void onCancelButtonClick(View view) {
        Log.d(TAG, "Boton para cambio de actividad pulsado en Trayecto");
        Intent intent = new Intent(TrayectoActivity.this, MapsActivity.class);
        startActivity(intent);
    }


}