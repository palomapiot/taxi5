package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.muei.apm.taxi5.R;
import com.muei.apm.taxi5.api.APIService;
import com.muei.apm.taxi5.api.ApiObject;
import com.muei.apm.taxi5.api.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private  final  String TAG = ProfileActivity.class.getSimpleName();

    // api
    private APIService mAPIService;
    private Long currentUserId = null;

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


        mAPIService = ApiUtils.getAPIService();
        // TODO: get user id
        mAPIService.getCurrentUserId().enqueue(new Callback<ApiObject>() {
            @Override
            public void onResponse(Call<ApiObject> call, Response<ApiObject> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "current user get submitted to API." + response.body().toString());
                    currentUserId = response.body().id;

                }
            }

            @Override
            public void onFailure(Call<ApiObject> call, Throwable t) {
                Log.i(TAG, "Unable to get current user from API.");
            }
        });


        mAPIService = ApiUtils.getAPIService();
        // TODO: get user id
        mAPIService.getUserDetails(1).enqueue(new Callback<ApiObject>() {
            @Override
            public void onResponse(Call<ApiObject> call, Response<ApiObject> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "current user get submitted to API." + response.body().toString());
                    currentUserId = response.body().id;

                }
            }

            @Override
            public void onFailure(Call<ApiObject> call, Throwable t) {
                Log.i(TAG, "Unable to get current user from API.");
            }
        });



    }

    public void onClickEditProfile(View view) {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void onClickChangePassword(View view) {
        Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

}
