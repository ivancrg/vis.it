package com.example.visit.database;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface HerokuAPI {
    // Retrofit helps us by automatically populating list of Users
    // Call object encapsulates single requests and responses
    // @GET holds path added to base URL
    @GET("getUsers")
    Call<List<Users>> getUsers();

    @POST("login")
    Call<LoginPost> login(@Body LoginPost post);

    @POST("register")
    Call<RegisterPost> register(@Body RegisterPost post);
}
