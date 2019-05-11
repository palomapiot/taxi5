package com.muei.apm.taxi5driver.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.muei.apm.taxi5driver.R;
import com.muei.apm.taxi5driver.api.APIService;
import com.muei.apm.taxi5driver.api.ApiUtils;
import com.muei.apm.taxi5driver.api.RideCostObject;
import com.muei.apm.taxi5driver.api.RideObject;

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

    boolean loaded = false;
    private SoundPool soundPool;
    private Vibrator vibrator;
    private int soundID;


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

        Snackbar.make(findViewById(R.id.solicitar_pago_btn), R.string.activity_insert_cost_send_price_user, Snackbar.LENGTH_SHORT).show();
        Button pago = findViewById(R.id.solicitar_pago_btn);
        pago.setText(R.string.volver);
    }

    public void onClickFinishTravel(View view) {
        btnFinishTravel = findViewById(R.id.btnLogin);


//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InsertCostActivity.this, R.style.AppCompatAlertDialogStyle);
//        // Configura el titulo.
//        alertDialogBuilder.setTitle(R.string.fin);
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//
//                        InsertCostActivity.this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
//                        // Load the sound
//                        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//                        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//                            @Override
//                            public void onLoadComplete(SoundPool soundPool, int sampleId,
//                                                       int status) {
//                                loaded = true;
//                            }
//                        });
//
//                        RingtoneManager.getRingtone(InsertCostActivity.this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).play();
//
//                        InsertCostActivity.this.finish();
//
//                    }
//                })
//                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                }).create().show();

        // Utilizar finish() para volver a la actividad anterior. NO CREAR un nuevo Intent
        //finish();
        // creamos un nuevo intent para obtener la lista de viajes actualizada
        Intent intent = new Intent(InsertCostActivity.this, TravelsActiveActivity.class);
        startActivity(intent);
    }

}
