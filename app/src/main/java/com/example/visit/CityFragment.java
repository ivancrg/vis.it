package com.example.visit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.*;

public class CityFragment extends Fragment {

    public CityFragment() {
        // Required empty public constructor
    }

    public static CityFragment newInstance() {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_city, container, false);

        TextInputEditText city = (TextInputEditText) view.findViewById(R.id.cityEdit);
        Button next = (Button) view.findViewById(R.id.next_city);
        Button cancel = (Button) view.findViewById(R.id.cancel_city);
        TextView continue_exploring = (TextView) view.findViewById(R.id.continue_text);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //destination string holds the city user picked
                String destination_city = city.getText().toString();
                if (!destination_city.equals(getString(R.string.city_text))){
                    TripPlanning.setCity(destination_city);
                    FragmentTransaction fragmentTransaction = getActivity()
                            .getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new AccommodationFragment());
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(view.getContext(), "Choose destination city!", Toast.LENGTH_LONG).show();
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