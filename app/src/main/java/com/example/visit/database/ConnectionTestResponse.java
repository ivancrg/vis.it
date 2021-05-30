package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

public class ConnectionTestResponse {
    @SerializedName("feedback")
    private String feedback;

    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "ConnectionTestResponse{" +
                "feedback='" + feedback + '\'' +
                '}';
    }
}
