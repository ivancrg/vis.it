package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

public class CurrentTripGet {
    @SerializedName("feedback")
    private String feedback;

    @SerializedName("currently_on_trip")
    private String trip_id;

    public String getCurrentTrip() {
        return trip_id;
    }
    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "CurrentTrip{" +
                "currently_on_trip = '" + trip_id + "'" +
                ", feedback = '" + feedback + '\'' +
                '}';
    }
}
