package com.example.visit.planning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.visit.MainActivity;
import com.example.visit.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ParticipantsFragment extends Fragment {

    public ParticipantsFragment() {
        // Required empty public constructor
    }

    public static ParticipantsFragment newInstance() {
        return new ParticipantsFragment();
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_participants, container, false);

        TextInputEditText participantsEdit = (TextInputEditText) view.findViewById(R.id.participantsEdit);
        Button next = (Button) view.findViewById(R.id.next_participants);
        Button cancel = (Button) view.findViewById(R.id.cancel_participants);
        TextView continueExploring = (TextView) view.findViewById(R.id.continue_text);

        if (TripPlanning.getParticipantsDescription() != null) {
            participantsEdit.setText(TripPlanning.getParticipantsDescription());
        }

        next.setOnClickListener(view -> {
            // Destination string holds the city user picked
            String participants = participantsEdit.getText().toString();
            // If user writes something in textbox, save it, but it is not required
            if (participants.length() > 0) {
                TripPlanning.setParticipantsDescription(participants);
            }

            MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new TripPlannerFragment(), true);
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