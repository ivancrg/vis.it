package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

public class ParticipantsFragment extends Fragment {

    public ParticipantsFragment() {
        // Required empty public constructor
    }

    public static ParticipantsFragment newInstance() {
        ParticipantsFragment fragment = new ParticipantsFragment();
        Bundle args = new Bundle();
        return fragment;
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
        TextView continue_exploring = (TextView) view.findViewById(R.id.continue_text);

        if(TripPlanning.getParticipantsDescription() != null) {
            participantsEdit.setText(TripPlanning.getParticipantsDescription());
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //destination string holds the city user picked
                String participants = participantsEdit.getText().toString();
                //  if user writes something in textbox, save it, but it is not required
                if (participants.length() > 0) {
                    TripPlanning.setParticipantsDescription(participants);
                }

                    FragmentTransaction fragmentTransaction = getActivity()
                            .getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new TripPlannerFragment());
                    fragmentTransaction.commit();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new TripPlannerFragment());
                fragmentTransaction.commit();
            }
        });

        continue_exploring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                // TODO
                //needs to be forwarded to Explore fragment
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}