package com.muei.apm.taxi5.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.muei.apm.taxi5.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final String TAG = MapsActivity.class.getSimpleName();
    private static GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate()");
        } else {
            Log.d(TAG, "onCreate() with previousState");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        configureMaps();
    }

    private void configureMaps() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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
    }


    public void onDirectionsClick(View view) {
        Log.d(TAG, "Boton direceciones pulsado en Trayecto");
        Toast.makeText(getApplicationContext(), "Boton direcciones", Toast.LENGTH_SHORT).show();
    }
}
