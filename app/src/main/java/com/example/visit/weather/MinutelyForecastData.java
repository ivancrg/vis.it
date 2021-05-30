package com.example.visit.weather;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

    // Converts forecast's time (dt) to local (device's timezone) HH:mm format
    public String getTimeHHmm(){
        long timeLong = (long) (time * 1000);
        Date date = new Date(timeLong);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getDefault());

        return format.format(date);
    }

    public Double getPrecipitationVolume() {
        return precipitationVolume;
    }

    @Override
    public @NotNull String toString() {
        return "MinutelyForecastData{" +
                "time=" + time +
                ", precipitationVolume=" + precipitationVolume +
                '}';
    }
}
