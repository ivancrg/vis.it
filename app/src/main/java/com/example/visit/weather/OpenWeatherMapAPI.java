package com.example.visit.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface OpenWeatherMapAPI {
    // Retrofit helps us by automatically populating WeatherData object
    // Call object encapsulates single requests and responses
    // @GET holds path added to base URL
    @GET("/data/2.5/onecall")
    Call<WeatherData> getWeatherData(@Query("lat") Double lat, @Query("lon") Double lon, @Query("appid") String appid, @Query("units") String units);
}
