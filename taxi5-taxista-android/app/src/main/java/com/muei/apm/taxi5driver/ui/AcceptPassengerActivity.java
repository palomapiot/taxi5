package com.muei.apm.taxi5driver.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.muei.apm.taxi5driver.MyTravelRecyclerViewAdapter;
import com.muei.apm.taxi5driver.R;
import com.muei.apm.taxi5driver.TravelFragment;
import com.muei.apm.taxi5driver.api.APIService;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_passenger);
    }

    public void onClickAcceptPassenger(View view) {

        mAPIService = ApiUtils.getAPIService();
        // TODO: get user id

        mAPIService = ApiUtils.getAPIService();
        // TODO: get user id

        // TODO: !!!!!!!!!!! MOCKEADO, PASAR VALORES DEL VIAJE SELECCIONADO EN LA ACTIVIDAD ANTERIOR Y GUARDAR EL ID DEL TAXISTA
        TaxiIdLogin body = new TaxiIdLogin(new Long(1)); // el id del taxista
        // path: id del viaje
        mAPIService.asignRide(1, body).enqueue(new Callback<RideObject>() {
            @Override
            public void onResponse(Call<RideObject> call, Response<RideObject> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "current user rides get submitted to API." + response.body().toString());

                    RideObject ride = response.body();

                    Intent intent = new Intent(AcceptPassengerActivity.this, InsertCostActivity.class);
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
