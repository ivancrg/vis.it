package com.example.visit.database;

import com.example.visit.RecyclerViewItemMyTrips;
import com.google.gson.annotations.SerializedName;

public class CurrentTripGet {
    @SerializedName("feedback")
    private String feedback;

    @SerializedName("currently_on_trip")
    private String tripId;

    @SerializedName("trip_data")
    RecyclerViewItemMyTrips tripDetails;

    public String getCurrentTrip() {
        return tripId;
    }
    public RecyclerViewItemMyTrips getTripDetails() {
        return tripDetails;
    }
    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "CurrentTrip{" +
                "currently_on_trip = '" + tripId + "'" +
                ", trip details: " + tripDetails.toString() +
                ", feedback = '" + feedback + '\'' +
                '}';
    }
}
