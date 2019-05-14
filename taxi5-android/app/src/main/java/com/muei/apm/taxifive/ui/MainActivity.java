package com.muei.apm.taxifive.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.muei.apm.taxifive.R;

import static com.muei.apm.taxifive.ui.LoginActivity.MY_PREFS_NAME;

public class MainActivity extends AppCompatActivity {


    private Button btnLogin;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //final SharedPreferences sharedPreferences = getActi().getPreferences(getActivity().getApplicationContext().MODE_PRIVATE);
        SharedPreferences editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (editor.getLong("currentUserId", 0) != 0) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }


        final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("EULA", false)) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle);
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


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onClickBtnLogin(View view) {
        btnLogin = findViewById(R.id.btnLogin);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickBtnRegister(View view) {
        btnRegister = findViewById(R.id.btnRegister);

        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);


    }


}
