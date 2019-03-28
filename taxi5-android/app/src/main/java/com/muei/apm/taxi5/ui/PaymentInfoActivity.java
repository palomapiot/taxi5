package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.muei.apm.taxi5.R;

public class PaymentInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void onClickBtnPagar(View view) {
        Intent intent = new Intent(PaymentInfoActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
