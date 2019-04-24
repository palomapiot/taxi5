package com.muei.apm.taxi5.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.muei.apm.taxi5.R;
import com.muei.apm.taxi5.api.RideObject;

import java.util.ArrayList;

public class RouteListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<RideObject> items;

    public RouteListAdapter(Activity activity, ArrayList<RideObject> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }


    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_route, null);
        }

        RideObject dir = items.get(position);

        TextView date = v.findViewById(R.id.route_date);
        date.setText(dir.ridedate);

        TextView origin = v.findViewById(R.id.origin_location);
        origin.setText(dir.origin);

        TextView destination = v.findViewById(R.id.destination_location);
        destination.setText(dir.destination);

        return v;
    }
}
