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
    TextView location, temperature, humidity, pressure, wind;
    ImageView image;
    String lat, lon;
    String icon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_travelling_weather, container, false);

        location = view.findViewById(R.id.location);
        temperature = view.findViewById(R.id.temperature);
        humidity = view.findViewById(R.id.humidity);
        pressure = view.findViewById(R.id.pressure);
        wind = view.findViewById(R.id.wind);
        image = view.findViewById(R.id.image);

        //get lat and lon from destination city
        lat = "21.75";
        lon = "46.3333";

        getWeatherAPI();

        return view;
    }

    private void getWeatherAPI() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&exclude=daily&appid=498a9c26d96534a84e001c916b65855c&units=metric")
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
                                JSONObject total = new JSONObject(myResponse);
                                JSONObject current = total.getJSONObject("current");

                                //set temperature textbox to current value
                                temperature.setText(current.getString("temp") + "°C");
                                humidity.setText("Humidity: " + current.getString("humidity"));
                                pressure.setText("Pressure: " + current.getString("pressure"));
                                wind.setText("Wind speed: " + current.getString("wind_speed"));

                                //set location to destination city
                                location.setText("Rijeka");

                                //set image according to weather forecast
                                icon = current.getJSONArray("weather").getJSONObject(0).getString("icon");

                                switch(icon) {
                                    case "01d": case "01n":
                                        image.setImageResource(R.drawable.sun);
                                        break;
                                    case "02d": case "02n": case "03d": case "03n":
                                        image.setImageResource(R.drawable.sun_cloud);
                                        break;
                                    case "13d": case "13n":
                                        image.setImageResource(R.drawable.snow);
                                        break;
                                    default:
                                        image.setImageResource(R.drawable.rain);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), "Sorry, the weather forecast is not available.", Toast.LENGTH_SHORT).show();
                            FragmentTransaction fragmentTransaction = getActivity()
                                    .getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, new TravellingFragment());
                            fragmentTransaction.commit();
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