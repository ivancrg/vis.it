package com.example.visit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import okhttp3.*;

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
    TextInputEditText city;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_city, container, false);

        city = (TextInputEditText) view.findViewById(R.id.cityEdit);
        Button next = (Button) view.findViewById(R.id.next_city);
        Button cancel = (Button) view.findViewById(R.id.cancel_city);
        TextView continue_exploring = (TextView) view.findViewById(R.id.continue_text);
        String destination_city = city.getText().toString();

        if (!Places.isInitialized()) {
            Places.initialize(view.getContext(), "AIzaSyA4bTb_BPnktpTpy4ePn6wHCyEY83yoBrA");
        }

        city.setFocusable(false);
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(view.getContext());
                startActivityForResult(intent, 100);
            }
        });

        if(TripPlanning.getCity() != null) {
            city.setText(TripPlanning.getCity());
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //destination string holds the city user picked
                if (destination_city.length() > 0){
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            city.setText(place.getAddress());
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(view.getContext(), status.getStatusMessage(), Toast.LENGTH_SHORT);
        }
    }
}