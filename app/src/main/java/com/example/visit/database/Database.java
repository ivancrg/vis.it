package com.example.visit.database;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Intended to hold general details about database or connecting to it

public abstract class Database {
    private static final String BASE_URL = "https://vis-it.herokuapp.com/";

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Database.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static String getBASE_URL() {
        return BASE_URL;
    }
}
