package com.muei.apm.taxi5driver.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.muei.apm.taxi5driver.MyTravelRecyclerViewAdapter;
import com.muei.apm.taxi5driver.R;
import com.muei.apm.taxi5driver.TravelFragment;
import com.muei.apm.taxi5driver.api.APIService;
import com.muei.apm.taxi5driver.api.ApiUtils;
import com.muei.apm.taxi5driver.api.RideObject;
import com.muei.apm.taxi5driver.model.Travel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TravelsActiveActivity extends AppCompatActivity implements TravelFragment.OnListFragmentInteractionListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TravelFragment.OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private MyTravelRecyclerViewAdapter adapterTravels;
    private List<Travel> travelList;
    // api
    private APIService mAPIService;
    private final String TAG = TravelFragment.class.getSimpleName();

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private SharedPreferences.Editor mEditor;
    private FloatingActionButton fabUpdate;

    //private  mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels_active);


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        fabUpdate = findViewById(R.id.fabUpdate);

        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAPIService = ApiUtils.getAPIService();
                mAPIService.getRides().enqueue(new Callback<List<RideObject>>() {
                    @Override
                    public void onResponse(Call<List<RideObject>> call, Response<List<RideObject>> response) {
                        if (response.isSuccessful()) {
                            Log.i(TAG, "current user rides get submitted to API." + response.body().toString());
                            if (travelList != null)
                                travelList.clear();
                            else {
                                travelList = new ArrayList<>();
                            }
                            for (int i = 0; i < response.body().size(); i++) {

                                RideObject ride = response.body().get(i);
                                // Añadimos los viajes
                                Travel cuerpo = new Travel(ride.id.longValue(), ride.origin, ride.destination, ride.userid.toString(), ride.ridedate);
                                travelList.add(cuerpo);
                            }
                            recyclerView = findViewById(R.id.list);
                            adapterTravels = new MyTravelRecyclerViewAdapter(travelList, mListener);
                            recyclerView.setAdapter(adapterTravels);

                        }
//                        swipeContainer.setRefreshing(false);

                    }

                    @Override
                    public void onFailure(Call<List<RideObject>> call, Throwable t) {
                        Log.i(TAG, "Unable to get current user rides from API.");
                        System.out.println(t.getMessage());
//                        swipeContainer.setRefreshing(false);

                    }
                });
            }
        });
    }


    @Override
    public void onListFragmentInteraction(Travel item) {
        Intent intent = new Intent(TravelsActiveActivity.this, AcceptPassengerActivity.class);
        intent.putExtra("rideId", item.id);
        startActivity(intent);
    }


    public void onBackPressed() {

    }

    // Método para mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        int positionOfMenuItem = 0;
        MenuItem item = menu.getItem(positionOfMenuItem);
        SpannableString s = new SpannableString(getString(R.string.logout));
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        item.setTitle(s);
        return true;
    }

    // Método para asignar las funciones correspondientes a las opciones
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_log_out:
//                mAuth.signOut();
                Intent intentLogout = new Intent(TravelsActiveActivity.this, MainActivity.class);
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
