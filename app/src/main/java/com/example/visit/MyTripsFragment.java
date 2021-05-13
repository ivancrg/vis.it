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
        trips.add(new RecyclerViewItemMyTrips("Croatia", "Rijeka", "2021-15-5"));
        trips.add(new RecyclerViewItemMyTrips("Croatia", "Zagreb", "2021-15-5"));
        trips.add(new RecyclerViewItemMyTrips("Croatia", "Split", "2021-15-5"));

        recyclerView = view.findViewById(R.id.myTripsScreenVerticalRecyclerView);

        // ??
        //recyclerView.setHasFixedSize(true);

        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewAdapter = new RecyclerViewAdapterMyTrips(getContext(), trips);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }
}