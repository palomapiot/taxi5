package com.muei.apm.taxifive.service;

import android.content.Context;
import android.os.AsyncTask;

import com.muei.apm.taxifive.dao.RideDao;
import com.muei.apm.taxifive.db.AppDatabase;
import com.muei.apm.taxifive.entities.Ride;

import java.util.ArrayList;
import java.util.List;

public class RideService {

    private RideDao rideDao;

    public RideService(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        rideDao = db.rideDao();
    }

    public List<Ride> getHistorialByUserId(long userId) {
        List<Ride> result = new ArrayList<>();

        for (Ride ride : rideDao.findAllByUserId(userId)) {
            result.add(new Ride(ride));
        }

        return result;
    }

    public void insert(Ride ride) {
        new insertAsyncTask(rideDao).execute(ride);
    }

    private static class insertAsyncTask extends AsyncTask<Ride, Void, Void> {

        private RideDao mAsyncTaskDao;

        insertAsyncTask(RideDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Ride... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
