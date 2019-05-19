package com.muei.apm.taxifive.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.muei.apm.taxifive.R;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG_HOME_ACTIVITY = HomeActivity.class.getSimpleName();
    private static GoogleMap mMap;

    private static double longitude = 43.333024;
    private static double latitude = -8.410868;
    LocationManager mLocationManager;
    Marker marker;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;

    private FirebaseAuth mAuth;

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private SharedPreferences.Editor mEditor;

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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    private void configureMaps() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        Address add = null;

        LatLng sydney = new LatLng(latitude, longitude);
        if (marker != null) {
            marker.remove();
        }
        try {
            add = geocoder.getFromLocation(sydney.latitude, sydney.longitude,1).get(0);
        } catch (Exception e) {

        }
        marker = mMap.addMarker(new MarkerOptions().position(sydney).title(add.getAddressLine(0)).draggable(true).visible(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
    }

    public void onDirectionsClick(View view) {
        Log.d(TAG_HOME_ACTIVITY, "Boton direceciones pulsado en Maps");
        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
        intent.putExtra("origenLat", latitude);
        intent.putExtra("origenLong", longitude);
        startActivity(intent);
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        LatLng sydney = new LatLng(latitude, longitude);
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in current position").draggable(true).visible(true));

        // añadimos taxis aleatorios según la ubicación actual del usuario
        mMap.addMarker(new MarkerOptions().position(new LatLng(sydney.latitude+0.0025, sydney.longitude-0.0025)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_taxi)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(sydney.latitude+0.0015, sydney.longitude+0.0015)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_taxi)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(sydney.latitude-0.0015, sydney.longitude-0.0015)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_taxi)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(sydney.latitude+0.0015, sydney.longitude-0.0025)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_taxi)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(sydney.latitude-0.0035, sydney.longitude-0.0015)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_taxi)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(sydney.latitude-0.0035, sydney.longitude+0.0015)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_taxi)));
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
                final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                mEditor = prefs.edit();
                mEditor.clear();
                mEditor.apply();
                mEditor = getSharedPreferences("LOGINGOOGLE", MODE_PRIVATE).edit();
                mEditor.clear();
                mEditor.apply();
                startActivity(intentLogout);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}