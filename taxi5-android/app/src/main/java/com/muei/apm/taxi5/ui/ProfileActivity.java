package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.muei.apm.taxi5.R;
import com.muei.apm.taxi5.api.APIService;
import com.muei.apm.taxi5.api.ApiObject;
import com.muei.apm.taxi5.api.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private Button buttonPass;
    private APIService mAPIService;
    private Long currentUserId = null;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private TextView tvName;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        buttonPass = findViewById(R.id.btnChangePassword);

        tvName = findViewById(R.id.tvName);
        imageView = findViewById(R.id.ivProfile);

        Boolean loginGoogle = true;

        //si login con google
        final SharedPreferences sharedPreferences = getSharedPreferences("LOGINGOOGLE", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("LOGINGOOGLE", loginGoogle)) {
            buttonPass.setVisibility(View.INVISIBLE);
            tvName = findViewById(R.id.tvName);
            tvName.setText(sharedPreferences.getString("NAME", ""));
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            Glide.with(getApplicationContext()).load(acct.getPhotoUrl())
                    .thumbnail(0.5f)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        } else {
            buttonPass.setVisibility(View.VISIBLE);
            mAPIService = ApiUtils.getAPIService();
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            currentUserId = prefs.getLong("currentUserId", 0);
            // TODO: get user id
            mAPIService.getUserDetails(currentUserId).enqueue(new Callback<ApiObject>() {
                @Override
                public void onResponse(Call<ApiObject> call, Response<ApiObject> response) {
                    if (response.isSuccessful()) {
                        Log.i("API", "current user get submitted to API." + response.body().toString());
                        currentUserId = response.body().id;
                        tvName.setText(response.body().firstName + " " + response.body().lastName);
                    }
                }

                @Override
                public void onFailure(Call<ApiObject> call, Throwable t) {
                    Log.i("API", "Unable to get current user from API.");
                }
            });
        }


    }

    public void onClickEditProfile(View view) {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void onClickChangePassword(View view) {

        Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

}
