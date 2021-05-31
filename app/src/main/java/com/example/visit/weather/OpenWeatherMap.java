package com.example.visit.weather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class OpenWeatherMap {
    private static final String BASE_URL = "https://api.openweathermap.org";
    private static final String API_KEY = "498a9c26d96534a84e001c916b65855c";

    public class UNITS{
        public static final String METRIC = "metric";
        public static final String IMPERIAL = "imperial";
    }

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(OpenWeatherMap.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static String getBASE_URL() {
        return BASE_URL;
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
