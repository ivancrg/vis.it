package com.example.visit.travelling;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.visit.MainActivity;
import com.example.visit.R;
import com.example.visit.database.CurrentTripGet;
import com.example.visit.database.Database;
import com.example.visit.database.HerokuAPI;
import com.example.visit.destination.DestinationFragment;
import com.example.visit.user_profile.LoggedUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    // private LatLng homeCoordinates;
    private LatLng deviceCoordinates;
    private LatLng destinationCoordinates;
    private Double distanceToDestination;
    private ProgressBar progressBar;
    private TextView progressBarTextView;
    private Button onDestinationButton;

    public TravellingFragment() {
        // Required empty public constructor
    }

    Bundle args;
    String destinationCity;
    String destinationCountry;
    double destinationCityLat = 0;
    double destinationCityLng = 0;
    RecyclerViewItemMyTrips tripDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        args = new Bundle();
        View view = inflater.inflate(R.layout.fragment_travelling, container, false);
        mapView = (MapView) view.findViewById(R.id.travellingFragmentMapView);
        Button arrivedButton = (Button) view.findViewById(R.id.travellingFragmentArrivedButton);
        ImageView weatherIcon = (ImageView) view.findViewById(R.id.travellingFragmentWeatherIcon);
        ImageView musicIcon = (ImageView) view.findViewById(R.id.travellingFragmentMusicIcon);
        ImageView clockIcon = (ImageView) view.findViewById(R.id.travellingFragmentClockIcon);
        progressBar = view.findViewById(R.id.travellingFragmentProgressBar);
        progressBarTextView = view.findViewById(R.id.travellingFragmentProgressBarText);
        onDestinationButton = view.findViewById(R.id.travellingFragmentArrivedButton);

        initGoogleMap(savedInstanceState);
        initGeolocation();

        arrivedButton.setOnClickListener(view1 -> {
            Toast.makeText(getContext(), "TEMP_TEXT_NOT_IMPLEMENTED Congrats!", Toast.LENGTH_SHORT).show();
        });

        weatherIcon.setOnClickListener(view12 -> {
            // Sending destinationCity and destinationCountry to TravellingWeatherFragment
            TravellingWeatherFragment fragmentWeatherTravelling = new TravellingWeatherFragment();
            fragmentWeatherTravelling.setArguments(args);

            MainActivity.changeFragment(requireActivity().getSupportFragmentManager(), fragmentWeatherTravelling, true);
        });

        musicIcon.setOnClickListener(view13 -> {
            // Sending destinationCity and destinationCountry to TravellingMusicFragment
            TravellingMusicFragment fragmentMusicTravelling = new TravellingMusicFragment();
            fragmentMusicTravelling.setArguments(args);

            MainActivity.changeFragment(requireActivity().getSupportFragmentManager(), fragmentMusicTravelling, true);
        });

        clockIcon.setOnClickListener(view14 -> {
            // Sending destinationCity and destinationCountry to TravellingTimeFragment
            TravellingTimeFragment fragmentTimeTravelling = new TravellingTimeFragment();
            fragmentTimeTravelling.setArguments(args);

            MainActivity.changeFragment(requireActivity().getSupportFragmentManager(), fragmentTimeTravelling, true);
        });

        onDestinationButton.setOnClickListener(v -> {
            MainActivity.changeFragment(requireActivity().getSupportFragmentManager(), new DestinationFragment(), false);
        });

        return view;
    }

    private void refreshProgressBarValue(double distance) {
        /*
        TODO: When real home coordinates become available, distance should be shown as
              a percentage of distance covered vs total distance, i.e. if I travelled
              80km out of 100km total, percentage should be 80/100.
              Real home coordinates are not available at the time of writing this comment.
        */

        // TODO: set homeDestinationDistance to real information (in kilometers)
        double homeDestinationDistance = 10000;

        // Converting meters to kilometers
        distance /= 1000.0;

        if (distance > homeDestinationDistance) {
            progressBar.setProgress(0);
            progressBarTextView.setText("0%");
        } else {
            progressBar.setProgress((int) ((homeDestinationDistance - distance) / 10000.0 * 100.0));
            progressBarTextView.setText(String.format("%.4s%%", (homeDestinationDistance - distance) / 10000.0 * 100.0));
        }
    }

    private void getShowDestinationCoordinates() {
        // Get current trip from database
        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);
        Call<CurrentTripGet> call = herokuAPI.getCurrentTrip(LoggedUser.getUsername());

        call.enqueue(new Callback<CurrentTripGet>() {
            @Override
            public void onResponse(@NotNull Call<CurrentTripGet> call, @NotNull Response<CurrentTripGet> response) {
                // Response received
                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/readOnTrip", "notSuccessful: Something went wrong. " + response.code());
                    Toast.makeText(requireView().getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Response code marks the completion of request as successful
                assert response.body() != null;
                CurrentTripGet currentTripGet = response.body();

                if (currentTripGet.getFeedback().equals("currently_not_on_trip")) {
                    // Ask user to pick the trip first
                    Toast.makeText(getContext(), "Choose the trip you want to start first!", Toast.LENGTH_SHORT).show();
                    MainActivity.changeFragment(requireActivity().getSupportFragmentManager(), new MyTripsFragment(), false);
                } else if (currentTripGet.getFeedback().equals("currently_on_trip")) {
                    // GET destination country and city from database
                    tripDetails = currentTripGet.getTripDetails();
                    destinationCity = tripDetails.getCity();
                    destinationCountry = tripDetails.getCountry();

                    //Get destination city lat and long from name
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> listOfAddress;
                    try {
                        listOfAddress = geocoder.getFromLocationName(destinationCity, 1);
                        if (listOfAddress != null && !listOfAddress.isEmpty()) {
                            Address address = listOfAddress.get(0);

                            destinationCityLat = address.getLatitude();
                            destinationCityLng = address.getLongitude();
                            destinationCoordinates = new LatLng(destinationCityLat, destinationCityLng);

                            distanceToDestination = SphericalUtil.computeDistanceBetween(deviceCoordinates, destinationCoordinates);

                            // Updates the progress in progress bar
                            refreshProgressBarValue(distanceToDestination);

                            // Actually showing the destination on map
                            googleMap.addMarker(new MarkerOptions()
                                    .position(destinationCoordinates)
                                    .title("Trip destination"));
                        }
                    } catch (IOException e) {
                        // If this error happens emulator needs to be restarted
                        Log.e("grpc failed", "Emulator error, please restart and cold boot device!");
                        e.printStackTrace();
                    }

                    // Used for forwarding city lat and lng to music, weather and time fragment
                    requireActivity().runOnUiThread(() -> {
                        args.putString("destinationCity", destinationCity);
                        args.putString("destinationCountry", destinationCountry);
                        args.putDouble("destinationCityLat", destinationCityLat);
                        args.putDouble("destinationCityLng", destinationCityLng);
                    });
                } else {
                    // API did not return any trip data (because of a database error or because the user has no trips saved)
                    Toast.makeText(requireView().getContext(), "No trips available.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CurrentTripGet> call, @NotNull Throwable t) {
                // Request towards vis.it API could not be completed
                Toast.makeText(requireView().getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                Log.e("/readOnTrip", "onFailure: Something went wrong. " + t.getMessage());
            }
        });
    }

    private void initGeolocation() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        // Location listener - it defines what is executed when our location changes by specified parameters
        LocationListener locationListener = location -> {
            // Location listener - it defines what is executed when our location changes by specified parameters

            deviceCoordinates = new LatLng(location.getLatitude(), location.getLongitude());

            getShowDestinationCoordinates();
        };

        if (locationPermissionGranted) {
            getLocationPermission();
        }

        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates("gps", (long) 5 * 1000, 10, locationListener);
    }

    // Pretty self-explanatory, gets the whole checking process rolling
    private boolean checkMapServices() {
        return isGoogleServicesAvailable() && isGPSEnabled();
    }

    // Returns true if the device is able to use Google services (GMaps API etc.)
    // Shows dialog if Google services are not available
    public boolean isGoogleServicesAvailable() {
        Log.d("CHECK_GOOGLE_SERVICES", "isGoogleServicesAvailable: Checking Google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(requireActivity());

        if (available == ConnectionResult.SUCCESS) {
            // Google services available, user can make Google related requests (for example GMaps API)
            Log.d("GOOGLE_SERVICES", "isGoogleServicesAvailable: Google services are available");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            // Error that can be resolved occured
            Log.d("GOOGLE_SERVICES", "isGoogleServicesAvailable: Resolvable error");
            Objects.requireNonNull(GoogleApiAvailability.getInstance().getErrorDialog(requireActivity(), available, ERROR_DIALOG_REQUEST)).show();
        }

        return false;
    }

    // Returns true if the device GPS is enabled
    // Returns false and calls buildAlertMessageNoGPS method otherwise
    public boolean isGPSEnabled() {
        final LocationManager lm = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
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
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    // onClick event for the "Yes" button - it opens settings page for enabling GPS
                    Intent enableGPSIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                    // We will get activity result in onActivityResult overridden method
                    startActivityForResult(enableGPSIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                });

        // Show the created dialog
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PERMISSIONS_REQUEST_ENABLE_GPS && !locationPermissionGranted) {
            getLocationPermission();
        }
    }

    // Requests location permission so that we can get the location of the device
    // Result of the permission handled by onRequestPermissionsResult
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Fine location permission already granted in the past
            locationPermissionGranted = true;
        } else {
            // Generates a pop-up that asks for the permission
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    // Method called upon granting or denying the permission for fine location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        locationPermissionGranted = false;

        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {// If request is cancelled, the result arrays are empty
            locationPermissionGranted = true;
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
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        initGeolocation();
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
    public void onMapReady(@NotNull GoogleMap map) {
        googleMap = map;

        // Showing device location
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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