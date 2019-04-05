package com.muei.apm.taxi5driver.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.muei.apm.taxi5driver.R;

public class InsertCostActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_cost);

    }

    public void onClickSendCost(View view) {
        Toast.makeText(this, getString(R.string.activity_insert_cost_send_price_user), Toast.LENGTH_SHORT).show();
    }

}
