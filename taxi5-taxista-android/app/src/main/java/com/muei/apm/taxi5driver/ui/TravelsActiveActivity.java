package com.muei.apm.taxi5driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
        Intent intent = new Intent(TravelsActiveActivity.this, AcceptPassengerActivity.class);
        intent.putExtra("rideId", item.id);
        startActivity(intent);
    }
}
