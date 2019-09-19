package com.example.traveljournal.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RoomDAO {
    @Query("SELECT * FROM Trip")
    List<TripModel> getAllTrips();

    @Insert
    void insertTrip (TripModel taskModel);

    @Update
    void updateTrip (TripModel projectModel);

    @Delete
    void deleteTrip (TripModel taskModel);
}
