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

import com.hbb20.CountryPickerView;

public class CountryFragment extends Fragment {

    public CountryFragment() {
        // Required empty public constructor
    }

    public static CountryFragment newInstance() {
        CountryFragment fragment = new CountryFragment();

        return fragment;
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_country, container, false);

        CountryPickerView country = view.findViewById(R.id.country_picker);

        Button next = view.findViewById(R.id.next_country);
        Button cancel = view.findViewById(R.id.cancel_country);
        TextView continue_exploring = view.findViewById(R.id.continue_text);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //destination string holds the country user picked
                if (country.getCpViewHelper().getSelectedCountry().getValue() != null){
                    String destination_country = country.getCpViewHelper().getSelectedCountry().getValue().component3();
                    TripPlanning.setCountry(destination_country);

                    Bundle args = new Bundle();
                    args.putString("key", destination_country);
                    CityFragment fragmentCity = new CityFragment();
                    fragmentCity.setArguments(args);

                    FragmentTransaction fragmentTransaction = getActivity()
                            .getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragmentCity);
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(view.getContext(), "Choose destination country!", Toast.LENGTH_LONG).show();
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