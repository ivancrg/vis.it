package com.example.visit;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TravellingTimeFragment extends Fragment {

    public TravellingTimeFragment() {
        // Required empty public constructor
    }

    public static TravellingTimeFragment newInstance() {
        TravellingTimeFragment fragment = new TravellingTimeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    View view;
    TextView homeTime, currentTime, destinationTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_travelling_time, container, false);

        homeTime = view.findViewById(R.id.home_time);
        currentTime = view.findViewById(R.id.local_time);
        destinationTime = view.findViewById(R.id.destination_time);


        //get current city name from lat and long
        Geocoder geocoder1 = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder1.getFromLocation(32, 75, 1);
            String city = addresses.get(0).getLocality();
            Log.d("grad", city);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //get current city lat and long from name
        Geocoder geocoder2 = new Geocoder(getContext(), Locale.getDefault());
        List<Address> listOfAddress;
        try {
            listOfAddress = geocoder2.getFromLocationName("Rijeka", 1);
            if(listOfAddress != null && !listOfAddress.isEmpty()){
                Address address = listOfAddress.get(0);

                double latitude = address.getLatitude();
                double longitude = address.getLongitude();

                Log.d("koordinate Rijeke", latitude + " " + longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        L

        getTimeZoneAPI();

        return view;
    }

    private void getTimeZoneAPI() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/timezone/json?location=39.6034810,-119.6822510&timestamp=1331161200&key=AIzaSyArpz0rGtzrmJHGxEz-FB71GhnasO2wz0I")
                .method("GET", null)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject timeZone = new JSONObject(myResponse);
                                Log.d("zona", timeZone.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), "Time not available!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}