package com.example.visit;

import com.example.visit.database.ConnectionTestResponse;
import com.example.visit.database.Database;
import com.example.visit.database.HerokuAPI;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTests {
    @BeforeAll
    public static void init() {
        System.out.println("RegisterFragmentTest starting.");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "database_connection_test.csv", numLinesToSkip = 1)
    void databaseConnection(String testStringToServer, String expectedResponseFromServer) {
        // Request towards vis.it API for getting user's trips data
        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);
        Call<ConnectionTestResponse> call = herokuAPI.testConnection(testStringToServer);

        call.enqueue(new Callback<ConnectionTestResponse>() {
            @Override
            public void onResponse(@NotNull Call<ConnectionTestResponse> call, @NotNull Response<ConnectionTestResponse> response) {
                // Response received

                if (!response.isSuccessful()) {
                    // Not OK
                    System.out.println("notSuccessful: Something went wrong. ==> " + response.code());
                    return;
                }

                // Response code marks the completion of request as successful
                assert response.body() != null;
                ConnectionTestResponse connectionTestResponse = response.body();

                assertEquals(expectedResponseFromServer, connectionTestResponse.getFeedback());
                System.out.println("Expected: " + expectedResponseFromServer + " ==> Received: " + connectionTestResponse.getFeedback());
            }

            @Override
            public void onFailure(@NotNull Call<ConnectionTestResponse> call, @NotNull Throwable t) {
                // Request towards vis.it API could not be completed
                System.out.println("onFailure: Something went wrong. ==> " + t.getMessage());
            }
        });
    }
}