package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyTripsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

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

        ArrayList<RecyclerViewItemMyTrips> trips = new ArrayList<>();
        trips.add(new RecyclerViewItemMyTrips("Croatia", "Rijeka", "2021-05-20"));
        trips.add(new RecyclerViewItemMyTrips("Croatia", "Zagreb", "2021-05-20"));
        trips.add(new RecyclerViewItemMyTrips("Croatia", "Split", "2021-05-20"));

        /*
        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);
        Call<ArrayList<RecyclerViewItemMyTrips>> call = herokuAPI.getTrips(LoggedUser.getUsername());

        call.enqueue(new Call<ArrayList<RecyclerViewItemMyTrips>>() {
                         @Override
                         public void onResponse(@NotNull Call<ArrayList<RecyclerViewItemMyTrips>> call, @NotNull Response<ArrayList<RecyclerViewItemMyTrips>> response) {
                             if (!response.isSuccessful()) {
                                 // Not OK
                                 Log.e("/getTrips", "notSuccessful: Something went wrong. " + response.code());
                                 Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                                 return;
                             }

                             assert response.body() != null;

                             ArrayList<RecyclerViewItemMyTrips> myTrips = response.body();

                             if (myTrips.getFeedback().equals("trips_found")) {
                                 trips = myTrips;

                                 FragmentTransaction fragmentTransaction = getActivity()
                                         .getSupportFragmentManager().beginTransaction();
                                 fragmentTransaction.replace(R.id.fragment_container, new UserInterfaceFragment());
                                 fragmentTransaction.commit();
                             } else {
                                 Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                             }
                         }
                     }*/

        recyclerView = view.findViewById(R.id.myTripsScreenVerticalRecyclerView);

        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewAdapter = new RecyclerViewAdapterMyTrips(this, trips);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }
}