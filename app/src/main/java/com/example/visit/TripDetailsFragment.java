package com.example.visit;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.visit.database.CurrentTripPatch;
import com.example.visit.database.Database;
import com.example.visit.database.HerokuAPI;
import com.example.visit.database.UpdatePasswordPatch;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TripDetailsFragment extends Fragment {

    public String dateDisplay;

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    double lat = 0;
    double lng = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_details, container, false);

        TextView country = (TextView) view.findViewById(R.id.tripDetailsCountry);
        TextView city = (TextView) view.findViewById(R.id.tripDetailsCity);
        TextView date = (TextView) view.findViewById(R.id.tripDetailsDate);
        TextView location = (TextView) view.findViewById(R.id.tripDetailsLocation);
        TextView necessities = (TextView) view.findViewById(R.id.tripDetailsNecessities);
        TextView travellingMode = (TextView) view.findViewById(R.id.tripDetailsTravellingMode);
        TextView participants = (TextView) view.findViewById(R.id.tripDetailsParticipants);
        Button start = (Button) view.findViewById(R.id.startTripButton);
        Button returnButton = (Button) view.findViewById(R.id.returnToTripsButton);

        dateDisplay = ChosenTrip.getDate().split("T")[0];

        country.setText(ChosenTrip.getCountry());
        city.setText(ChosenTrip.getCity());
        date.setText(dateDisplay);
        location.setText(ChosenTrip.getLocation());
        necessities.setText((ChosenTrip.getNecessities() == null) ? ("No necessities selected.") : (ChosenTrip.getNecessities()));
        travellingMode.setText(ChosenTrip.getTravellingMode());
        participants.setText((ChosenTrip.getParticipantsDescription() == null) ? ("No participants selected") : (ChosenTrip.getParticipantsDescription()));

        String destinationCity = city.getText().toString();
        String destinationCountry = country.getText().toString();

        start.setOnClickListener(v -> {
            // Save current trip into database
            Retrofit retrofit = Database.getRetrofit();
            HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);

            CurrentTripPatch currentTripPatch = new CurrentTripPatch(LoggedUser.getUsername(), Integer.toString(ChosenTrip.getId()));
            Call<CurrentTripPatch> call = herokuAPI.addCurrentTrip(LoggedUser.getUsername(), currentTripPatch);

            call.enqueue(new Callback<CurrentTripPatch>() {
                @Override
                public void onResponse(@NotNull Call<CurrentTripPatch> call, @NotNull Response<CurrentTripPatch> response) {
                    if (!response.isSuccessful()) {
                        // Not OK
                        Log.e("/addCurrentTrip", "notSuccessful: Something went wrong. " + response.code());
                        return;
                    }

                    CurrentTripPatch postResponse = response.body();

                    assert postResponse != null;

                    if ("current_trip_updated".equals(postResponse.getFeedback())) {// Trip saved
                        Toast.makeText(view.getContext(), "Successfully starting trip.", Toast.LENGTH_LONG).show();
                    } else if ("user_not_found".equals(postResponse.getFeedback())) {// Possible database error server-side, user not found...
                        Toast.makeText(view.getContext(), "Sorry, there is an error in the username.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(view.getContext(), "Sorry, there is a database error.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CurrentTripPatch> call, @NotNull Throwable t) {
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                    Log.e("/addCurrentTrip", "onFailure: Something went wrong. " + t.getMessage());
                }
            });

            MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new TravellingFragment(), false);
        });

        returnButton.setOnClickListener(v -> {
            if (LoggedUser.getIsLoggedIn()) {
                MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new MyTripsFragment(), false);
            } else {
                Toast.makeText(view.getContext(), "You are currently not logged in.", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}