package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.muei.apm.taxi5.R;
import com.muei.apm.taxi5.api.APIService;
import com.muei.apm.taxi5.api.ApiObject;
import com.muei.apm.taxi5.api.ApiUtils;
import com.muei.apm.taxi5.api.BooleanObject;
import com.muei.apm.taxi5.api.PsswdObject;
import com.muei.apm.taxi5.api.UpdatePsswdObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private  final  String TAG = ChangePasswordActivity.class.getSimpleName();
    // api
    private APIService mAPIService;
    private Long currentUserId = null;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private EditText mActualPsswdView;
    private EditText mNewPsswdView;
    private EditText mmConfirmNewPsswdView;


    private String mActualPsswd;
    private String mNewPsswd;
    private String mConfirmNewPsswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onClickConfirmPassword(View view) {
        // TODO guardar constraseña

        boolean cancel = false;
        View focusView = null;

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        currentUserId = prefs.getLong("currentUserId", 0);


        mActualPsswdView = findViewById(R.id.editText4);
        mNewPsswdView = findViewById(R.id.editText5);
        mmConfirmNewPsswdView = findViewById(R.id.editText6);

        mActualPsswd = mActualPsswdView.getText().toString();
        mNewPsswd = mNewPsswdView.getText().toString();
        mConfirmNewPsswd = mmConfirmNewPsswdView.getText().toString();

        //Check for a password not null
        if (TextUtils.isEmpty(mNewPsswd)) {
            mNewPsswdView.setError(getString(R.string.error_incorrect_password));
            focusView = mNewPsswdView;
            cancel = true;
        } else if (!mNewPsswd.equals(mConfirmNewPsswd)) { // check the same password
            mmConfirmNewPsswdView.setError(getString(R.string.error_incorrect_password));
            focusView = mmConfirmNewPsswdView;
            cancel = true;
        }

        //Check for a password not null
        if (TextUtils.isEmpty(mNewPsswd)) {
            mNewPsswdView.setError(getString(R.string.error_incorrect_password));
            focusView = mNewPsswdView;
            cancel = true;
        } else if (!mConfirmNewPsswd.equals(mNewPsswd)) {// check the same password
            mNewPsswdView.setError(getString(R.string.error_incorrect_password));
            focusView = mNewPsswdView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            mAPIService = ApiUtils.getAPIService();

            // TODO: Comprobar que la contraseña actual es la que está en la base de datos
            PsswdObject body = new PsswdObject(mActualPsswd);
            mAPIService.checkPsswd(currentUserId, body).enqueue(new Callback<BooleanObject>() {
                @Override
                public void onResponse(Call<BooleanObject> call, Response<BooleanObject> response) {
                    Log.i(TAG, "respuesta" + response);
                    if (response.isSuccessful()) {
                        Log.i(TAG, "passwd matches." + response.body());

                        UpdatePsswdObject contrasinal = new UpdatePsswdObject(mNewPsswd);
                        mAPIService.updatePsswd(currentUserId, contrasinal).enqueue(new Callback<ApiObject>() {
                            @Override
                            public void onResponse(Call<ApiObject> call, Response<ApiObject> response) {
                                if (response.isSuccessful()) {
                                    Log.i(TAG, "user update put submitted to API." + response.body().toString());
                                    currentUserId = response.body().id;
                                    Toast.makeText(getApplicationContext(), getString(R.string.activity_change_password_password_updated), Toast.LENGTH_SHORT).show();
                                    ChangePasswordActivity.this.finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<ApiObject> call, Throwable t) {
                                Log.i(TAG, "Unable to get current user from API.");
                                Toast.makeText(getApplicationContext(), getString(R.string.activity_change_password_password_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<BooleanObject> call, Throwable t) {
                    Log.i(TAG, "passwd doest match.");
                    mActualPsswdView.setError(getString(R.string.error_incorrect_password));

                }
            });



        }


    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

}
