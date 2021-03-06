package com.example.visit.weather;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentWeatherData {
    // Current time, Unix, UTC
    @SerializedName("dt")
    private Double time;

    // Sunrise time, Unix, UTC
    @SerializedName("sunrise")
    private Double sunrise;

    // Sunset time, Unix, UTC
    @SerializedName("sunset")
    private Double sunset;

    // Temperature
    // Units - default: kelvin, metric: Celsius, imperial: Fahrenheit
    @SerializedName("temp")
    private Double temperature;

    // Temperature - this temperature parameter accounts for the human perception of weather
    // Units – default: kelvin, metric: Celsius, imperial: Fahrenheit
    @SerializedName("feels_like")
    private Double temperatureFeelsLike;

    // Atmospheric pressure on the sea level, hPa
    @SerializedName("pressure")
    private Double pressure;

    // Humidity, %
    @SerializedName("humidity")
    private Double humidity;

    // Atmospheric temperature (varying according to pressure and humidity) below which water droplets begin to condense and dew can form
    // Units – default: kelvin, metric: Celsius, imperial: Fahrenheit
    @SerializedName("dew_point")
    private Double dewPoint;

    // Cloudiness, %
    @SerializedName("clouds")
    private Double cloudsPercentage;

    // Current UV index
    @SerializedName("uvi")
    private Double uvIndex;

    // Average visibility, metres
    @SerializedName("visibility")
    private Double visibility;

    // Wind speed
    // Units – default: metre/sec, metric: metre/sec, imperial: miles/hour
    @SerializedName("wind_speed")
    private Double windSpeed;

    // Sometimes not available
    // Wind gust
    // Units – default: metre/sec, metric: metre/sec, imperial: miles/hour
    @SerializedName("wind_gust")
    private Double windGust;

    // Wind direction, degrees (meteorological)
    @SerializedName("wind_deg")
    private Double windDirection;

    // Sometimes not available
    // Rain volume for last hour, mm
    @SerializedName("rain")
    private Rain rainData;

    // Sometimes not available
    // Snow volume for last hour, mm
    @SerializedName("snow")
    private Snow snowData;

    // Describes weather conditions
    @SerializedName("weather")
    private ArrayList<WeatherDescription> weatherDescription;

    public Double getTime() {
        return time;
    }

    public Double getSunrise() {
        return sunrise;
    }

    public Double getSunset() {
        return sunset;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getTemperatureFeelsLike() {
        return temperatureFeelsLike;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    public Double getCloudsPercentage() {
        return cloudsPercentage;
    }

    public Double getUvIndex() {
        return uvIndex;
    }

    public Double getVisibility() {
        return visibility;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Double getWindGust() {
        return windGust;
    }

    public Double getWindDirection() {
        return windDirection;
    }

    public Rain getRainData() {
        return rainData;
    }

    public Snow getSnowData() {
        return snowData;
    }

    public ArrayList<WeatherDescription> getWeatherDescription() {
        return weatherDescription;
    }

    @Override
    public String toString() {
        return "CurrentWeatherData{" +
                "time=" + time +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", temperature=" + temperature +
                ", temperatureFeelsLike=" + temperatureFeelsLike +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", dewPoint=" + dewPoint +
                ", cloudsPercentage=" + cloudsPercentage +
                ", uvIndex=" + uvIndex +
                ", visibility=" + visibility +
                ", windSpeed=" + windSpeed +
                ", windGust=" + windGust +
                ", windDirection=" + windDirection +
                ", rainData=" + rainData +
                ", snowData=" + snowData +
                ", weatherDescription=" + weatherDescription +
                '}';
    }
}
