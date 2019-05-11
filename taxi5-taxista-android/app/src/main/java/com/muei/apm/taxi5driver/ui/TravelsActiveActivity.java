package com.muei.apm.taxi5driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.muei.apm.taxi5driver.MyTravelRecyclerViewAdapter;
import com.muei.apm.taxi5driver.R;
import com.muei.apm.taxi5driver.TravelFragment;
import com.muei.apm.taxi5driver.api.APIService;
import com.muei.apm.taxi5driver.model.Travel;

import java.util.List;


public class TravelsActiveActivity extends AppCompatActivity implements TravelFragment.OnListFragmentInteractionListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TravelFragment.OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private MyTravelRecyclerViewAdapter adapterTravels;
    private List<Travel> travelList;
    // api
    private APIService mAPIService;
    private final String TAG = TravelFragment.class.getSimpleName();

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

    public void onBackPressed() {

    }
}
