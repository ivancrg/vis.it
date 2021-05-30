package com.example.visit.database;

import com.example.visit.RecyclerViewItemMyTrips;
import com.google.gson.annotations.SerializedName;

public class CurrentTripGet {
    @SerializedName("feedback")
    private String feedback;

    @SerializedName("currently_on_trip")
    private String trip_id;

    @SerializedName("trip_data")
    RecyclerViewItemMyTrips trip_details;

    public String getCurrentTrip() {
        return trip_id;
    }
    public RecyclerViewItemMyTrips getTripDetails() {
        return trip_details;
    }
    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "CurrentTrip{" +
                "currently_on_trip = '" + trip_id + "'" +
                ", trip details: " + trip_details.toString() +
                ", feedback = '" + feedback + '\'' +
                '}';
    }
}
