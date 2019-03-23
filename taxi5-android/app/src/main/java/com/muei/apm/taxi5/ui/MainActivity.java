package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.muei.apm.taxi5.R;

public class MainActivity extends AppCompatActivity {


    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_main);
    }

    public void onClickBtnLogin(View view) {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickBtnRegister(View view) {
        btnRegister = (Button) findViewById(R.id.btnRegister);
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
