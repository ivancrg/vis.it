package com.example.visit.database;

import android.provider.ContactsContract;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Intended to hold general details about database or connecting to it

public abstract class Database {
    private final static String BASE_URL = "https://vis-it.herokuapp.com/";

    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Database.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static String getBASE_URL() {
        return BASE_URL;
    }
}
