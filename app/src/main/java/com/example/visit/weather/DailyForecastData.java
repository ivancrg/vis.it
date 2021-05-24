package com.example.visit.weather;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DailyForecastData {
    // Time of the forecast data, Unix, UTC
    @SerializedName("dt")
    private Double forecastTime;

    // Sunrise time, Unix, UTC
    @SerializedName("sunrise")
    private Double sunrise;

    // Sunset time, Unix, UTC
    @SerializedName("sunset")
    private Double sunset;

    // The time of when the moon rises for this day, Unix, UTC
    @SerializedName("moonrise")
    private Double moonrise;

    // The time of when the moon sets for this day, Unix, UTC
    @SerializedName("moonset")
    private Double moonset;

    // oon phase. 0 and 1 are 'new moon', 0.25 is 'first quarter moon', 0.5 is 'full moon'
    // and 0.75 is 'last quarter moon'. The periods in between are called 'waxing crescent',
    // 'waxing gibous', 'waning gibous', and 'waning crescent', respectively
    @SerializedName("moon_phase")
    private Double moonPhase;

    // Temperature
    // Units – default: kelvin, metric: Celsius, imperial: Fahrenheit
    @SerializedName("temp")
    private DailyTemperature temperature;

    // This accounts for the human perception of weather
    // Units – default: kelvin, metric: Celsius, imperial: Fahrenheit
    @SerializedName("feels_like")
    private DailyFeelsLike temperatureFeelsLike;

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

    // Cloudiness, %
    @SerializedName("clouds")
    private Double clouds;

    // The maximum value of UV index for the day
    @SerializedName("uvi")
    private Double uvIndex;

    // Probability of precipitation
    @SerializedName("pop")
    private Double probabilityOfPrecipitation;

    // Sometimes not available
    // Precipitation volume, mm
    @SerializedName("rain")
    private Double rainVolume;

    // Sometimes not available
    // Snow volume, mm
    @SerializedName("snow")
    private Double snowVolume;

    // Weather description
    @SerializedName("weather")
    private ArrayList<WeatherDescription> weatherDescription;

    public Double getForecastTime() {
        return forecastTime;
    }

    public Double getSunrise() {
        return sunrise;
    }

    public Double getSunset() {
        return sunset;
    }

    public Double getMoonrise() {
        return moonrise;
    }

    public Double getMoonset() {
        return moonset;
    }

    public Double getMoonPhase() {
        return moonPhase;
    }

    public DailyTemperature getTemperature() {
        return temperature;
    }

    public DailyFeelsLike getTemperatureFeelsLike() {
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

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Double getWindGust() {
        return windGust;
    }

    public Double getWindDirection() {
        return windDirection;
    }

    public Double getClouds() {
        return clouds;
    }

    public Double getUvIndex() {
        return uvIndex;
    }

    public Double getProbabilityOfPrecipitation() {
        return probabilityOfPrecipitation;
    }

    public Double getRainVolume() {
        return rainVolume;
    }

    public Double getSnowVolume() {
        return snowVolume;
    }

    public ArrayList<WeatherDescription> getWeatherDescription() {
        return weatherDescription;
    }

    @Override
    public String toString() {
        return "DailyForecastData{" +
                "forecastTime=" + forecastTime +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", moonrise=" + moonrise +
                ", moonset=" + moonset +
                ", moonPhase=" + moonPhase +
                ", temperature=" + temperature +
                ", temperatureFeelsLike=" + temperatureFeelsLike +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", dewPoint=" + dewPoint +
                ", windSpeed=" + windSpeed +
                ", windGust=" + windGust +
                ", windDirection=" + windDirection +
                ", clouds=" + clouds +
                ", uvIndex=" + uvIndex +
                ", probabilityOfPrecipitation=" + probabilityOfPrecipitation +
                ", rainVolume=" + rainVolume +
                ", snowVolume=" + snowVolume +
                ", weatherDescription=" + weatherDescription +
                '}';
    }
}
