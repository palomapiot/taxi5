package com.muei.apm.taxi5driver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class InsertCostActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_cost);

    }

    public void onClickSendCost(View view) {
        Toast.makeText(this, getString(R.string.send_price_user), Toast.LENGTH_SHORT).show();
    }

}
