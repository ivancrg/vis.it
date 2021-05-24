package com.example.visit.weather;

import com.google.gson.annotations.SerializedName;

public class MinutelyForecastData {
    // Time of the forecasted data, unix, UTC
    @SerializedName("dt")
    private Double time;

    // Precipitation volume, mm
    @SerializedName("precipitation")
    private Double precipitationVolume;

    public Double getTime() {
        return time;
    }

    public Double getPrecipitationVolume() {
        return precipitationVolume;
    }

    @Override
    public String toString() {
        return "MinutelyForecastData{" +
                "time=" + time +
                ", precipitationVolume=" + precipitationVolume +
                '}';
    }
}
