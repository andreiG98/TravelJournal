package com.example.traveljournal.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Database(entities = {TripModel.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB INSTANCE;

    public abstract RoomDAO roomDao ();

    public static RoomDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "room-db").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    public void insertTrip (final TripModel tripModel) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                INSTANCE.roomDao().insertTrip(tripModel);
                return null;
            }
        }.execute();
    }

    public void deleteTrip (final TripModel tripModel) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                INSTANCE.roomDao().deleteTrip(tripModel);
                return null;
            }
        }.execute();
    }

    public List<TripModel> loadTrips () {
        try {
            return new AsyncTask<Void, Void, List>() {
                @Override
                protected List<TripModel> doInBackground(Void... voids) {
                    return INSTANCE.roomDao().getAllTrips();
                }
            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
