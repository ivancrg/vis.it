package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

public class CurrentTripGet {
    @SerializedName("feedback")
    private String feedback;

    @SerializedName("currently_on_trip")
    private int trip_id;

    public int getCurrentTrip() {
        return trip_id;
    }
    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "CurrentTrip{" +
                "feedback = '" + feedback + '\'' +
                '}';
    }
}
