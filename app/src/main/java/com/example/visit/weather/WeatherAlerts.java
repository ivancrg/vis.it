package com.example.visit.weather;

import com.google.gson.annotations.SerializedName;

public class WeatherAlerts {
    // Name of the alert source
    @SerializedName("sender_name")
    private String senderName;

    // Name of the alert source
    @SerializedName("event")
    private String eventName;

    // Name of the alert source
    @SerializedName("start")
    private Double startTime;

    // Name of the alert source
    @SerializedName("end")
    private Double endTime;

    // Name of the alert source
    @SerializedName("description")
    private String description;

    public String getSenderName() {
        return senderName;
    }

    public String getEventName() {
        return eventName;
    }

    public Double getStartTime() {
        return startTime;
    }

    public Double getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "WeatherAlerts{" +
                "senderName='" + senderName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                '}';
    }
}
