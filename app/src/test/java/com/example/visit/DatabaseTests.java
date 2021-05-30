package com.example.visit;

import com.example.visit.database.ConnectionTestResponse;
import com.example.visit.database.Database;
import com.example.visit.database.HerokuAPI;
import com.example.visit.database.TripPost;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseTests {
    private static ArrayList<Integer> insertedTripIDs = new ArrayList<>();

    @BeforeAll
    public static void init() {
        System.out.println("DatabaseTests starting.");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "database_connection_test.csv", numLinesToSkip = 1)
    void databaseConnection(String testStringToServer, String expectedResponseFromServer) {
        // Request towards vis.it API for getting user's trips data
        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);
        Call<ConnectionTestResponse> call = herokuAPI.testConnection(testStringToServer);
        ConnectionTestResponse connectionTestResponse;

        try{
            Response<ConnectionTestResponse> response = call.execute();

            // Response code marks the completion of request as successful
            assert response.body() != null;
            connectionTestResponse = response.body();

            System.out.println("Expected: " + expectedResponseFromServer + " ==> Received: " + connectionTestResponse.getFeedback());
            assertEquals(expectedResponseFromServer, connectionTestResponse.getFeedback());
        } catch (Exception e){
            System.out.println("onFailure: Something went wrong. ==> " + e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "database_trip_insertion_test.csv", numLinesToSkip = 1)
    void databaseTripInsertion(String country, String city, String location, String travellingMode, int year, int month, int day, String necessities, String creator, String participants) throws ParseException {
        // This method inserts trips according to database_trip_insertion_test.csv file and calls
        // checkTripById method to make sure that the trips have really been added

        // Converting CSV file's year, month and day to LocalDate
        LocalDate localDate = LocalDate.of(year, month, day);

        TripPost tripPost = new TripPost(country,
                city,
                location,
                travellingMode,
                localDate,
                necessities,
                participants,
                creator);

        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);
        Call<TripPost> call = herokuAPI.postTripGetID(tripPost);
        TripPost tripPostResponse;

        try{
            Response<TripPost> response = call.execute();

            // Response code marks the completion of request as successful
            assert response.body() != null;
            tripPostResponse = response.body();

            boolean trip_is_inserted_correctly = "trip_inserted".equals(tripPostResponse.getFeedback()) && checkTripById(tripPostResponse.getId(), tripPost);
            if(trip_is_inserted_correctly) insertedTripIDs.add(tripPostResponse.getId());

            System.out.println("Expected: 'trip_inserted' ==> Received: " + tripPostResponse.getFeedback());
            assertTrue(trip_is_inserted_correctly);
        } catch (Exception e){
            System.out.println("onFailure databaseTripInsertion: Something went wrong. ==> " + e.getMessage());
        }
    }

    private boolean checkTripById(int insertedTripID, TripPost inputTrip) {
        // This method checks whether trip that was supposed to be inserted (inputTrip)
        // is actually the same as the trip that was inserted (trip with ID equal to insertedTripID)


        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);
        Call<TripPost> call = herokuAPI.getTripById(insertedTripID);
        TripPost tripPostResponse;

        try{
            Response<TripPost> response = call.execute();

            // Response code marks the completion of request as successful
            assert response.body() != null;
            tripPostResponse = response.body();

            System.out.println("Expected: " + inputTrip.toString() + " ==> Received: " + tripPostResponse.toString());
            return tripPostResponse.contentEquals(inputTrip);
        } catch (Exception e){
            System.out.println("onFailure checkTripById: Something went wrong. ==> " + e.getMessage());
        }

        return false;
    }

    @AfterAll
    public static void afterAll(){
        // This method deletes all the inserted trips according to list of IDs

        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);

        for(Integer id : insertedTripIDs){
            Call<String> call = herokuAPI.deleteTripById(id);
            String responseString;

            try{
                Response<String> response = call.execute();

                // Response code marks the completion of request as successful
                assert response.body() != null;
                responseString = response.body();

                if(responseString.equals("trip_deleted")){
                    System.out.println("Trip " + id + " successfully deleted.");
                } else{
                    System.out.println("Error occurred while deleting trip " + id + ".");
                }
            } catch (Exception e){
                System.out.println("onFailure afterAll: Something went wrong. ==> " + e.getMessage());
            }
        }

        System.out.println("All tests executed.");
    }
}