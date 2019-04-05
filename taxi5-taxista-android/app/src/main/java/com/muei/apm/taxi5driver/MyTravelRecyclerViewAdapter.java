package com.muei.apm.taxi5driver;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.muei.apm.taxi5driver.TravelFragment.OnListFragmentInteractionListener;
import com.muei.apm.taxi5driver.model.Travel;
import com.muei.apm.taxi5driver.ui.InsertCostActivity;

import java.text.SimpleDateFormat;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Travel} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTravelRecyclerViewAdapter extends RecyclerView.Adapter<MyTravelRecyclerViewAdapter.ViewHolder> {

    private final List<Travel> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTravelRecyclerViewAdapter(List<Travel> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_travel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvUser.setText(holder.mItem.getUser());
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        holder.tvDate.setText(format1.format(holder.mItem.getDate().getTime()));
        holder.tvOrigin.setText(holder.mItem.getOrigin());
        holder.tvDestination.setText(holder.mItem.getDestination());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView tvUser;
        public final TextView tvDate;
        public final TextView tvOrigin;
        public final TextView tvDestination;
        public Travel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvUser = view.findViewById(R.id.tvUser);
            tvDate = view.findViewById(R.id.tvDate);
            tvOrigin = view.findViewById(R.id.tvOrigin);
            tvDestination = view.findViewById(R.id.tvDestination);

        }


        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", tvUser=" + tvUser +
                    ", tvDate=" + tvDate +
                    ", tvOrigin=" + tvOrigin +
                    ", tvDestination=" + tvDestination +
                    ", mItem=" + mItem +
                    '}';
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), InsertCostActivity.class);
            startActivity(v.getContext(), intent, null);
            Log.d(TAG, "onClick " + getPosition() + " " + mItem);
            Toast.makeText(v.getContext(), "Pago", Toast.LENGTH_SHORT).show();
        }
    }
}
