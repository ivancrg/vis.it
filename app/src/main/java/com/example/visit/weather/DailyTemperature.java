package com.example.visit.weather;

import com.google.gson.annotations.SerializedName;

public class DailyTemperature {
    // Morning temperature
    @SerializedName("morn")
    private Double morning;

    // Day temperature
    @SerializedName("day")
    private Double day;

    // Evening temperature
    @SerializedName("eve")
    private Double eve;

    // Night temperature
    @SerializedName("night")
    private Double night;

    // Minimum daily temperature
    @SerializedName("min")
    private Double minimum;

    // Maximum daily temperature
    @SerializedName("max")
    private Double maximum;

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

    public Double getMinimum() {
        return minimum;
    }

    public Double getMaximum() {
        return maximum;
    }

    @Override
    public String toString() {
        return "DailyTemperature{" +
                "morning=" + morning +
                ", day=" + day +
                ", eve=" + eve +
                ", night=" + night +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                '}';
    }
}
