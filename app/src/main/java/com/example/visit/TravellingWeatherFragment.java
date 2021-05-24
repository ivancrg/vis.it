package com.example.visit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.visit.weather.OpenWeatherMap;
import com.example.visit.weather.OpenWeatherMapAPI;
import com.example.visit.weather.WeatherData;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    double lat, lon;
    String icon, destinationCity;

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

        // Get lat and lng from destination city from bundle
        Bundle args = this.getArguments();
        assert args != null;
        destinationCity = args.getString("destinationCity");
        lat = args.getDouble("destinationCityLat");
        lon = args.getDouble("destinationCityLng");

        downloadWeatherData();

        return view;
    }

    private void downloadWeatherData(){
        Retrofit retrofit = OpenWeatherMap.getRetrofit();
        OpenWeatherMapAPI openWeatherMapAPI = retrofit.create(OpenWeatherMapAPI.class);
        Call<WeatherData> call = openWeatherMapAPI.getWeatherData(lat, lon, OpenWeatherMap.getApiKey(), OpenWeatherMap.UNITS.METRIC);

        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(@NotNull Call<WeatherData> call, @NotNull Response<WeatherData> response) {
                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/getWeatherData", "notSuccessful: Something went wrong. " + response.code());
                    Toast.makeText(getContext(), "Sorry, the weather forecast is not available.", Toast.LENGTH_SHORT).show();
                    return;
                }

                assert response.body() != null;
                WeatherData weatherData = response.body();
                Log.i("/getWeatherData", weatherData.toString());

                //set temperature textbox to current value
                temperature.setText(String.format("%s°C", weatherData.getCurrentWeatherData().getTemperature().toString()));
                humidity.setText(String.format("Humidity: %s%%", weatherData.getCurrentWeatherData().getHumidity().toString()));
                pressure.setText(String.format("Pressure: %shPa", weatherData.getCurrentWeatherData().getPressure().toString()));
                wind.setText(String.format("Wind speed: %sm/s", weatherData.getCurrentWeatherData().getWindSpeed().toString()));

                //set location to destination city
                location.setText(destinationCity);

                //set image according to weather forecast
                icon = weatherData.getCurrentWeatherData().getWeatherDescription().get(0).getIconID();

                switch (icon) {
                    case "01d":
                    case "01n":
                        image.setImageResource(R.drawable.sun);
                        break;
                    case "02d":
                    case "02n":
                    case "03d":
                    case "03n":
                        image.setImageResource(R.drawable.sun_cloud);
                        break;
                    case "13d":
                    case "13n":
                        image.setImageResource(R.drawable.snow);
                        break;
                    default:
                        image.setImageResource(R.drawable.rain);
                }
            }

            @Override
            public void onFailure(@NotNull Call<WeatherData> call, @NotNull Throwable t) {
                // Communication error, JSON parsing error, class configuration error...
                Log.e("/getWeatherData", "onFailure: Something went wrong. " + t.getMessage());
                Toast.makeText(getContext(), "Sorry, the weather forecast is not available.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}