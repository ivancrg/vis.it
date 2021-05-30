package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class TravellingNecessitiesFragment extends Fragment {

    public TravellingNecessitiesFragment() {
        // Required empty public constructor
    }

    public static TravellingNecessitiesFragment newInstance() {
        TravellingNecessitiesFragment fragment = new TravellingNecessitiesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_travelling_necessities, container, false);

        TextInputEditText necessitiesEdit = (TextInputEditText) view.findViewById(R.id.necessitiesEdit);
        Button next = (Button) view.findViewById(R.id.next_necessities);
        Button cancel = (Button) view.findViewById(R.id.cancel_necessities);
        TextView continue_exploring = (TextView) view.findViewById(R.id.continue_text);

        if (TripPlanning.getNecessities() != null) {
            necessitiesEdit.setText(TripPlanning.getNecessities());
        }

        next.setOnClickListener(view -> {
            //destination string holds the city user picked
            String necessities = Objects.requireNonNull(necessitiesEdit.getText()).toString();
            //  if user writes something in textbox, save it, but it is not required
            if (necessities.length() > 0) {
                TripPlanning.setNecessities(necessities);
            }

            MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new ParticipantsFragment(), true);
        });
        cancel.setOnClickListener(view -> {
            MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new TripPlannerFragment(), true);
        });

        continue_exploring.setOnClickListener(view -> {
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