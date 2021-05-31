package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

public class DeleteTripResponse {
    @SerializedName("feedback")
    String feedback;

    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "DeleteTripResponse{" +
                "feedback='" + feedback + '\'' +
                '}';
    }
}
