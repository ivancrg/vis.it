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

public class AccommodationFragment extends Fragment {

    public AccommodationFragment() {
        // Required empty public constructor
    }

    public static AccommodationFragment newInstance() {
        AccommodationFragment fragment = new AccommodationFragment();
        Bundle args = new Bundle();
        return fragment;
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
        TextView continue_exploring = (TextView) view.findViewById(R.id.continue_text);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //destination string holds the city user picked
                String accommodation = accommodationEdit.getText().toString();
                if (!accommodation.equals(getString(R.string.link_accommodation))){
                    TripPlanning.setLocation(accommodation);

                    FragmentTransaction fragmentTransaction = getActivity()
                            .getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new TravellingModeFragment());
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(view.getContext(), "Input accommodation!", Toast.LENGTH_LONG).show();
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