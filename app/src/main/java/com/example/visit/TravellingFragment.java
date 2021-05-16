package com.example.visit;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TravellingFragment extends Fragment implements OnMapReadyCallback {
    // TODO: move to local.properties (so it becomes a hidden variable)
    private static final String MAPVIEW_BUNDLE_KEY = "AIzaSyArpz0rGtzrmJHGxEz-FB71GhnasO2wz0I";
    // Integer permission descriptors?
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9002;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003;

    private boolean locationPermissionGranted = true;
    private MapView mapView;
    private GoogleMap googleMap;

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

    // Pretty self-explanatory, gets the whole checking process rolling
    private boolean checkMapServices() {
        if (isGoogleServicesAvailable() && isGPSEnabled()) {
            return true;
        }

        return false;
    }

    // Returns true if the device is able to use Google services (GMaps API etc.)
    // Shows dialog if Google services are not available
    public boolean isGoogleServicesAvailable() {
        Log.d("CHECK_GOOGLE_SERVICES", "isGoogleServicesAvailable: Checking Google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Objects.requireNonNull(getActivity()));

        if (available == ConnectionResult.SUCCESS) {
            // Google services available, user can make Google related requests (for example GMaps API)
            Log.d("GOOGLE_SERVICES", "isGoogleServicesAvailable: Google services are available");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            // Error that can be resolved occured
            Log.d("GOOGLE_SERVICES", "isGoogleServicesAvailable: Resolvable error");
            Objects.requireNonNull(GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST)).show();
        }

        return false;
    }

    // Returns true if the device GPS is enabled
    // Returns false and calls buildAlertMessageNoGPS method otherwise
    public boolean isGPSEnabled() {
        final LocationManager locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGPS();
            return false;
        }

        return true;
    }

    // Alert shown on no-GPS
    private void buildAlertMessageNoGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("This part of the application requires GPS to work properly. Do you want to enable it?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // onClick event for the "Yes" button - it opens settings page for enabling GPS
                        Intent enableGPSIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                        // We will get activity result in onActivityResult overridden method
                        startActivityForResult(enableGPSIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });

        // Show the created dialog
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (!locationPermissionGranted) {
                    getLocationPermission();
                }
            }
        }
    }

    // Requests location permission so that we can get the location of the device
    // Result of the permission handled by onRequestPermissionsResult
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()).getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Fine location permission already granted in the past
            locationPermissionGranted = true;
        } else {
            // Generates a pop-up that asks for the permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    // Method called upon granting or denying the permission for fine location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        locationPermissionGranted = false;

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
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
    public void onSaveInstanceState(@NotNull Bundle outState) {
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

        if (googleMap != null && locationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
        }

        if (checkMapServices()) {
            if (locationPermissionGranted) {
                mapView.onResume();
            } else {
                getLocationPermission();
            }
        }
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
        googleMap = map;

        // TODO show destination location instead of Rijeka
        map.addMarker(new MarkerOptions()
                .position(new LatLng(45.34306, 14.40917))
                .title("Trip destination"));

        // TODO show current location
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
            return;
        }

        map.setMyLocationEnabled(true);
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