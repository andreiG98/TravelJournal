package com.example.traveljournal.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.traveljournal.helpers.DateConverter;

import java.util.Date;

@Entity(tableName = "Trip")
@TypeConverters(DateConverter.class)
public class TripModel {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id")
    public int id;

    @ColumnInfo (name = "destination")
    private String destination;

    @ColumnInfo (name = "trip_name")
    private  String tripName;

    @ColumnInfo (name = "trip_type")
    private String tripType;

    @ColumnInfo (name = "trip_price")
    private int tripPrice;

    @ColumnInfo (name = "start_date")
    private Date startDate;

    @ColumnInfo (name = "end_date")
    private Date endDate;

    @ColumnInfo (name = "trip_rating")
    private float tripRating;

    @ColumnInfo (name = "trip_icon_url")
    private String tripIconURL;

    public TripModel() {
    }

    public TripModel(String destination, String tripName, String tripType, int tripPrice, Date startDate, Date endDate, float tripRating, String tripIconURL) {
        this.destination = destination;
        this.tripName = tripName;
        this.tripType = tripType;
        this.tripPrice = tripPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripRating = tripRating;
        this.tripIconURL = tripIconURL;
    }

    public int getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public String getTripName() {
        return tripName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getTripIconURL() {
        return tripIconURL;
    }

    public String getTripType() {
        return tripType;
    }

    public int getTripPrice() {
        return tripPrice;
    }

    public float getTripRating() {
        return tripRating;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTripIconURL(String tripIconURL) {
        this.tripIconURL = tripIconURL;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public void setTripPrice(int tripPrice) {
        this.tripPrice = tripPrice;
    }

    public void setTripRating(float tripRating) {
        this.tripRating = tripRating;
    }
}
