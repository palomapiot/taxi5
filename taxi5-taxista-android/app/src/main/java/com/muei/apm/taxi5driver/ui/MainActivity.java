package com.muei.apm.taxi5driver.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.muei.apm.taxi5driver.R;

import static com.muei.apm.taxi5driver.ui.LoginActivity.MY_PREFS_NAME;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if(editor.getLong("currentUserId", 0)!=0){
            startActivity(new Intent(MainActivity.this, TravelsActiveActivity.class));
        }
        final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("EULA", false)) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this,  R.style.AppCompatAlertDialogStyle);
            // Configura el titulo.
            alertDialogBuilder.setTitle(R.string.eula_dialog_title);
            // Configura el mensaje.
            alertDialogBuilder
                    .setMessage(R.string.eula_dialog)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sharedPreferences.edit().putBoolean("EULA", true).commit();
                            //Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            //startActivity(intent);
                        }
                    }).create().show();
        }

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_main);
    }

    public void onClickBtnLogin(View view) {
        btnLogin = findViewById(R.id.btnLogin);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
