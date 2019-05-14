package com.muei.apm.taxifive.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
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
import com.muei.apm.taxifive.R;
import com.muei.apm.taxifive.api.APIService;
import com.muei.apm.taxifive.api.ApiUtils;
import com.muei.apm.taxifive.api.RideObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrayectoActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final String TAG_TRAYECTO_ACTIVITY
            = TrayectoActivity.class.getSimpleName();
    private static GoogleMap mMap;
    private String origen;
    private String destino;


    // api
    private APIService mAPIService;
    private Long currentUserId = null;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private final  String TAG = TrayectoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            Log.d(TAG_TRAYECTO_ACTIVITY, "onCreate()");
        } else {
            Log.d(TAG_TRAYECTO_ACTIVITY, "onCreate() with previousState");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trayecto);

        Bundle extras = getIntent().getExtras();
        if (extras == null){
            origen = null;
            destino = null;
        } else {
            origen = extras.getString("ORIGEN");
            destino = extras.getString("DESTINO");
        }

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);


        //Toolbar myToolbar = findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
            Log.e(TAG_TRAYECTO_ACTIVITY, ex.getLocalizedMessage());
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

            Marker destino = mMap.addMarker(new MarkerOptions().position(barcelona).title(direccionesDestino.get(0).getAddressLine(0)));



            //destino.showInfoWindow();


        } catch (Exception ex) {
            Log.e(TAG_TRAYECTO_ACTIVITY, ex.getLocalizedMessage());
        }

        if ((origenStr == "") || (destinoStr == "")) {
            AlertDialog.Builder ad = new AlertDialog.Builder(TrayectoActivity.this, R.style.AppCompatAlertDialogStyle);
            ad.setTitle("Imposible calcular la ruta");
            ad.setMessage("La ruta establecida no es válida. Introduzca origen y destino de nuevo.");
            ad.setCancelable(false);
            ad.setPositiveButton("Volver a buscar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface ad, int id) {
                    Intent intentSearch = new Intent(TrayectoActivity.this, SearchActivity.class);
                    startActivity(intentSearch);
                }
            });
            ad.show();
        }

        //Define list to get all latlng for the route
        List<LatLng> path = new ArrayList();

        //Execute Directions API request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyAy6azXMUfKwGJf-vsVHlFF54q6GQNnJ6M")
                .build();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, origenStr, destinoStr);
        req.alternatives(true);

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
                // si hay más de una ruta
                if (1 < res.routes.length) {
                    // rutas alternativas
                    // recorremos el array de routes desde la posición 1 (es decir, la segunda)
                    for (int m = 1; m < res.routes.length; m++) {
                        //Define list to get all latlng for the alternative routes
                        List<LatLng> altPath = new ArrayList();

                        DirectionsRoute altRoute = res.routes[m];

                        if (altRoute.legs != null) {
                            for (int n = 0; n < altRoute.legs.length; n++) {
                                DirectionsLeg altLeg = altRoute.legs[n];
                                if (altLeg.steps != null) {
                                    for (int h = 0; h < altLeg.steps.length; h++) {
                                        DirectionsStep altStep = altLeg.steps[h];
                                        if (altStep.steps != null && altStep.steps.length > 0) {
                                            for (int l = 0; l < altStep.steps.length; l++) {
                                                DirectionsStep altStep1 = altStep.steps[l];
                                                EncodedPolyline altPoints1 = altStep1.polyline;
                                                if (altPoints1 != null) {
                                                    //Decode polyline and add points to list of route coordinates
                                                    List<com.google.maps.model.LatLng> altCoords1 = altPoints1.decodePath();
                                                    for (com.google.maps.model.LatLng altCord1 : altCoords1) {
                                                        altPath.add(new LatLng(altCord1.lat, altCord1.lng));
                                                    }
                                                }
                                            }
                                        } else {
                                            EncodedPolyline altPoints = altStep.polyline;
                                            if (altPoints != null) {
                                                //Decode polyline and add points to list of route coordinates
                                                List<com.google.maps.model.LatLng> altCoords = altPoints.decodePath();
                                                for (com.google.maps.model.LatLng altCoord : altCoords) {
                                                    altPath.add(new LatLng(altCoord.lat, altCoord.lng));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //Draw the polyline for alt routes
                        if (altPath.size() > 0) {
                            PolylineOptions opts2 = new PolylineOptions().addAll(altPath).color(Color.GRAY).width(20);
                            Polyline mainLine2 = mMap.addPolyline(opts2);
                        }
                    }
                }



            } else {
                AlertDialog.Builder ad = new AlertDialog.Builder(TrayectoActivity.this, R.style.AppCompatAlertDialogStyle);
                ad.setTitle("Imposible calcular la ruta");
                ad.setMessage("La ruta establecida es imposible realizarla en taxi. Introduzca una ruta válida.");
                ad.setCancelable(false);
                ad.setPositiveButton("Volver a buscar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface ad, int id) {
                        Intent intentSearch = new Intent(TrayectoActivity.this, SearchActivity.class);
                        startActivity(intentSearch);
                    }
                });
                ad.show();
            }
        } catch (Exception ex) {
            Log.e(TAG_TRAYECTO_ACTIVITY, ex.getLocalizedMessage());
        }


        //Draw the polyline
        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(20)
                    .zIndex(2000).geodesic(true);
            Polyline mainLine =  mMap.addPolyline(opts);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for(int i = 0; i < mainLine.getPoints().size(); i++){
                builder.include(mainLine.getPoints().get(i));
            }

            LatLngBounds bounds = builder.build();

            //mMap.addMarker(new MarkerOptions().position(bounds.getCenter()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_infofilter)));

            //Marker mainRoute = mMap.addMarker(new MarkerOptions().position(centro).title("Ruta principal").icon(BitmapDescriptorFactory.defaultMarker()));
            //mainRoute.showInfoWindow();
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

        float zoomLevel = 12.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barcelona, zoomLevel));

    }













    public void onConfirmButtonClick(View view) {

        mAPIService = ApiUtils.getAPIService();
        // TODO: get user id
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        currentUserId = prefs.getLong("currentUserId", 0);

        mAPIService = ApiUtils.getAPIService();

        RideObject body = new RideObject(origen, destino, System.currentTimeMillis(), currentUserId);
        mAPIService.addRide(body).enqueue(new Callback<RideObject>() {
            @Override
            public void onResponse(Call<RideObject> call, Response<RideObject> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "ride post submitted to API." + response.body().toString());
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putLong("lastRideId", response.body().id);
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<RideObject> call, Throwable t) {
                Log.i(TAG, "Unable to post ride to API.");
            }
        });


        Intent intent = new Intent(TrayectoActivity.this, RutaActivity.class);
        intent.putExtra("ORIGEN", origen);
        intent.putExtra("DESTINO", destino);

        startActivity(intent);
    }

    public void onCancelButtonClick(View view) {
        Log.d(TAG_TRAYECTO_ACTIVITY, "Boton para cambio de actividad pulsado en Trayecto");

        AlertDialog.Builder builder1 = new AlertDialog.Builder(TrayectoActivity.this, R.style.AppCompatAlertDialogStyle);
        builder1.setMessage(R.string.cancelar_viaje);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Sí",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(TrayectoActivity.this, getString(R.string.viaje_cancelado), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TrayectoActivity.this, HomeActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trayecto, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cancelarViajeSinIniciar) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(TrayectoActivity.this, R.style.AppCompatAlertDialogStyle);
            builder1.setMessage(R.string.cancelar_viaje);
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Sí",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(TrayectoActivity.this, getString(R.string.viaje_cancelado), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TrayectoActivity.this, HomeActivity.class);
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

}
