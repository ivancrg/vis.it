package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TripDetailsFragment extends Fragment {

    public String dateDisplay;

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
        TextView location = (TextView) view.findViewById(R.id.tripDetailsLocation);
        TextView necessities = (TextView) view.findViewById(R.id.tripDetailsNecessities);
        TextView travellingMode = (TextView) view.findViewById(R.id.tripDetailsTravellingMode);
        TextView participants = (TextView) view.findViewById(R.id.tripDetailsParticipants);
        Button start = (Button) view.findViewById(R.id.startTripButton);
        Button returnButton = (Button) view.findViewById(R.id.returnToTripsButton);

        dateDisplay = ChosenTrip.getDate().split("T")[0];

        country.setText(ChosenTrip.getCountry());
        city.setText(ChosenTrip.getCity());
        date.setText(dateDisplay);
        location.setText(ChosenTrip.getLocation());
        necessities.setText((ChosenTrip.getNecessities() == null) ? ("No necessities selected.") : (ChosenTrip.getNecessities()));
        travellingMode.setText(ChosenTrip.getTravellingMode());
        participants.setText((ChosenTrip.getParticipantsDescription() == null) ? ("No participants selected") : (ChosenTrip.getParticipantsDescription()));

        start.setOnClickListener(v -> {
            // Sending city and country name to TravellingFragment
            Bundle args = new Bundle();
            args.putString("country", country.toString());
            args.putString("city", city.toString());

            TravellingFragment fragmentTravelling = new TravellingFragment();
            fragmentTravelling.setArguments(args);

            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragmentTravelling);
            fragmentTransaction.commit();
        });

        returnButton.setOnClickListener(v -> {
            if (LoggedUser.getIsLoggedIn()) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new MyTripsFragment());
                fragmentTransaction.commit();
            } else {
                Toast.makeText(view.getContext(), "You are currently not logged in.", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}