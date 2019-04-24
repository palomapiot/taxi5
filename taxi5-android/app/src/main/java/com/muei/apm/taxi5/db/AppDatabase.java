package com.muei.apm.taxi5.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.muei.apm.taxi5.dao.RideDao;
import com.muei.apm.taxi5.dao.UserDao;
import com.muei.apm.taxi5.entities.Ride;
import com.muei.apm.taxi5.entities.User;

import java.util.Calendar;

@Database(entities = {User.class, Ride.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract RideDao rideDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "taxi5").allowMainThreadQueries()
                    // Wipes and rebuilds
                    .fallbackToDestructiveMigration()
                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Example data when db created
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao userDao;
        private final RideDao rideDao;

        PopulateDbAsync(AppDatabase db) {
            userDao = db.userDao();
            rideDao = db.rideDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            User user = new User("Paloma", "Piot", "paloma@email.com", "678345123", "micontrasenha");
            userDao.insert(user);

            long userId = userDao.findByName(user.firstName, user.lastName).id;

            Calendar date = Calendar.getInstance();
            date.set(2019, 4, 21, 11,40);
            Ride ride = new Ride("Oleiros", "Arteixo", date, userId);
            rideDao.insert(ride);

            return null;
        }
    }

}
