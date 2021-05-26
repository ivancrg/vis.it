package com.example.visit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Line;
import com.anychart.core.utils.OrdinalZoom;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.visit.weather.HourlyForecastData;
import com.example.visit.weather.MinutelyForecastData;
import com.example.visit.weather.OpenWeatherMap;
import com.example.visit.weather.OpenWeatherMapAPI;
import com.example.visit.weather.WeatherData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
    AnyChartView chart1, chart2, chart3, chart4;
    LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4;
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

        // Charts
        // 1h rain precipitation
        chart1 = view.findViewById(R.id.travellingWeatherFragmentChart1);
        linearLayout1 = view.findViewById(R.id.travellingWeatherFragmentChartLinearLayout1);
        // 48h temperature/feels-like temperature
        chart2 = view.findViewById(R.id.travellingWeatherFragmentChart2);
        linearLayout1 = view.findViewById(R.id.travellingWeatherFragmentChartLinearLayout1);
        linearLayout2 = view.findViewById(R.id.travellingWeatherFragmentChartLinearLayout2);
        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);

        // Get lat and lng from destination city from bundle
        Bundle args = this.getArguments();
        assert args != null;
        destinationCity = args.getString("destinationCity");
        lat = args.getDouble("destinationCityLat");
        lon = args.getDouble("destinationCityLng");

        downloadWeatherData();

        return view;
    }

    private void downloadWeatherData() {
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

                createCharts(weatherData);
            }

            @Override
            public void onFailure(@NotNull Call<WeatherData> call, @NotNull Throwable t) {
                // Communication error, JSON parsing error, class configuration error...
                Log.e("/getWeatherData", "onFailure: Something went wrong. " + t.getMessage());
                Toast.makeText(getContext(), "Sorry, the weather forecast is not available.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Calls methods for creating charts related to weather forecasts
    private void createCharts(WeatherData weatherData) {
        if (weatherData.getMinutelyForecastData() != null) {
            createChart1(weatherData.getMinutelyForecastData());
        } else {
            Toast.makeText(getActivity(), "No minutely data available.", Toast.LENGTH_SHORT).show();
        }

        if (weatherData.getHourlyForecastData() != null) {
            createChart2(weatherData.getHourlyForecastData());
        } else {
            Toast.makeText(getActivity(), "No hourly data available.", Toast.LENGTH_SHORT).show();
        }
    }

    private void createChart1(ArrayList<MinutelyForecastData> minutelyForecastData) {
        APIlib.getInstance().setActiveAnyChartView(chart1);
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        for (MinutelyForecastData dataItem : minutelyForecastData) {
            data.add(new ValueDataEntry(dataItem.getTimeHHmm(), dataItem.getPrecipitationVolume()));
        }

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("Expected at {%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(0d)
                .format("Expected precipitation volume: {%Value}{groupsSeparator: }mm");

        cartesian.animation(true);
        cartesian.title("1 hour rain precipitation");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }mm");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xScroller(true);

        OrdinalZoom xZoom = cartesian.xZoom();

        xZoom.setToPointsCount(6, false, null);

        xZoom.getStartRatio();
        xZoom.getEndRatio();

        cartesian.xAxis(0).title("Time");
        cartesian.yAxis(0).title("Volume");

        chart1.setChart(cartesian);
        linearLayout1.setVisibility(View.VISIBLE);
    }

    private void createChart2(ArrayList<HourlyForecastData> hourlyForecastData) {
        APIlib.getInstance().setActiveAnyChartView(chart2);
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip()
                .titleFormat("Expected on {%fullTime}")
                .positionMode(TooltipPositionMode.POINT);

        cartesian.title("48h temperature/feels-like temperature");

        cartesian.yAxis(0).title("Temperature (°C)");
        cartesian.xAxis(0).title("Time");

        List<DataEntry> seriesData = new ArrayList<>();
        for (HourlyForecastData dataItem : hourlyForecastData) {
            seriesData.add(new TemperatureDataEntry(dataItem.getTimeHHmm(), dataItem.getTemperature(), dataItem.getTemperatureFeelsLike(), dataItem.getDateTime()));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value', label: 'fullTime' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Actual");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d)
                .format("Actual: {%Value}{groupsSeparator: }°C");;
        series1.stroke("blue");

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Feels-like");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .stroke("red")
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d)
                .format("Feels-like: {%Value}{groupsSeparator: }°C");
        series2.stroke("red");

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        cartesian.xScroller(true);

        OrdinalZoom xZoom = cartesian.xZoom();

        xZoom.setToPointsCount(6, false, null);

        xZoom.getStartRatio();
        xZoom.getEndRatio();


        chart2.setChart(cartesian);
        linearLayout2.setVisibility(View.VISIBLE);
    }

    private class TemperatureDataEntry extends ValueDataEntry {
        TemperatureDataEntry(String x, Number value, Number value2, String fullTime) {
            super(x, value);
            setValue("value2", value2);
            setValue("fullTime", fullTime);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}