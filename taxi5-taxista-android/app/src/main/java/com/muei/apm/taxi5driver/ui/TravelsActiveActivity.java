package com.muei.apm.taxi5driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.muei.apm.taxi5driver.R;
import com.muei.apm.taxi5driver.TravelFragment;
import com.muei.apm.taxi5driver.model.Travel;

public class TravelsActiveActivity extends AppCompatActivity implements TravelFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels_active);
    }


    @Override
    public void onListFragmentInteraction(Travel item) {
        Intent intent = new Intent(this, InsertCostActivity.class);
        startActivity(intent);
    }
}
