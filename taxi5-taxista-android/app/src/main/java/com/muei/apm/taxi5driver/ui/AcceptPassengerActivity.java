package com.muei.apm.taxi5driver.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.muei.apm.taxi5driver.R;
import com.muei.apm.taxi5driver.api.APIService;
import com.muei.apm.taxi5driver.api.ApiObject;
import com.muei.apm.taxi5driver.api.ApiUtils;
import com.muei.apm.taxi5driver.api.RideObject;
import com.muei.apm.taxi5driver.api.TaxiIdLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptPassengerActivity extends AppCompatActivity implements View.OnTouchListener {

    private SoundPool soundPool;
    private Vibrator vibrator;

    private int soundID;
    boolean loaded = false;


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

                    final RideObject ride = response.body();

                    Intent intent = new Intent(AcceptPassengerActivity.this, InsertCostActivity.class);

                    intent.putExtra("aRideId", ride.id);
                    startActivity(intent);
                    AcceptPassengerActivity.this.finish();

                }
            }

            @Override
            public void onFailure(Call<RideObject> call, Throwable t) {
                Log.i(TAG, "Unable to get current user rides from API.");
                System.out.println(t.getMessage());
            }
        });





    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Getting the user sound settings
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            float actualVolume = (float) audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = (float) audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = actualVolume / maxVolume;
            // Is the sound loaded already?
            if (loaded) {
                soundPool.play(soundID, volume, volume, 1, 0, 1f);
                Log.e("Test", "Played sound");
            }
        }
        return false;
    }

    public void onClickCancel(View view) {
        finish();
    }

}
