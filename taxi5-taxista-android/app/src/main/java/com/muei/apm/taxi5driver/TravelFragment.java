package com.muei.apm.taxi5driver;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muei.apm.taxi5driver.api.APIService;
import com.muei.apm.taxi5driver.api.ApiUtils;
import com.muei.apm.taxi5driver.api.RideObject;
import com.muei.apm.taxi5driver.model.Travel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TravelFragment extends Fragment {

    private SwipeRefreshLayout swipeContainer;

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    private MyTravelRecyclerViewAdapter adapterTravels;

    private List<Travel> travelList;

    // api
    private APIService mAPIService;
    private Long currentUserId = null;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private final  String TAG = TravelFragment.class.getSimpleName();


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TravelFragment() {
        travelList = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_list, container, false);


        // Set the adapter
        recyclerView = view.findViewById(R.id.list);
        if (recyclerView != null) {
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            //travelList.add(new Travel("Coruña", "Santiago", "Daniel", Calendar.getInstance()));

            mAPIService = ApiUtils.getAPIService();
            mAPIService.getRides().enqueue(new Callback<List<RideObject>>() {
                @Override
                public void onResponse(Call<List<RideObject>> call, Response<List<RideObject>> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "current user rides get submitted to API." + response.body().toString());
                        for (int i = 0; i < response.body().size(); i++) {

                            RideObject ride = response.body().get(i);
                            // Añadimos los viajes
                            Travel cuerpo = new Travel(ride.id.longValue(), ride.origin, ride.destination, ride.userid.toString(), ride.ridedate);
                            travelList.add(cuerpo);
                        }

                        adapterTravels = new MyTravelRecyclerViewAdapter(travelList, mListener);
                        recyclerView.setAdapter(adapterTravels);

                    }
                }

                @Override
                public void onFailure(Call<List<RideObject>> call, Throwable t) {
                    Log.i(TAG, "Unable to get current user rides from API.");
                    System.out.println(t.getMessage());
                }
            });


        }

        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                mAPIService = ApiUtils.getAPIService();
                mAPIService.getRides().enqueue(new Callback<List<RideObject>>() {
                    @Override
                    public void onResponse(Call<List<RideObject>> call, Response<List<RideObject>> response) {
                        if (response.isSuccessful()) {
                            Log.i(TAG, "current user rides get submitted to API." + response.body().toString());
                            travelList.clear();
                            for (int i = 0; i < response.body().size(); i++) {

                                RideObject ride = response.body().get(i);
                                // Añadimos los viajes
                                Travel cuerpo = new Travel(ride.id.longValue(), ride.origin, ride.destination, ride.userid.toString(), ride.ridedate);
                                travelList.add(cuerpo);
                            }

                            adapterTravels = new MyTravelRecyclerViewAdapter(travelList, mListener);
                            recyclerView.setAdapter(adapterTravels);

                        }
                        swipeContainer.setRefreshing(false);

                    }

                    @Override
                    public void onFailure(Call<List<RideObject>> call, Throwable t) {
                        Log.i(TAG, "Unable to get current user rides from API.");
                        System.out.println(t.getMessage());
                        swipeContainer.setRefreshing(false);

                    }
                });
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Travel item);
    }
}
