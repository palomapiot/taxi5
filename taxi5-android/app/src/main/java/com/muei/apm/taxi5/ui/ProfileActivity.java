package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.muei.apm.taxi5.R;

public class ProfileActivity extends AppCompatActivity {

    private Button buttonPass;

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

        buttonPass = (Button) findViewById(R.id.btnChangePassword);

        Boolean loginGoogle = true;

        //si login con google
        final SharedPreferences sharedPreferences = getSharedPreferences("LOGINGOOGLE", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("LOGINGOOGLE", loginGoogle)) {
            buttonPass.setVisibility(View.INVISIBLE);
        } else {
            buttonPass.setVisibility(View.VISIBLE);
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

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

}
