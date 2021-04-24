package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TripPlannerFragment extends Fragment {

    public TripPlannerFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trip_planner, container, false);

        Button countryButton = (Button) view.findViewById(R.id.countryButton);
        Button cityButton = (Button) view.findViewById(R.id.cityButton);
        Button locationButton = (Button) view.findViewById(R.id.locationButton);
        Button travellingModeButton = (Button) view.findViewById(R.id.travellingModeButton);
        Button dateButton = (Button) view.findViewById(R.id.dateButton);
        Button necessitiesButton = (Button) view.findViewById(R.id.necessitiesButton);
        Button participantsButton = (Button) view.findViewById(R.id.participantsButton);

        TextView selectedCountryTextView = (TextView) view.findViewById(R.id.selectedCountryTextView);
        TextView selectedCityTextView = (TextView) view.findViewById(R.id.selectedCityTextView);
        TextView selectedLocationTextView = (TextView) view.findViewById(R.id.selectedLocationTextView);
        TextView selectedTravellingModeTextView = (TextView) view.findViewById(R.id.selectedTravellingModeTextView);
        TextView selectedDateTextView = (TextView) view.findViewById(R.id.selectedDateTextView);
        TextView selectedNecessitiesTextView = (TextView) view.findViewById(R.id.selectedNecessitiesTextView);
        TextView selectedParticipantsTextView = (TextView) view.findViewById(R.id.selectedParticipantsTextView);

        selectedCountryTextView.setText((TripPlanning.getCountry() == null) ? ("No country selected.") : (TripPlanning.getCountry()));
        selectedCityTextView.setText((TripPlanning.getCity() == null) ? ("No city selected.") : (TripPlanning.getCity()));
        selectedLocationTextView.setText((TripPlanning.getLocation() == null) ? ("No location selected.") : (TripPlanning.getLocation()));
        selectedTravellingModeTextView.setText((TripPlanning.getTravellingMode() == null) ? ("No travelling mode selected.") : (TripPlanning.getTravellingMode()));
        selectedDateTextView.setText((TripPlanning.getDateOfDeparture() == null) ? ("No date selected.") : (TripPlanning.getDateOfDeparture().toString()));
        selectedNecessitiesTextView.setText((TripPlanning.getNecessities() == null) ? ("No necessities selected.") : (TripPlanning.getNecessities()));
        selectedParticipantsTextView.setText((TripPlanning.getParticipantsDescription() == null) ? ("No participants selected.") : (TripPlanning.getParticipantsDescription()));

        countryButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new CountryFragment());
            fragmentTransaction.commit();
        });

        cityButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new CityFragment());
            fragmentTransaction.commit();
        });

        locationButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AccommodationFragment());
            fragmentTransaction.commit();
        });

        travellingModeButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new TravellingModeFragment());
            fragmentTransaction.commit();
        });

        dateButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new DateFragment());
            fragmentTransaction.commit();
        });

        necessitiesButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new TravellingNecessitiesFragment());
            fragmentTransaction.commit();
        });

        participantsButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new ParticipantsFragment());
            fragmentTransaction.commit();
        });

        return view;
    }
}