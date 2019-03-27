package com.muei.apm.taxi5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }

    public void onClickConfirmPassword(View view) {
        Toast.makeText(this, "Contraseña actualizada!", Toast.LENGTH_SHORT).show();
    }

}
