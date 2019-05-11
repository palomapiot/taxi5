package com.muei.apm.taxi5driver.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.muei.apm.taxi5driver.R;

public class InsertCostActivity extends AppCompatActivity {

    private Button btnFinishTravel;

    boolean loaded = false;
    private SoundPool soundPool;
    private Vibrator vibrator;
    private int soundID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_cost);
    }

    public void onClickSendCost(View view) {
        Snackbar.make(findViewById(R.id.solicitar_pago_btn), R.string.activity_insert_cost_send_price_user, Snackbar.LENGTH_SHORT).show();
        Button pago = findViewById(R.id.solicitar_pago_btn);
        pago.setText(R.string.volver);

    }

    public void onClickFinishTravel(View view) {
        btnFinishTravel = findViewById(R.id.btnLogin);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InsertCostActivity.this, R.style.AppCompatAlertDialogStyle);
        // Configura el titulo.
        alertDialogBuilder.setTitle(R.string.fin);
        alertDialogBuilder
                .setMessage(R.string.aceptacion_p)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        InsertCostActivity.this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
                        // Load the sound
                        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                            @Override
                            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                                       int status) {
                                loaded = true;
                            }
                        });

                        RingtoneManager.getRingtone(InsertCostActivity.this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).play();

                        InsertCostActivity.this.finish();

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();


    }

}
