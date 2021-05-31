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

public class TravellingModeFragment extends Fragment {

    public TravellingModeFragment() {
        // Required empty public constructor
    }

    public static TravellingModeFragment newInstance() {
        return new TravellingModeFragment();
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_travelling_mode, container, false);

        TextInputEditText modeEdit = (TextInputEditText) view.findViewById(R.id.modeEdit);
        Button next = (Button) view.findViewById(R.id.next_mode);
        Button cancel = (Button) view.findViewById(R.id.cancel_mode);
        TextView continueExploring = (TextView) view.findViewById(R.id.continue_text);

        if (TripPlanning.getTravellingMode() != null) {
            modeEdit.setText(TripPlanning.getTravellingMode());
        }

        next.setOnClickListener(view -> {
            //destination string holds the city user picked
            String mode = Objects.requireNonNull(modeEdit.getText()).toString();
            if (mode.length() > 0) {
                TripPlanning.setTravellingMode(mode);

                MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new DateFragment(), true);
            } else {
                Toast.makeText(view.getContext(), "Input travelling mode!", Toast.LENGTH_LONG).show();
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