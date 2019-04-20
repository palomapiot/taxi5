package com.muei.apm.taxi5.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.muei.apm.taxi5.R;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG_HOME_ACTIVITY = HomeActivity.class.getSimpleName();
    private static GoogleMap mMap;

    private static double longitude = 43.333024;
    private static double latitude = -8.410868;
    LocationManager mLocationManager;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            Log.d(TAG_HOME_ACTIVITY, "onCreate()");
        } else {
            Log.d(TAG_HOME_ACTIVITY, "onCreate() with previousState");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            getCurrentPosition();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            getCurrentPosition();

        }

        configureMaps();



        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                }
            }
        };


    }

    private void getCurrentPosition() {
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;

        if(network_enabled){

            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location!=null){
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            } else {
                Toast.makeText(HomeActivity.this, getString(R.string.no_current_location), Toast.LENGTH_SHORT).show();
            }
        }
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

        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in current position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
    }

    public void onDirectionsClick(View view) {
        Log.d(TAG_HOME_ACTIVITY, "Boton direceciones pulsado en Maps");
        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void onGetCurrentPositionClick(View view) {
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;

        if(network_enabled){

            location = getLastKnownLocation();

            if(location!=null){
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
        }
        Snackbar.make(findViewById(R.id.map), R.string.getting_current_location, Snackbar.LENGTH_SHORT).show();
        configureMaps();

    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    // Método para mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    // Método para asignar las funciones correspondientes a las opciones
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_view_profile:
                Intent intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                return true;

            case R.id.action_view_historical:
                Intent intentHistorical = new Intent(HomeActivity.this, HistoricalActivity.class);
                startActivity(intentHistorical);
                return true;

            case R.id.action_log_out:
                mAuth.signOut();
                Intent intentLogout = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intentLogout);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}