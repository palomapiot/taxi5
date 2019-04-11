package com.muei.apm.taxi5.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.muei.apm.taxi5.R;

import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private TextInputEditText inputDestino;
    private TextInputEditText inputOrigen;
    private Button btnRequestTaxi;

    private static double longitude = 43.333024;
    private static double latitude = -8.410868;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        btnRequestTaxi = findViewById(R.id.btnRequestTaxi);
        inputOrigen = findViewById(R.id.inputOrigen);
        inputDestino = findViewById(R.id.inputDestino);


        try {
            LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Location location;
            if(network_enabled){
                location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(location!=null){
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
            }

            List<Address> addresses = geocoder.getFromLocation(latitude, longitude,1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getAddressLine(1);
            String country = addresses.get(0).getAddressLine(2);
            inputOrigen.setText(address+"\n"+city+"\n"+country);

        } catch (Exception ex) {
            Log.e(SearchActivity.class.getSimpleName(), ex.getLocalizedMessage());
        }



        btnRequestTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(inputDestino.getText().toString().trim())) {
                    Toast.makeText(SearchActivity.this, getString(R.string.enter_destination_address), Toast.LENGTH_SHORT).show();
                } else {
                    final Intent intent = new Intent(SearchActivity.this, TrayectoActivity.class);

                    intent.putExtra("ORIGEN", inputOrigen.getText().toString().trim());
                    intent.putExtra("DESTINO", inputDestino.getText().toString().trim());

                    SearchActivity.this.finish();
                    SearchActivity.this.startActivity(intent);

                }
            }
        });

    }



}
