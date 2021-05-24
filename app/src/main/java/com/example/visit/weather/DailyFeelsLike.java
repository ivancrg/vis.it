package com.example.visit.weather;

import com.google.gson.annotations.SerializedName;

public class DailyFeelsLike {
    // Morning feels like temperature
    @SerializedName("morn")
    private Double morning;

    // Day feels like temperature
    @SerializedName("day")
    private Double day;

    // Evening feels like temperature
    @SerializedName("eve")
    private Double eve;

    // Night feels like temperature
    @SerializedName("night")
    private Double night;

    public Double getMorning() {
        return morning;
    }

    public Double getDay() {
        return day;
    }

    public Double getEve() {
        return eve;
    }

    public Double getNight() {
        return night;
    }

    @Override
    public String toString() {
        return "DailyFeelsLike{" +
                "morning=" + morning +
                ", day=" + day +
                ", eve=" + eve +
                ", night=" + night +
                '}';
    }
}
