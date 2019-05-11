package com.muei.apm.taxifive.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.muei.apm.taxifive.R;
import com.muei.apm.taxifive.api.APIService;
import com.muei.apm.taxifive.api.ApiUtils;
import com.muei.apm.taxifive.api.RideObject;
import com.muei.apm.taxifive.service.RideService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricalActivity extends AppCompatActivity {

    private RideService rideService;
    // api
    private APIService mAPIService;
    private Long currentUserId = null;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private final  String TAG = HistoricalActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        final Activity act = this;

        mAPIService = ApiUtils.getAPIService();
        // TODO: get user id
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        currentUserId = prefs.getLong("currentUserId", 0);

        mAPIService = ApiUtils.getAPIService();
        // TODO: get user id
        mAPIService.getUserRides(currentUserId).enqueue(new Callback<List<RideObject>>() {
            @Override
            public void onResponse(Call<List<RideObject>> call, Response<List<RideObject>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "current user rides get submitted to API." + response.body().toString());

                    ListView lv = findViewById(R.id.lvHistorical);
                    ArrayList<RideObject> routes = new ArrayList<>();
                    for(int i = 0; i < response.body().size(); i++){


                        // Añadimos los viajes
                        RideObject cuerpo = response.body().get(i);


                        // Create the Route objects
                        RideObject rideObject = new RideObject(cuerpo.origin, cuerpo.destination, cuerpo.ridedate, cuerpo.price, cuerpo.userid);

                        routes.add(rideObject);
                    }

                    RouteListAdapter adapter = new RouteListAdapter(act, routes);

                    lv.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<List<RideObject>> call, Throwable t) {
                Log.i(TAG, "Unable to get current user rides from API.");
                System.out.println(t.getMessage());
            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(HistoricalActivity.this, HomeActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }
}
