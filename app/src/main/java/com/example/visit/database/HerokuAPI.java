package com.example.visit.database;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface HerokuAPI {
    // Retrofit helps us by automatically populating list of Users
    // Call object encapsulates single requests and responses
    // @GET holds path added to base URL
    @GET("getUsers")
    Call<List<Users>> getUsers();

    @GET("getUser")
    Call<User> getUser(@Query("username") String username);

    @POST("login")
    Call<LoginPost> login(@Body LoginPost post);

    @POST("register")
    Call<RegisterPost> register(@Body RegisterPost post);

    @PATCH("updateUser/{username}")
    Call<UpdatePatch> updateData(@Path("username") String username, @Body UpdatePatch patch);

    @PATCH("updatePassword/{username}")
    Call<UpdatePasswordPatch> updatePassword(@Path("username") String username, @Body UpdatePasswordPatch patch);

    @POST("insertTrip")
    Call<TripPost> postTrip(@Body TripPost tripPost);

    @GET("getUsersTrips")
    Call<TripsGet> getUsersTrips(@Query("username") String username);
}
