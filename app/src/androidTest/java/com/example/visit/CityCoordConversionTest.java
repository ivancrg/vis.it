package com.example.visit;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CityCoordConversionTest {
    private final static Context context = ApplicationProvider.getApplicationContext();

    // Maximum tolerable distance between calculated and expected positions in meters
    private double maxTolerablePositionsDistance = 25000;

    private String destinationCityCountry;
    private LatLng expectedCoord;

    public CityCoordConversionTest(String destinationCityCountry, LatLng expectedCoord) {
        this.destinationCityCountry = destinationCityCountry;
        this.expectedCoord = expectedCoord;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testDataRead() throws IOException {
        Collection<Object[]> returnValue = new LinkedList<Object[]>();

        // Reading CSV file from assets folder
        InputStreamReader inputStreamReader = new InputStreamReader(context.getAssets().open("city_coordinates_conversion_test.csv"));
        BufferedReader reader = new BufferedReader(inputStreamReader);

        // Skip frist line
        reader.readLine();
        String line;

        while ((line = reader.readLine()) != null) {
            String destinationCityCountry = line.split(",")[0] + ", " + line.split(",")[1];
            double expectedLatitude = Double.parseDouble(line.split(",")[2]);
            double expectedLongitude = Double.parseDouble(line.split(",")[3]);
            Log.i("OUTPUT", destinationCityCountry + " " + expectedLatitude + " " + expectedLongitude);

            returnValue.add(new Object[]{destinationCityCountry, new LatLng(expectedLatitude, expectedLongitude)});
        }

        return returnValue;
    }

    @Test
    public void cityCoordConversionTest() {
        // COORDINATE CONVERSION TEST BEGINS
        // Get destination city lat and lng from city name
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> listOfAddress;

        try {
            listOfAddress = geocoder.getFromLocationName(destinationCityCountry, 1);
            if (listOfAddress != null && !listOfAddress.isEmpty()) {
                Address address = listOfAddress.get(0);

                double destinationCityLat = address.getLatitude();
                double destinationCityLng = address.getLongitude();
                LatLng destinationCoordinates = new LatLng(destinationCityLat, destinationCityLng);

                double positionsDistance = SphericalUtil.computeDistanceBetween(expectedCoord, destinationCoordinates);

                Log.i("OUTPUT", destinationCoordinates.toString());
                Log.i("OUTPUT",
                        "Maximum tolerable distance between calculated and expected positions (m): "
                                + maxTolerablePositionsDistance
                                + " Actual distance: "
                                + positionsDistance
                                + "\n");

                assertTrue(positionsDistance < maxTolerablePositionsDistance);
            }
        } catch (IOException e) {
            // If this error happens emulator needs to be restarted
            Log.e("ERROR", "GRPC failed: Emulator error, please restart and cold boot device or change your emulator!");
            e.printStackTrace();
        }
    }
}