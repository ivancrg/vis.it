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

import com.google.android.material.textfield.TextInputEditText;

public class TravellingModeFragment extends Fragment {

    public TravellingModeFragment() {
        // Required empty public constructor
    }

    public static TravellingModeFragment newInstance() {
        TravellingModeFragment fragment = new TravellingModeFragment();
        Bundle args = new Bundle();
        return fragment;
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
        TextView continue_exploring = (TextView) view.findViewById(R.id.continue_text);

        if(TripPlanning.getTravellingMode() != null) {
            modeEdit.setText(TripPlanning.getTravellingMode());
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //destination string holds the city user picked
                String mode = modeEdit.getText().toString();
                if (mode.length() > 0){
                    TripPlanning.setTravellingMode(mode);

                    FragmentTransaction fragmentTransaction = getActivity()
                            .getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new DateFragment());
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(view.getContext(), "Input travelling mode!", Toast.LENGTH_LONG).show();
                }
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