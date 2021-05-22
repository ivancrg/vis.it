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

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

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
    String time = "00:00";
    String currentTimeDate;
    double latitude, longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_travelling_time, container, false);

        homeTime = view.findViewById(R.id.home_time);
        currentTime = view.findViewById(R.id.local_time);
        destinationTime = view.findViewById(R.id.destination_time);

        //Getting destination city from TravellingFragment
        Bundle args = this.getArguments();
        String destinationCity = args.getString("destinationCity");
        latitude = args.getDouble("destinationCityLat");
        longitude = args.getDouble("destinationCityLng");

        //Update destination and current time every 10 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Setting current time
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentTimeDate = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                        currentTime.setText(currentTimeDate);
                    }
                });
                //Setting destination time
                getTimeZoneAPI();
            }
        }, 0, 10000);

        //TODO set home time

        return view;
    }

    private void getTimeZoneAPI() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.timezonedb.com/v2.1/get-time-zone?key=I7JVY5MYINC6&format=json&by=position&lat="+latitude+"&lng="+longitude+"")
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
                            try {
                                JSONObject timeObject = new JSONObject(myResponse);
                                time = timeObject.get("formatted").toString();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        destinationTime.setText(time.substring(time.length() - 8, time.length() - 3));
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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