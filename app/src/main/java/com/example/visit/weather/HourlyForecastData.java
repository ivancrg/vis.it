package com.example.visit.weather;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class HourlyForecastData {
    // Time of the forecast data, Unix, UTC
    @SerializedName("dt")
    private Double time;

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

    // Current UV index
    @SerializedName("uvi")
    private Double uvIndex;

    // Cloudiness, %
    @SerializedName("clouds")
    private Double cloudsPercentage;

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

    // Probability of precipitation
    @SerializedName("pop")
    private Double probabilityOfPrecipitation;

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

    public Double getUvIndex() {
        return uvIndex;
    }

    public Double getCloudsPercentage() {
        return cloudsPercentage;
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

    public Double getProbabilityOfPrecipitation() {
        return probabilityOfPrecipitation;
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

    // Converts forecast's time (dt) to local (device's timezone) dd.MM.yyyy. @ HH:mm format
    public String getDateTime(){
        long timeLong = (long) (time * 1000);
        Date date = new Date(timeLong);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy. @ HH:mm");
        format.setTimeZone(TimeZone.getDefault());

        return format.format(date);
    }

    // Converts forecast's time (dt) to local (device's timezone) HH:mm format
    public String getTimeHHmm(){
        long timeLong = (long) (time * 1000);
        Date date = new Date(timeLong);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getDefault());

        return format.format(date);
    }

    @Override
    public String toString() {
        return "HourlyForecastData{" +
                "time=" + time +
                ", temperature=" + temperature +
                ", temperatureFeelsLike=" + temperatureFeelsLike +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", dewPoint=" + dewPoint +
                ", uvIndex=" + uvIndex +
                ", cloudsPercentage=" + cloudsPercentage +
                ", visibility=" + visibility +
                ", windSpeed=" + windSpeed +
                ", windGust=" + windGust +
                ", windDirection=" + windDirection +
                ", probabilityOfPrecipitation=" + probabilityOfPrecipitation +
                ", rainData=" + rainData +
                ", snowData=" + snowData +
                ", weatherDescription=" + weatherDescription +
                '}';
    }
}
