package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class TripDetailsFragment extends Fragment {

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_details, container, false);

        TextView country = (TextView) view.findViewById(R.id.tripDetailsCountry);
        TextView city = (TextView) view.findViewById(R.id.tripDetailsCity);
        TextView date = (TextView) view.findViewById(R.id.tripDetailsDate);
        Button start = (Button) view.findViewById(R.id.startTripButton);

        country.setText(ChosenTrip.getCountry());
        city.setText(ChosenTrip.getCity());
        date.setText(ChosenTrip.getDate());

        start.setOnClickListener(v -> {
            // TODO
            // connect to travelling fragment
        });

        return view;
    }
}