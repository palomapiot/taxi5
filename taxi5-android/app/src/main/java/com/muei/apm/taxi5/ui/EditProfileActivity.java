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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class EditProfileActivity extends AppCompatActivity {

    private  final  String TAG = EditProfileActivity.class.getSimpleName();
    // api
    private APIService mAPIService;
    private Long currentUserId = null;
    public static final String MY_PREFS_NAME = "MyPrefsFile";


    private EditText mNameView;
    private EditText mSurnameView;
    private EditText mEmailView;
    private EditText mPhoneView;
    private ImageView mImageView;


    private String mEmail;
    private String mFirstName;
    private String mLastName;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }


        // Set up the login form.
        mNameView = findViewById(R.id.editText);
        mSurnameView = findViewById(R.id.editText1);
        mEmailView = findViewById(R.id.editText2);
        mPhoneView = findViewById(R.id.editText3);
        mImageView = findViewById(R.id.imageView);

        final SharedPreferences sharedPreferences = getSharedPreferences("LOGINGOOGLE", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("LOGINGOOGLE", true)) {
            String email = sharedPreferences.getString("EMAIL", "");
            mEmailView.setText(email);
            mEmailView.setEnabled(false);
            String[] name = sharedPreferences.getString("NAME", "").split(" ", 2);
            String firstname = name[0];
            String lastname = name[1];
            mNameView.setText(firstname);
            mSurnameView.setText(lastname);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            Glide.with(getApplicationContext()).load(acct.getPhotoUrl())
                    .thumbnail(0.5f)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImageView);
        } else {
            // TODO: get user id
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            currentUserId = prefs.getLong("currentUserId", 0);

            mAPIService = ApiUtils.getAPIService();
            // TODO: get user id
            mAPIService.getUserDetails(currentUserId).enqueue(new Callback<ApiObject>() {
                @Override
                public void onResponse(Call<ApiObject> call, Response<ApiObject> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "current user get submitted to API." + response.body().toString());
                        currentUserId = response.body().id;

                        mNameView.setText(response.body().firstName);
                        mSurnameView.setText(response.body().lastName);
                        mEmailView.setText(response.body().email);
                        mPhoneView.setText(response.body().phone);
                    }
                }

                @Override
                public void onFailure(Call<ApiObject> call, Throwable t) {
                    Log.i(TAG, "Unable to get current user from API.");
                }
            });
        }

    }

    public void onClickConfirmChanges(View view) {
        Toast.makeText(this, getString(R.string.activity_edit_profile_profile_updated), Toast.LENGTH_SHORT).show();
        // TODO guardar cambios

        mFirstName = mNameView.getText().toString();
        mLastName = mSurnameView.getText().toString();
        mEmail = mEmailView.getText().toString();
        mPhone = mPhoneView.getText().toString();


        ApiObject body = new ApiObject(mFirstName, mLastName, mEmail, mPhone);
        mAPIService.updateUserDetails(currentUserId, body).enqueue(new Callback<ApiObject>() {
            @Override
            public void onResponse(Call<ApiObject> call, Response<ApiObject> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "user update put submitted to API." + response.body().toString());
                    currentUserId = response.body().id;

                    mNameView.setText(response.body().firstName);
                    mSurnameView.setText(response.body().lastName);
                    mEmailView.setText(response.body().email);
                    mPhoneView.setText(response.body().phone);

                }
            }

            @Override
            public void onFailure(Call<ApiObject> call, Throwable t) {
                Log.i(TAG, "Unable to get current user from API.");
            }
        });

        EditProfileActivity.this.finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }
}
