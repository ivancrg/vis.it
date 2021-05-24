package com.example.visit.weather;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherData {
    // Geographical coordinates of the location (latitude)
    @SerializedName("lat")
    private Double lat;

    // Geographical coordinates of the location (longitude)
    @SerializedName("lon")
    private Double lon;

    // Timezone name for the requested location
    @SerializedName("timezone")
    private String timezone;

    // Shift in seconds from UTC
    @SerializedName("timezone_offset")
    private Double timezoneOffset;

    // Current weather data API response
    @SerializedName("current")
    private CurrentWeatherData currentWeatherData;

    // Minute forecast weather data API response
    @SerializedName("minutely")
    private ArrayList<MinutelyForecastData> minutelyForecastData;

    // Hourly forecast weather data API response
    @SerializedName("hourly")
    private ArrayList<HourlyForecastData> hourlyForecastData;

    // Daily forecast weather data API response
    @SerializedName("daily")
    private ArrayList<DailyForecastData> dailyForecastData;

    // National weather alerts data from major national weather warning systems
    @SerializedName("alerts")
    private ArrayList<WeatherAlerts> weatherAlerts;

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public Double getTimezoneOffset() {
        return timezoneOffset;
    }

    public CurrentWeatherData getCurrentWeatherData() {
        return currentWeatherData;
    }

    public ArrayList<MinutelyForecastData> getMinutelyForecastData() {
        return minutelyForecastData;
    }

    public ArrayList<HourlyForecastData> getHourlyForecastData() {
        return hourlyForecastData;
    }

    public ArrayList<DailyForecastData> getDailyForecastData() {
        return dailyForecastData;
    }

    public ArrayList<WeatherAlerts> getWeatherAlerts() {
        return weatherAlerts;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", timezone='" + timezone + '\'' +
                ", timezoneOffset=" + timezoneOffset +
                ", currentWeatherData=" + currentWeatherData +
                ", minutelyForecastData=" + minutelyForecastData +
                ", hourlyForecastData=" + hourlyForecastData +
                ", dailyForecastData=" + dailyForecastData +
                ", weatherAlerts=" + weatherAlerts +
                '}';
    }
}
