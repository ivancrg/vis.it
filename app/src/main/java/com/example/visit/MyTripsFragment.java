package com.example.visit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

       /* trips.add(new RecyclerViewItemMyTrips("Croatia", "Rijeka", "2021-05-20"));
        trips.add(new RecyclerViewItemMyTrips("Croatia", "Zagreb", "2021-05-20"));
        trips.add(new RecyclerViewItemMyTrips("Croatia", "Split", "2021-05-20")); */

        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);
        Call<TripsGet> call = herokuAPI.getUsersTrips(LoggedUser.getUsername());

        call.enqueue(new Callback<TripsGet>() {
                         @Override
                         public void onResponse(@NotNull Call<TripsGet> call, @NotNull Response<TripsGet> response) {
                             if (!response.isSuccessful()) {
                                 // Not OK
                                 Log.e("/getTrips", "notSuccessful: Something went wrong. " + response.code());
                                 Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                                 return;
                             }

                             assert response.body() != null;

                             TripsGet tripsGet = response.body();

                             // ovdje uopće ne ispisuje podatke iz baze

                             Log.i("", "" + response.body());

                             if (tripsGet.getFeedback().equals("trips_found")) {
                                 trips = tripsGet.getTrips();
                             } else {
                                 Toast.makeText(view.getContext(), "Sorry, there was an error." + tripsGet.getFeedback(), Toast.LENGTH_LONG).show();
                             }
                         }

                        @Override
                        public void onFailure(@NotNull Call<TripsGet> call, @NotNull Throwable t) {
                            Toast.makeText(view.getContext(), "Sorry! there was an error.", Toast.LENGTH_LONG).show();
                            Log.e("/getUsersTrips", "onFailure: Something went wrong. " + t.getMessage());
                         }

                     });

        recyclerView = view.findViewById(R.id.myTripsScreenVerticalRecyclerView);

        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewAdapter = new RecyclerViewAdapterMyTrips(this, trips);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }
}