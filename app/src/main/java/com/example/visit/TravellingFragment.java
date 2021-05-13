package com.example.visit;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;

public class TravellingFragment extends Fragment {
    public TravellingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travelling, container, false);

        MapView mapView = (MapView) view.findViewById(R.id.travellingFragmentMapView);
        Button arrivedButton = (Button) view.findViewById(R.id.travellingFragmentArrivedButton);
        ImageView weatherIcon = (ImageView) view.findViewById(R.id.travellingFragmentWeatherIcon);
        ImageView musicIcon = (ImageView) view.findViewById(R.id.travellingFragmentMusicIcon);
        ImageView clockIcon = (ImageView) view.findViewById(R.id.travellingFragmentClockIcon);

        arrivedButton.setOnClickListener(view1 -> {
            Toast.makeText(getContext(), "TEMP_TEXT_NOT_IMPLEMENTED Congrats!", Toast.LENGTH_SHORT).show();
        });

        weatherIcon.setOnClickListener(view12 -> {
            Toast.makeText(getContext(), "You need to switch fragments now. Line 44 TravellingFragment.java", Toast.LENGTH_LONG).show();
        });

        musicIcon.setOnClickListener(view13 -> {
            Toast.makeText(getContext(), "You need to switch fragments now. Line 48 TravellingFragment.java", Toast.LENGTH_LONG).show();
        });

        clockIcon.setOnClickListener(view14 -> {
            Toast.makeText(getContext(), "You need to switch fragments now. Line 52 TravellingFragment.java", Toast.LENGTH_LONG).show();
        });

        return view;
    }
}