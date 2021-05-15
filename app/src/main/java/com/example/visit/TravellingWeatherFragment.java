package com.example.visit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TravellingWeatherFragment extends Fragment {

    public TravellingWeatherFragment() {
        // Required empty public constructor
    }

    public static TravellingWeatherFragment newInstance() {
        TravellingWeatherFragment fragment = new TravellingWeatherFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    View view;
    TextView location, temperature;
    ImageView image;
    String lat, lon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_travelling_weather, container, false);

        location = view.findViewById(R.id.location);
        temperature = view.findViewById(R.id.temperature);
        image = view.findViewById(R.id.image);

        //get lat and lon from destination city
        lat = "21.75";
        lon = "46.3333";

        getWeatherAPI();

        return view;
    }

    private void getWeatherAPI() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}