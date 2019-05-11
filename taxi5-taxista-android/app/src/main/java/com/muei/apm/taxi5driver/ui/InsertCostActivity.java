package com.muei.apm.taxi5driver.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.muei.apm.taxi5driver.R;
import com.muei.apm.taxi5driver.api.APIService;
import com.muei.apm.taxi5driver.api.ApiUtils;
import com.muei.apm.taxi5driver.api.RideCostObject;
import com.muei.apm.taxi5driver.api.RideObject;
import com.muei.apm.taxi5driver.api.TaxiIdLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertCostActivity extends AppCompatActivity {

    private Button btnFinishTravel;

    //api
    private APIService mAPIService;
    private Long currentUserId = null;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private final  String TAG = AcceptPassengerActivity.class.getSimpleName();

    private Long extraRideId;

    private EditText editCost;
    private String mCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_cost);

        Bundle extras = getIntent().getExtras();
        if (extras == null){
            extraRideId = null;
        } else {
            extraRideId = extras.getLong("aRideId");
        }


        editCost = findViewById(R.id.editText);

    }

    public void onClickSendCost(View view) {

        mAPIService = ApiUtils.getAPIService();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        currentUserId = prefs.getLong("currentUserId", 0);

        mCost = editCost.getText().toString();

        RideCostObject body = new RideCostObject(Float.parseFloat(mCost)); // el id del taxista
        mAPIService.sendRideCost(extraRideId.longValue(), body).enqueue(new Callback<RideObject>() {
            @Override
            public void onResponse(Call<RideObject> call, Response<RideObject> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "get ride details submitted to API." + response.body().toString());

                }
            }

            @Override
            public void onFailure(Call<RideObject> call, Throwable t) {
                Log.i(TAG, "Unable to get current user rides from API.");
                System.out.println(t.getMessage());
            }
        });

        Toast.makeText(this, getString(R.string.activity_insert_cost_send_price_user), Toast.LENGTH_SHORT).show();
    }

    public void onClickFinishTravel(View view) {
        btnFinishTravel = findViewById(R.id.btnLogin);
        Toast.makeText(this, getString(R.string.activity_insert_cost_travel_finished), Toast.LENGTH_SHORT).show();

        // Utilizar finish() para volver a la actividad anterior. NO CREAR un nuevo Intent
        //finish();
        // creamos un nuevo intent para obtener la lista de viajes actualizada
        Intent intent = new Intent(InsertCostActivity.this, TravelsActiveActivity.class);
        startActivity(intent);
    }

}
