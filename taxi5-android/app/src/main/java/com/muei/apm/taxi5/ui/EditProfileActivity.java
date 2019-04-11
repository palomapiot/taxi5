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

public class EditProfileActivity extends AppCompatActivity {

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
    }

    public void onClickConfirmChanges(View view) {
        Toast.makeText(this, getString(R.string.activity_edit_profile_profile_updated), Toast.LENGTH_SHORT).show();
        EditProfileActivity.this.finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }
}
