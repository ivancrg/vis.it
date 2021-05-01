package com.example.visit;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

public class CountryPost {
    private String error;
    private String msg;

    @SerializedName("data")
    private Array cities;

    public CountryPost (String error, String message, Array cities){
        this.error = error;
        this.msg = message;
        this.cities = cities;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return msg;
    }

    public Array getCities() {
        return cities;
    }
}
