package com.muei.apm.taxi5.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muei.apm.taxi5.R;
import com.muei.apm.taxi5.api.APIService;
import com.muei.apm.taxi5.api.ApiUtils;
import com.muei.apm.taxi5.api.RideObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements View.OnTouchListener {

    private String origen;
    private String destino;
    private float coste;
    private float finalPrice;
    boolean loaded = false;
    private SoundPool soundPool;
    private Vibrator vibrator;
    private int soundID;



    private static final String PAGO_ACTIVITY_TAG = PaymentActivity.class.getSimpleName();


    // api
    private APIService mAPIService;
    private Long currentUserId = null;
    private Long lastRideId = null;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private final String TAG = PaymentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            origen = null;
            destino = null;
        } else {
            origen = extras.getString("ORIGEN");
            destino = extras.getString("DESTINO");
        }


        mAPIService = ApiUtils.getAPIService();
        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        lastRideId = prefs.getLong("lastRideId", 0);

        mAPIService.getRideById(lastRideId).enqueue(new Callback<RideObject>() {
            @Override
            public void onResponse(Call<RideObject> call, Response<RideObject> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "ride post submitted to API." + response.body().toString());
                    coste = response.body().cost;

                    TextView precio_label = findViewById(R.id.pago_precio);
                    TextView origen_label = findViewById(R.id.pago_origen);
                    TextView destino_label = findViewById(R.id.pago_destino);

                    precio_label.setText(String.valueOf(coste));
                    origen_label.setText(origen);
                    destino_label.setText(destino);

                    finalPrice = Float.parseFloat(precio_label.getText().toString());
                }
            }

            @Override
            public void onFailure(Call<RideObject> call, Throwable t) {
                Log.i(TAG, "Unable to post ride to API.");
            }
        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public void onBackPressed() {


    }

    public void onClickBtnPay(final View view) {

        // Obtiene instancia a Vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Button button1 = findViewById(R.id.btnMakePayment);
        //Compruebe si dispositivo tiene un vibrador.
        if (vibrator.hasVibrator()) {//Si tiene vibrador
            long tiempo = 500; //en milisegundos
            vibrator.vibrate(tiempo);
        } else {//no tiene
            Log.d("VIBRATOR", "Este dispositivo NO puede vibrar");
        }



        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PaymentActivity.this, R.style.AppCompatAlertDialogStyle);
        // Configura el titulo.
        alertDialogBuilder.setTitle(R.string.payment);
        // Configura el mensaje.
        alertDialogBuilder
                .setMessage(R.string.payment_dialog)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Button botonPagar= findViewById(R.id.btnMakePayment);
                        botonPagar.setVisibility(View.INVISIBLE);

                        PaymentActivity.this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
                        // Load the sound
                        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                            @Override
                            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                                       int status) {
                                loaded = true;
                            }
                        });

                        RingtoneManager.getRingtone(PaymentActivity.this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).play();

                        Snackbar.make(findViewById(R.id.btnMakePayment), R.string.activity_pago_paid, Snackbar.LENGTH_SHORT).show();

                        //Toast.makeText(PaymentActivity.this, getString(R.string.activity_pago_paid), Toast.LENGTH_SHORT).show();

                        // añadir precio del viaje en la bd
                        mAPIService = ApiUtils.getAPIService();
                        // TODO: get user id
                        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                        currentUserId = prefs.getLong("currentUserId", 0);
                        lastRideId = prefs.getLong("lastRideId", 0);

                        mAPIService = ApiUtils.getAPIService();

                        RideObject body = new RideObject(finalPrice);
                        mAPIService.updateRide(lastRideId, body).enqueue(new Callback<RideObject>() {
                            @Override
                            public void onResponse(Call<RideObject> call, Response<RideObject> response) {
                                if (response.isSuccessful()) {
                                    Log.i(TAG, "ride post submitted to API." + response.body().toString());

                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.remove("lastRideId");
                                }
                            }

                            @Override
                            public void onFailure(Call<RideObject> call, Throwable t) {
                                Log.i(TAG, "Unable to post ride to API.");
                            }
                        });

                        //Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                        //startActivity(intent);
                       // PaymentActivity.this.finish();

                        Snackbar.make(findViewById(R.id.btnMakePayment), R.string.activity_pago_paid, Snackbar.LENGTH_INDEFINITE).setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setAction(R.string.undo_string, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Log.i("Snackbar", "Pulsada acción snackbar!");
                                        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        PaymentActivity.this.finish();
                                    }
                                })
                                .show();




                        //Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                        //startActivity(intent);


                        /*AlertDialog.Builder builder1 = new AlertDialog.Builder(PaymentActivity.this, R.style.AppCompatAlertDialogStyle);
                        builder1.setMessage(R.string.agradecimiento);
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ir a inicio",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        dialog.cancel();

                                        PaymentActivity.this.finish();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();*/

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();






    }


    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(PaymentActivity.this, TrayectoActivity.class);
        startActivityForResult(intent, 0);
        return true;
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
}




