package com.muei.apm.taxi5.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.muei.apm.taxi5.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RutaActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG_RUTA_ACTIVITY
            = RutaActivity.class.getSimpleName();
    private static GoogleMap mMap;
    private String origen;
    private String destino;

    // user route
    private List<LatLng> routePoints = new ArrayList<LatLng>();
    private LocationManager locationManager;
    private android.location.LocationListener myLocationListener;
    Location currentLocation;
    private double currentLongitude;
    private double currentLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            Log.d(TAG_RUTA_ACTIVITY, "onCreate()");
        } else {
            Log.d(TAG_RUTA_ACTIVITY, "onCreate() with previousState");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta);

        Bundle extras = getIntent().getExtras();
        if (extras == null){
            origen = null;
            destino = null;
        } else {
            origen = extras.getString("ORIGEN");
            destino = extras.getString("DESTINO");
        }

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //Progress bar
        final ProgressDialog pd = new ProgressDialog(RutaActivity.this, R.style.AppCompatAlertDialogStyle);
        pd.setTitle(getString(R.string.buscando_taxista));
        pd.setMessage(getString(R.string.buscando_taxista_mensaje));
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.confirm_driver_arrival), new DialogInterface.OnClickListener() {
            // Set a click listener for progress dialog cancel button
            @Override
            public void onClick(DialogInterface dialog, int which){
                // dismiss the progress dialog
                Toast.makeText(RutaActivity.this, getString(R.string.viaje_en_proceso), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
        pd.show();

    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ruta, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.problemaRuta) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(RutaActivity.this, R.style.AppCompatAlertDialogStyle);
            builder1.setMessage(R.string.cancelar_viaje_iniciado);
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Sí",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(RutaActivity.this, getString(R.string.viaje_cancelado), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RutaActivity.this, HomeActivity.class);
                            startActivity(intent);
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)  {
        mMap = googleMap;

        Geocoder locationAddress = new Geocoder(this, Locale.getDefault());
        List<Address> direccionesOrigen = new ArrayList<Address>();
        List<Address> direccionesDestino = new ArrayList<Address>();
        LatLng sydney = new LatLng(43.333024, -8.410868);
        LatLng barcelona = new LatLng(43.333024, -8.410868);
        String origenStr = "";
        String destinoStr = "";

        try {
            direccionesOrigen = locationAddress.getFromLocationName(origen, 1);
            direccionesDestino = locationAddress.getFromLocationName(destino, 1);

        } catch (Exception ex) {
            Log.e(TAG_RUTA_ACTIVITY, ex.getLocalizedMessage());
        }



        try {
            sydney = new LatLng(direccionesOrigen.get(0).getLatitude(), direccionesOrigen.get(0).getLongitude());
            Double l1 = sydney.latitude;
            Double l2 = sydney.longitude;
            String coordl1 = l1.toString();
            String coordl2 = l2.toString();
            origenStr = coordl1+","+coordl2;

            mMap.setMyLocationEnabled(true);
            barcelona = new LatLng(direccionesDestino.get(0).getLatitude(), direccionesDestino.get(0).getLongitude());

            Double l3 = barcelona.latitude;
            Double l4 = barcelona.longitude;
            String coordl3 = l3.toString();
            String coordl4 = l4.toString();
            destinoStr = coordl3+","+coordl4;

            mMap.addMarker(new MarkerOptions().position(barcelona).title("Marker in Destination"));
        } catch (Exception ex) {
            Log.e(TAG_RUTA_ACTIVITY, ex.getLocalizedMessage());
        }


        //Define list to get all latlng for the route
        List<LatLng> path = new ArrayList();


        //Execute Directions API request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyAy6azXMUfKwGJf-vsVHlFF54q6GQNnJ6M")
                .build();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, origenStr, destinoStr);
        try {
            DirectionsResult res = req.await();

            //Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs != null) {
                    for (int i = 0; i < route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j = 0; j < leg.steps.length; j++) {
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length > 0) {
                                    for (int k = 0; k < step.steps.length; k++) {
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e(TAG_RUTA_ACTIVITY, ex.getLocalizedMessage());
        }

        //Draw the polyline
        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(20);
            mMap.addPolyline(opts);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));


        checkLocation();

    }

    public void checkLocation() {

        String serviceString = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(serviceString);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        myLocationListener = new android.location.LocationListener() {
            public void onLocationChanged(Location locationListener) {

                if (isGPSEnabled(RutaActivity.this)) {
                    if (locationListener != null) {
                        if (ActivityCompat.checkSelfPermission(RutaActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(RutaActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }

                        if (locationManager != null) {
                            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (currentLocation != null) {
                                currentLatitude = currentLocation.getLatitude();
                                currentLongitude = currentLocation.getLongitude();
                            }
                        }
                    }
                } else if (isInternetConnected(RutaActivity.this)) {
                    if (locationManager != null) {
                        currentLocation = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (currentLocation != null) {
                            currentLatitude = currentLocation.getLatitude();
                            currentLongitude = currentLocation.getLongitude();
                        }
                    }
                }

                LatLng latLng = new LatLng(currentLatitude, currentLongitude);

                routePoints.add(latLng);

                Polyline route = mMap.addPolyline(new PolylineOptions()
                        .width(20)
                        .color(Color.RED)
                        .geodesic(false)
                        .zIndex(3));
                route.setPoints(routePoints);


            }

            public void onProviderDisabled(String provider) {

            }

            public void onProviderEnabled(String provider) {

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, myLocationListener);  //  here the min time interval and min distance
    }


    public static boolean isInternetConnected(Context ctx) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // Check if wifi or mobile network is available or not. If any of them is
        // available or connected then it will return true, otherwise false;
        if (wifi != null) {
            if (wifi.isConnected()) {
                return true;
            }
        }
        if (mobile != null) {
            return mobile.isConnected();
        }
        return false;
    }

    public boolean isGPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void onConfirmButtonClick(View view) {

        Intent intent = new Intent(RutaActivity.this, PaymentActivity.class);
        intent.putExtra("ORIGEN", origen);
        intent.putExtra("DESTINO", destino);

        startActivity(intent);
    }

}
