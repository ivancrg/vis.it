package com.example.visit.database;

import com.example.visit.travelling.RecyclerViewItemMyTrips;

import java.util.ArrayList;

public class TripsGet {

    private ArrayList<RecyclerViewItemMyTrips> trips;
    private String feedback;

    public ArrayList<RecyclerViewItemMyTrips> getTrips() {
        return trips;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "TripsGet{" +
                "trips=" + trips +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
