package com.example.visit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TravellingFragment extends Fragment implements OnMapReadyCallback {
    // TODO: move to local.properties (so it becomes a hidden variable)
    private static final String MAPVIEW_BUNDLE_KEY = "AIzaSyArpz0rGtzrmJHGxEz-FB71GhnasO2wz0I";
    // Integer permission descriptors?
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9002;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003;

    private MapView mapView;

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
        mapView = (MapView) view.findViewById(R.id.travellingFragmentMapView);
        Button arrivedButton = (Button) view.findViewById(R.id.travellingFragmentArrivedButton);
        ImageView weatherIcon = (ImageView) view.findViewById(R.id.travellingFragmentWeatherIcon);
        ImageView musicIcon = (ImageView) view.findViewById(R.id.travellingFragmentMusicIcon);
        ImageView clockIcon = (ImageView) view.findViewById(R.id.travellingFragmentClockIcon);

        initGoogleMap(savedInstanceState);

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

    // initGoogleMap used just as a way to clean up the code in onCreate method a little bit
    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // TODO show destination location instead of Rijeka
        map.addMarker(new MarkerOptions()
                .position(new LatLng(45.34306, 14.40917))
                .title("Trip destination"));

        // TODO show current location
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}