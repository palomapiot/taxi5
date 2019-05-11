package com.muei.apm.taxi5driver.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.muei.apm.taxi5driver.R;
import com.muei.apm.taxi5driver.api.APIService;
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
        mAPIService.getRideById(extraRideId.longValue()).enqueue(new Callback<RideObject>() {
            @Override
            public void onResponse(Call<RideObject> call, Response<RideObject> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "get ride details submitted to API." + response.body().toString());
                    // TODO: recuperar nombre y apellidos usuarios, no id
                    tvUser.setText(response.body().userid.toString());

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


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AcceptPassengerActivity.this, R.style.AppCompatAlertDialogStyle);
                    // Configura el titulo.
                    alertDialogBuilder.setTitle(R.string.aceptacion);
                    // Configura el mensaje.
                    alertDialogBuilder
                            .setMessage(R.string.aceptacion_p)
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    //Compruebe si dispositivo tiene un vibrador.
                                    if (vibrator.hasVibrator()) {//Si tiene vibrador
                                        long tiempo = 500; //en milisegundos
                                        vibrator.vibrate(tiempo);
                                    } else {//no tiene
                                        Log.d("VIBRATOR", "Este dispositivo NO puede vibrar");
                                    }

                                    AcceptPassengerActivity.this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
                                    // Load the sound
                                    soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                                    soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                        @Override
                                        public void onLoadComplete(SoundPool soundPool, int sampleId,
                                                                   int status) {
                                            loaded = true;
                                        }
                                    });

                                    RingtoneManager.getRingtone(AcceptPassengerActivity.this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).play();

                                    Intent intent = new Intent(AcceptPassengerActivity.this, InsertCostActivity.class);

                                    intent.putExtra("aRideId", ride.id);
                                    startActivity(intent);
                                    AcceptPassengerActivity.this.finish();

                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).create().show();
                    //Toast.makeText(this, getString(R.string.activity_accept_passenger_accepted), Toast.LENGTH_SHORT).show();
//                    finish();

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
