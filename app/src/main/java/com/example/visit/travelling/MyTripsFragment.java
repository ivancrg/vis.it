package com.example.visit.travelling;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.user_profile.LoggedUser;
import com.example.visit.R;
import com.example.visit.database.Database;
import com.example.visit.database.HerokuAPI;
import com.example.visit.database.TripsGet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyTripsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList<RecyclerViewItemMyTrips> trips;

    public MyTripsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_trips, container, false);

        recyclerView = view.findViewById(R.id.myTripsScreenVerticalRecyclerView);
        tripsToRecyclerView(view);

        return view;
    }

    private void tripsToRecyclerView(View view) {
        // Request towards vis.it API for getting user's trips data

        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);
        Call<TripsGet> call = herokuAPI.getUsersTrips(LoggedUser.getUsername());

        call.enqueue(new Callback<TripsGet>() {
            @Override
            public void onResponse(@NotNull Call<TripsGet> call, @NotNull Response<TripsGet> response) {
                // Response received

                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/getTrips", "notSuccessful: Something went wrong. " + response.code());
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Response code marks the completion of request as successful
                assert response.body() != null;
                TripsGet tripsGet = response.body();

                if (tripsGet.getFeedback().equals("trips_found")) {
                    // API returned an array of trips
                    trips = tripsGet.getTrips();

                    recyclerViewLayoutManager = new LinearLayoutManager(getContext());
                    recyclerViewAdapter = new RecyclerViewAdapterMyTrips(requireActivity().getSupportFragmentManager(), trips);

                    recyclerView.setLayoutManager(recyclerViewLayoutManager);
                    recyclerView.setAdapter(recyclerViewAdapter);
                } else {
                    // API did not return any trip data (because of a database error or because the user has no trips saved)
                    Toast.makeText(view.getContext(), "No trips available.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<TripsGet> call, @NotNull Throwable t) {
                // Request towards vis.it API could not be completed

                Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                Log.e("/getUsersTrips", "onFailure: Something went wrong. " + t.getMessage());
            }
        });
    }
}