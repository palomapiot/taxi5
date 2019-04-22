package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.muei.apm.taxi5.R;
import com.muei.apm.taxi5.api.APIService;
import com.muei.apm.taxi5.api.ApiObject;
import com.muei.apm.taxi5.api.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private Button btnLogin;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private  final  String TAG = RegisterActivity.class.getSimpleName();

    // api
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


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

        mAPIService = ApiUtils.getAPIService();
        ApiObject body = new ApiObject("d2", "dd22", "dd2@email.com", "11212", "micontrasena");
        mAPIService.createUser(body).enqueue(new Callback<ApiObject>() {
            @Override
            public void onResponse(Call<ApiObject> call, Response<ApiObject> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());

                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ApiObject> call, Throwable t) {
                Log.i(TAG, "Unable to submit post to API.");
            }
        });


    }


}
