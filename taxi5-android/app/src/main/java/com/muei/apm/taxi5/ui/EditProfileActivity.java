package com.muei.apm.taxi5.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.muei.apm.taxi5.R;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void onClickConfirmChanges(View view) {
        Toast.makeText(this, "Perfil actualizado!", Toast.LENGTH_SHORT).show();
    }

}