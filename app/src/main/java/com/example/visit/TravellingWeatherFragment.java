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
import com.anychart.enums.Orientation;
import com.anychart.enums.Position;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.anychart.scales.Linear;
import com.example.visit.weather.DailyForecastData;
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
        return new TravellingWeatherFragment();
    }

    View view;
    TextView location;
    TextView description;
    TextView temperature;
    TextView humidity;
    TextView pressure;
    TextView windSpeed;
    TextView windDirection;
    ImageView image;
    AnyChartView chart1;
    AnyChartView chart2;
    AnyChartView chart3;
    AnyChartView chart4;
    AnyChartView chart5;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    LinearLayout linearLayout4;
    LinearLayout linearLayout5;
    double lat;
    double lon;
    String icon;
    String destinationCity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_travelling_weather, container, false);

        location = view.findViewById(R.id.location);
        description = view.findViewById(R.id.description);
        temperature = view.findViewById(R.id.temperature);
        humidity = view.findViewById(R.id.humidity);
        pressure = view.findViewById(R.id.pressure);
        windSpeed = view.findViewById(R.id.windSpeed);
        windDirection = view.findViewById(R.id.windDirection);
        image = view.findViewById(R.id.image);

        // Charts
        // 1h expected rain volume
        chart1 = view.findViewById(R.id.travellingWeatherFragmentChart1);
        linearLayout1 = view.findViewById(R.id.travellingWeatherFragmentChartLinearLayout1);
        // 48h actual/feels-like temperature
        chart2 = view.findViewById(R.id.travellingWeatherFragmentChart2);
        linearLayout2 = view.findViewById(R.id.travellingWeatherFragmentChartLinearLayout2);
        // 48h probability of precipitation
        chart3 = view.findViewById(R.id.travellingWeatherFragmentChart3);
        linearLayout3 = view.findViewById(R.id.travellingWeatherFragmentChartLinearLayout3);
        // 7 day actual/feels-like temperature
        chart4 = view.findViewById(R.id.travellingWeatherFragmentChart4);
        linearLayout4 = view.findViewById(R.id.travellingWeatherFragmentChartLinearLayout4);
        // 7 day probability of precipitation + rain/snow volume
        chart5 = view.findViewById(R.id.travellingWeatherFragmentChart5);
        linearLayout5 = view.findViewById(R.id.travellingWeatherFragmentChartLinearLayout5);

        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.GONE);
        linearLayout4.setVisibility(View.GONE);
        linearLayout5.setVisibility(View.GONE);

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

                // Display name of destination city
                location.setText(destinationCity);

                // Making sure that the first letter of the description is always
                // upper case and all the other letters lower case
                String desc = Character.toUpperCase(weatherData.getCurrentWeatherData().getWeatherDescription().get(0).getDescription().charAt(0))
                        + weatherData.getCurrentWeatherData().getWeatherDescription().get(0).getDescription().substring(1).toLowerCase();

                // Display current weather conditions
                description.setText(desc);
                temperature.setText(String.format("%s°C", weatherData.getCurrentWeatherData().getTemperature().toString()));
                humidity.setText(String.format("%s%%", weatherData.getCurrentWeatherData().getHumidity().toString()));
                pressure.setText(String.format("%shPa", weatherData.getCurrentWeatherData().getPressure().toString()));
                windSpeed.setText(String.format("%sm/s", weatherData.getCurrentWeatherData().getWindSpeed().toString()));
                windDirection.setText(String.format("%s°", weatherData.getCurrentWeatherData().getWindDirection().toString()));


                // Display image according to weather conditions
                icon = weatherData.getCurrentWeatherData().getWeatherDescription().get(0).getIconID();

                switch (icon) {
                    case "01d":
                        image.setImageResource(R.drawable.weather_icon_sun);
                        break;
                    case "01n":
                        image.setImageResource(R.drawable.weather_icon_moon);
                        break;
                    case "02d":
                        image.setImageResource(R.drawable.weather_icon_sun_cloud);
                        break;
                    case "02n":
                        image.setImageResource(R.drawable.weather_icon_moon_cloud);
                        break;
                    case "03d":
                    case "03n":
                        image.setImageResource(R.drawable.weather_icon_cloud);
                        break;
                    case "04d":
                    case "04n":
                        image.setImageResource(R.drawable.weather_icon_cloud_foggy);
                        break;
                    case "09d":
                    case "09n":
                        image.setImageResource(R.drawable.weather_icon_cloud_more_rain);
                        break;
                    case "10d":
                        image.setImageResource(R.drawable.weather_icon_sun_cloud_rain);
                        break;
                    case "10n":
                        image.setImageResource(R.drawable.weather_icon_moon_cloud_rain);
                        break;
                    case "11d":
                        image.setImageResource(R.drawable.weather_icon_sun_cloud_thunder_rain);
                        break;
                    case "11n":
                        image.setImageResource(R.drawable.weather_icon_moon_cloud_thunder_rain);
                        break;
                    case "13d":
                    case "13n":
                        image.setImageResource(R.drawable.weather_icon_snow);
                        break;
                    case "50d":
                    case "50n":
                        image.setImageResource(R.drawable.weather_icon_foggy);
                        break;
                    default:
                        image.setImageResource(R.drawable.weather_icon_sun_cloud_drop);
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
            createChart3(weatherData.getHourlyForecastData());
        } else {
            Toast.makeText(getActivity(), "No hourly data available.", Toast.LENGTH_SHORT).show();
        }

        if (weatherData.getDailyForecastData() != null) {
            createChart4(weatherData.getDailyForecastData());
            createChart5(weatherData.getDailyForecastData());
        } else {
            Toast.makeText(getActivity(), "No daily data available.", Toast.LENGTH_SHORT).show();
        }
    }

    // Creates chart 1 - 1h expected volume of precipitation
    private void createChart1(ArrayList<MinutelyForecastData> minutelyForecastData) {
        // Selecting chart 1 - without it, the last selected chart changes instead
        APIlib.getInstance().setActiveAnyChartView(chart1);

        // Cartesian system with column chart
        Cartesian cartesian = AnyChart.column();

        // Preparing data for binding to chart
        List<DataEntry> data = new ArrayList<>();
        for (MinutelyForecastData dataItem : minutelyForecastData) {
            data.add(new ValueDataEntry(dataItem.getTimeHHmm(), dataItem.getPrecipitationVolume()));
        }

        // Adding data to the chart system
        Column column = cartesian.column(data);

        // Defining preferences when a column is selected
        column.tooltip()
                // {%X} - X value of selected column
                .titleFormat("Expected at {%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(0d)
                // {%Value} - Y value of selected column
                .format("Expected precipitation volume: {%Value}{groupsSeparator: }mm");

        // Starting animation
        cartesian.animation(true);

        cartesian.title("1h volume of precipitation");

        // Scale of y - minimum and maximum Y values (selected to minimum span)
        cartesian.yScale().minimum(0d);

        // Labels along y axis
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }mm");

        // Zoom, interactivity, horizontal scrolling
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.xScroller(true);
        OrdinalZoom xZoom = cartesian.xZoom();
        xZoom.setToPointsCount(6, false, null);
        xZoom.getStartRatio();
        xZoom.getEndRatio();

        // Axis titles
        cartesian.xAxis(0).title("Time");
        cartesian.yAxis(0).title("Volume");

        // Placing the configured system to wanted chart
        chart1.setChart(cartesian);

        // Setting the linear layout that wraps the chart to visible
        linearLayout1.setVisibility(View.VISIBLE);
    }

    // Creates chart 2 - 48h actual/feels-like temperature
    private void createChart2(ArrayList<HourlyForecastData> hourlyForecastData) {
        // Most of the command's meanings can be found in comments of function createChart1

        APIlib.getInstance().setActiveAnyChartView(chart2);

        // Cartesian system will be shown as a line chart
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

        cartesian.yAxis(0).title("Temperature");
        cartesian.yAxis(0).labels().format("{%Value}°C");
        cartesian.xAxis(0).title("Time");

        List<DataEntry> seriesData = new ArrayList<>();
        for (HourlyForecastData dataItem : hourlyForecastData) {
            seriesData.add(new TemperatureDataEntry(dataItem.getTimeHHmm(), dataItem.getTemperature(), dataItem.getTemperatureFeelsLike(), dataItem.getDateTime()));
        }

        // Data sets for each of the lines
        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        // Actual temperature line
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
                .format("Actual: {%Value}{groupsSeparator: }°C");
        series1.stroke("green");

        // Feels-like temperature line
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

    // Creating chart 3 - 48h probability of precipitation
    private void createChart3(ArrayList<HourlyForecastData> hourlyForecastData) {
        // Almost identical to createChart1 - I didn't add any explanations because
        // they can be found in the mentioned method

        APIlib.getInstance().setActiveAnyChartView(chart3);
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        for (HourlyForecastData dataItem : hourlyForecastData) {
            data.add(new PrecipitationDataEntry(dataItem.getTimeHHmm(), dataItem.getProbabilityOfPrecipitation() * 100, dataItem.getDateTime()));
        }

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("Expected at {%fullTime}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(0d)
                .format("Probability of precipitation: {%Value}{groupsSeparator: }%");

        cartesian.animation(true);
        cartesian.title("48h probability of precipitation");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }%");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xScroller(true);

        OrdinalZoom xZoom = cartesian.xZoom();

        xZoom.setToPointsCount(6, false, null);

        xZoom.getStartRatio();
        xZoom.getEndRatio();

        cartesian.xAxis(0).title("Time");
        cartesian.yAxis(0).title("Probability");

        chart3.setChart(cartesian);
        linearLayout3.setVisibility(View.VISIBLE);
    }

    // Creates chart 4 - 7 day actual/feels-like temperature
    private void createChart4(ArrayList<DailyForecastData> dailyForecastData) {
        // Most of the command's meanings can be found in comments of function createChart1 and createChart2

        APIlib.getInstance().setActiveAnyChartView(chart4);

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

        cartesian.title("7 day temperature/feels-like temperature");

        cartesian.yAxis(0).title("Temperature");
        cartesian.yAxis(0).labels().format("{%Value}°C");
        cartesian.xAxis(0).title("Day");

        List<DataEntry> seriesData = new ArrayList<>();
        for (DailyForecastData dataItem : dailyForecastData) {
            seriesData.add(new TemperatureDataEntry(dataItem.getTimeddMM(), dataItem.getTemperature().getDay(), dataItem.getTemperatureFeelsLike().getDay(), dataItem.getDateTime()));
        }

        // Data sets for each of the lines
        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        // Actual temperature line
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
                .format("Actual: {%Value}{groupsSeparator: }°C");
        series1.stroke("green");

        // Feels-like temperature line
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

        chart4.setChart(cartesian);
        linearLayout4.setVisibility(View.VISIBLE);
    }

    // Creating chart 5 - 7 day probability of precipitation + rain/snow volume
    private void createChart5(ArrayList<DailyForecastData> dailyForecastData) {
        // Similar setup as in all other chart-related methods

        APIlib.getInstance().setActiveAnyChartView(chart5);
        Cartesian cartesian = AnyChart.cartesian();

        cartesian.animation(true);

        cartesian.title("7 day probability and volume of precipitation");

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip()
                .titleFormat("Expected on {%fullTime}")
                .positionMode(TooltipPositionMode.POINT);

        cartesian.yScale().stackMode(ScaleStackMode.VALUE);

        Linear scalesLinear = Linear.instantiate();
        scalesLinear.minimum(0d);
        scalesLinear.maximum(100d);
        scalesLinear.ticks("{ interval: 20 }");

        // Main, usual X and Y axes
        cartesian.yAxis(0).title("Volume (if available)");
        cartesian.yAxis(0).labels().format("{%Value}mm");
        cartesian.xAxis(0).title("Day");

        // Right Y axis
        com.anychart.core.axes.Linear extraYAxis = cartesian.yAxis(1d);
        extraYAxis.orientation(Orientation.RIGHT)
                .scale(scalesLinear);
        extraYAxis.labels()
                .padding(0d, 0d, 0d, 5d)
                .format("{%Value}%");
        extraYAxis.title("Probability");

        List<DataEntry> data = new ArrayList<>();
        for (DailyForecastData dataItem : dailyForecastData) {
            data.add(new TemperaturePrecipitationDataEntry(dataItem.getTimeddMM(),
                    dataItem.getProbabilityOfPrecipitation() * 100,
                    dataItem.getRainVolume(),
                    dataItem.getSnowVolume(),
                    dataItem.getDateTime()));
        }

        Set set = Set.instantiate();
        set.data(data);
        Mapping lineData = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping column1Data = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping column2Data = set.mapAs("{ x: 'x', value: 'value3' }");

        Column rainColumn = cartesian.column(column1Data);
        rainColumn.name("Rain volume");
        rainColumn.hovered().markers().enabled(true);
        rainColumn.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        rainColumn.tooltip()
                .titleFormat("Expected at {%fullTime}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(0d)
                .format("Expected rain volume: {%Value}{groupsSeparator: }mm");

        Column snowColumn = cartesian.column(column2Data);
        snowColumn.name("Snow volume");
        snowColumn.hovered().markers().enabled(true);
        snowColumn.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        snowColumn.tooltip()
                .titleFormat("Expected at {%fullTime}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(0d)
                .format("Expected snow volume: {%Value}{groupsSeparator: }mm");

        Line line = cartesian.line(lineData);
        line.name("POP");
        line.yScale(scalesLinear);
        line.hovered().markers().enabled(true);
        line.hovered().markers()
                .type(MarkerType.CIRCLE)
                .stroke("red")
                .size(4d);
        line.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d)
                .format("POP: {%Value}{groupsSeparator: }%");
        line.stroke("red");

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        cartesian.xScroller(true);
        OrdinalZoom xZoom = cartesian.xZoom();
        xZoom.setToPointsCount(6, false, null);
        xZoom.getStartRatio();
        xZoom.getEndRatio();

        chart5.setChart(cartesian);
        linearLayout5.setVisibility(View.VISIBLE);
    }

    private static class TemperatureDataEntry extends ValueDataEntry {
        TemperatureDataEntry(String x, Number value, Number value2, String fullTime) {
            super(x, value);
            setValue("value2", value2);
            setValue("fullTime", fullTime);
        }
    }

    private static class PrecipitationDataEntry extends ValueDataEntry {
        PrecipitationDataEntry(String x, Number value, String fullTime) {
            super(x, value);
            setValue("fullTime", fullTime);
        }
    }

    private static class TemperaturePrecipitationDataEntry extends ValueDataEntry {
        TemperaturePrecipitationDataEntry(String x, Number value, Number value2, Number value3, String fullTime) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("fullTime", fullTime);
        }
    }

}