package com.muei.apm.taxi5driver;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muei.apm.taxi5driver.model.Travel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TravelFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    private MyTravelRecyclerViewAdapter adapterTravels;

    private List<Travel> travelList;

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
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            travelList.add(new Travel("Coruña", "Santiago", "Daniel", Calendar.getInstance()));
            travelList.add(new Travel("Orense", "Vigo", "Carlos", Calendar.getInstance()));
            travelList.add(new Travel("Lugo", "A Coruña", "Rodrigo", Calendar.getInstance()));
            travelList.add(new Travel("Ferrol", "Cecebre", "Belén", Calendar.getInstance()));
            travelList.add(new Travel("Vigo", "Ribeira", "María", Calendar.getInstance()));
            travelList.add(new Travel("Cerceda", "Sanxenxo", "Paloma", Calendar.getInstance()));
            travelList.add(new Travel("Culleredo", "Vigo", "Marta", Calendar.getInstance()));
            travelList.add(new Travel("Mazaricos", "Santiago", "José", Calendar.getInstance()));
            travelList.add(new Travel("Cee", "Santiago", "Samuel", Calendar.getInstance()));
            travelList.add(new Travel("Corcubión", "Coruña", "Raquel", Calendar.getInstance()));
            travelList.add(new Travel("Muros", "Cee", "Lois", Calendar.getInstance()));
            travelList.add(new Travel("Noia", "Ferrol", "Suso", Calendar.getInstance()));


            adapterTravels = new MyTravelRecyclerViewAdapter(travelList, mListener);


            recyclerView.setAdapter(adapterTravels);
        }
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
