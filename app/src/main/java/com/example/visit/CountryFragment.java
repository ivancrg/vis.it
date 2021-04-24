package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hbb20.CountryPickerView;

public class CountryFragment extends Fragment {

    public CountryFragment() {
        // Required empty public constructor
    }

    public static CountryFragment newInstance() {
        CountryFragment fragment = new CountryFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_country, container, false);
        picking();
        return view;
    }

    public void picking(){
        CountryPickerView country = view.findViewById(R.id.country_picker);
        Button button = view.findViewById(R.id.save_city);
        TextView continue_exploring = view.findViewById(R.id.continue_text);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //destination string holds the country user picked
                String destination_country = country.getCpViewHelper().getSelectedCountry().toString();
                TripPlanning.setCountry(destination_country);

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


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}