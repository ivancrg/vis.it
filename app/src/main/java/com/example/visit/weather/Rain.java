package com.example.visit.weather;

import com.google.gson.annotations.SerializedName;

public class Rain {
    // Rain volume for last hour, mm
    @SerializedName("1h")
    private Double volume;

    public Double getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return "Rain{" +
                "volume=" + volume +
                '}';
    }
}
