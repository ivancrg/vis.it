package com.example.visit;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TripDetailsFragment extends Fragment {

    public String dateDisplay;

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    double lat = 0;
    double lng = 0;

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

        String destinationCity = city.getText().toString();
        String destinationCountry = country.getText().toString();

        start.setOnClickListener(v -> {
            // Sending city and country name to TravellingFragment
            Bundle args = new Bundle();
            args.putString("destinationCity", destinationCity);
            args.putString("destinationCountry", destinationCountry);

            //Get destination city lat and long from name
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> listOfAddress;
            try {
                listOfAddress = geocoder.getFromLocationName(destinationCity, 1);
                if (listOfAddress != null && !listOfAddress.isEmpty()) {
                    Address address = listOfAddress.get(0);

                    lat = address.getLatitude();
                    lng = address.getLongitude();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Sending city lat and lng to Travelling Fragment
            args.putDouble("destinationCityLat", lat);
            args.putDouble("destinationCityLng", lng);

            TravellingFragment fragmentTravelling = new TravellingFragment();
            fragmentTravelling.setArguments(args);

            MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), fragmentTravelling, false);
        });

        returnButton.setOnClickListener(v -> {
            if (LoggedUser.getIsLoggedIn()) {
                MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new MyTripsFragment(), false);
            } else {
                Toast.makeText(view.getContext(), "You are currently not logged in.", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}