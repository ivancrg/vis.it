package com.example.visit.database;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @PATCH("addOnTrip/{username}")
    Call<CurrentTripPatch> addCurrentTrip(@Path("username") String username, @Body CurrentTripPatch patch);

    @GET("readOnTrip")
    Call<CurrentTripGet> getCurrentTrip(@Query("username") String username);

    @GET("connectionTest")
    Call<ConnectionTestResponse> testConnection(@Query("testString") String testString);

    // Only difference to @POST("insertTrip") is that we receive both feedback and ID of inserted trip
    // They can be combined to one path, but I didn't want to change /insertTrip one day before the control point
    @POST("insertTripResponseID")
    Call<TripPost> postTripGetID(@Body TripPost tripPost);

    @GET("getTripById")
    Call<TripPost> getTripById(@Query("id") int id);

    @DELETE("deleteTripById")
    Call<String> deleteTripById(@Query("id") int id);
}
