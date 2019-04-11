package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.muei.apm.taxi5.R;

public class ChangePasswordActivity extends AppCompatActivity {

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
        Toast.makeText(this, getString(R.string.activity_change_password_password_updated), Toast.LENGTH_SHORT).show();
        ChangePasswordActivity.this.finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

}
