package com.example.visit.planning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.visit.MainActivity;
import com.example.visit.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AccommodationFragment extends Fragment {

    public AccommodationFragment() {
        // Required empty public constructor
    }

    public static AccommodationFragment newInstance() {
        return new AccommodationFragment();
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_accommodation, container, false);

        TextInputEditText accommodationEdit = (TextInputEditText) view.findViewById(R.id.accommodationEdit);
        Button next = (Button) view.findViewById(R.id.next_accommodation);
        Button cancel = (Button) view.findViewById(R.id.cancel_accommodation);
        TextView continueExploring = (TextView) view.findViewById(R.id.continue_text);

        if (TripPlanning.getLocation() != null) {
            accommodationEdit.setText(TripPlanning.getLocation());
        }

        next.setOnClickListener(view -> {
            //destination string holds the city user picked
            String accommodation = Objects.requireNonNull(accommodationEdit.getText()).toString();
            if (accommodation.length() > 0) {
                TripPlanning.setLocation(accommodation);

                MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new TravellingModeFragment(), true);
            } else {
                Toast.makeText(view.getContext(), "Input accommodation!", Toast.LENGTH_LONG).show();
            }
        });

        cancel.setOnClickListener(view -> {
            MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new TripPlannerFragment(), true);
        });

        continueExploring.setOnClickListener(view -> {
            // TODO
            //needs to be forwarded to Explore fragment
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}