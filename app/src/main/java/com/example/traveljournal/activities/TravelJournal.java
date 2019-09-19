package com.example.traveljournal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.View;
import com.example.traveljournal.R;
import com.example.traveljournal.adapters.TripAdapter;
import com.example.traveljournal.models.RoomDB;
import com.example.traveljournal.models.TripModel;
import java.util.Date;
import java.util.List;

public class TravelJournal extends AppCompatActivity {

    private RecyclerView tripListView;
    private RecyclerView.Adapter mTripAdapter;
    private static final int MANAGE_TRIP_ACTIVITY_REQUEST_CODE = 0;
    List<TripModel> tripList;
    RoomDB dbInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_journal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_trip_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ManageTripActivity.class);
                startActivityForResult(i, MANAGE_TRIP_ACTIVITY_REQUEST_CODE);
            }
        });

        dbInstance = RoomDB.getInstance(TravelJournal.this);
        tripList = dbInstance.loadTrips();
        tripListView = findViewById(R.id.trip_list_rv);
        tripListView.setLayoutManager(new LinearLayoutManager(this));
        mTripAdapter = new TripAdapter(this, tripList);
        tripListView.setAdapter(mTripAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check that it is the SecondActivity with an OK result
        if (requestCode == MANAGE_TRIP_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                String tripName = data.getStringExtra("tripName");
                String destination = data.getStringExtra("destination");
                String tripType = data.getStringExtra("tripType");
                int tripPrice = data.getIntExtra("tripPrice", 100);
                float tripRating = data.getFloatExtra("tripRating", 2.5f);
                Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                int startDay = data.getIntExtra("startDay", today.monthDay);
                int startMonth = data.getIntExtra("startMonth", today.month + 1);
                int startYear = data.getIntExtra("startYear", today.year);
                int endDay = data.getIntExtra("endDay", today.monthDay);
                int endMonth = data.getIntExtra("endMonth", today.month + 1);
                int endYear = data.getIntExtra("endYear", today.year);
                String mImageUrl = data.getStringExtra("imageURL");

                TripModel newTrip = new TripModel(destination, tripName, tripType, tripPrice, new Date(startYear, startMonth, startDay), new Date(endYear, endMonth, endDay), tripRating, mImageUrl);
                dbInstance.insertTrip(newTrip);
                tripList.add(newTrip);
                mTripAdapter.notifyItemInserted(tripList.size() - 1);
            }
        }
    }
}
