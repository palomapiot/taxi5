package com.muei.apm.taxi5driver.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.muei.apm.taxi5driver.MyTravelRecyclerViewAdapter;
import com.muei.apm.taxi5driver.R;
import com.muei.apm.taxi5driver.TravelFragment;
import com.muei.apm.taxi5driver.api.APIService;
import com.muei.apm.taxi5driver.api.ApiObject;
import com.muei.apm.taxi5driver.api.ApiUtils;
import com.muei.apm.taxi5driver.api.LoginObject;
import com.muei.apm.taxi5driver.api.RideObject;
import com.muei.apm.taxi5driver.api.TaxiIdLogin;
import com.muei.apm.taxi5driver.model.Travel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptPassengerActivity extends AppCompatActivity {

    // api
    private APIService mAPIService;
    private Long currentUserId = null;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private final  String TAG = AcceptPassengerActivity.class.getSimpleName();
    private Long extraRideId;

    private TextView tvUser;
    private TextView tvOrigin;
    private TextView tvDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_passenger);

        Bundle extras = getIntent().getExtras();
        if (extras == null){
            extraRideId = null;
        } else {
            extraRideId = extras.getLong("rideId");
        }

        tvUser = findViewById(R.id.textView2);
        tvOrigin = findViewById(R.id.textView3);
        tvDestination = findViewById(R.id.textView4);

        mAPIService = ApiUtils.getAPIService();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        currentUserId = prefs.getLong("currentUserId", 0);
        mAPIService.getRideById(extraRideId.longValue()).enqueue(new Callback<RideObject>() {
            @Override
            public void onResponse(Call<RideObject> call, Response<RideObject> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "get ride details submitted to API." + response.body().toString());
                    // TODO: recuperar nombre y apellidos usuarios, no id


                    mAPIService.getUserDetails(currentUserId).enqueue(new Callback<ApiObject>() {
                        @Override
                        public void onResponse(Call<ApiObject> call, Response<ApiObject> response) {
                            if (response.isSuccessful()) {
                                Log.i(TAG, "get user details submitted to API." + response.body().toString());

                                ApiObject usuario = response.body();
                                tvUser.setText(usuario.firstName + ' ' + usuario.lastName);
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiObject> call, Throwable t) {
                            Log.i(TAG, "Unable to get current user details from API.");
                            System.out.println(t.getMessage());
                        }
                    });




                    tvOrigin.setText(response.body().origin);
                    tvDestination.setText(response.body().destination);

                }
            }

            @Override
            public void onFailure(Call<RideObject> call, Throwable t) {
                Log.i(TAG, "Unable to get current user rides from API.");
                System.out.println(t.getMessage());
            }
        });

    }

    public void onClickAcceptPassenger(View view) {
        mAPIService = ApiUtils.getAPIService();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        currentUserId = prefs.getLong("currentUserId", 0);

        TaxiIdLogin body = new TaxiIdLogin(currentUserId); // el id del taxista
        mAPIService.asignRide(extraRideId, body).enqueue(new Callback<RideObject>() {
            @Override
            public void onResponse(Call<RideObject> call, Response<RideObject> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "current user rides get submitted to API." + response.body().toString());

                    RideObject ride = response.body();

                    Intent intent = new Intent(AcceptPassengerActivity.this, InsertCostActivity.class);

                    intent.putExtra("aRideId", ride.id);
                    startActivity(intent);
                    //Toast.makeText(this, getString(R.string.activity_accept_passenger_accepted), Toast.LENGTH_SHORT).show();
                    finish();

                }
            }

            @Override
            public void onFailure(Call<RideObject> call, Throwable t) {
                Log.i(TAG, "Unable to get current user rides from API.");
                System.out.println(t.getMessage());
            }
        });



    }

    public void onClickCancel(View view) {
        finish();
    }

}
