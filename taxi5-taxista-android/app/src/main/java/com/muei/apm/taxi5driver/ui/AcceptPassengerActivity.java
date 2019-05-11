package com.muei.apm.taxi5driver.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.muei.apm.taxi5driver.R;

public class AcceptPassengerActivity extends AppCompatActivity implements View.OnTouchListener {

    private SoundPool soundPool;
    private Vibrator vibrator;

    private int soundID;
    boolean loaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_passenger);
    }

    public void onClickAcceptPassenger(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AcceptPassengerActivity.this, R.style.AppCompatAlertDialogStyle);
        // Configura el titulo.
        alertDialogBuilder.setTitle(R.string.aceptacion);
        // Configura el mensaje.
        alertDialogBuilder
                .setMessage(R.string.aceptacion_p)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


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
                        startActivity(intent);
                        AcceptPassengerActivity.this.finish();

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();
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
