package com.muei.apm.taxi5driver.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.muei.apm.taxi5driver.R;

public class AcceptPassengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_passenger);
    }

    public void onClickAcceptPassenger(View view) {
        Intent intent = new Intent(AcceptPassengerActivity.this, InsertCostActivity.class);
        startActivity(intent);
        //Toast.makeText(this, getString(R.string.activity_accept_passenger_accepted), Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onClickCancel(View view) {
        finish();
    }

}
