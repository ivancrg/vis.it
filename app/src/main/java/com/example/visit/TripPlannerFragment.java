package com.example.visit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.visit.database.Database;
import com.example.visit.database.HerokuAPI;
import com.example.visit.database.TripPost;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TripPlannerFragment extends Fragment {

    public TripPlannerFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trip_planner, container, false);

        TextView selectedCountryTextView = (TextView) view.findViewById(R.id.selectedCountryTextView);
        TextView selectedCityTextView = (TextView) view.findViewById(R.id.selectedCityTextView);
        TextView selectedLocationTextView = (TextView) view.findViewById(R.id.selectedLocationTextView);
        TextView selectedTravellingModeTextView = (TextView) view.findViewById(R.id.selectedTravellingModeTextView);
        TextView selectedDateTextView = (TextView) view.findViewById(R.id.selectedDateTextView);
        TextView selectedNecessitiesTextView = (TextView) view.findViewById(R.id.selectedNecessitiesTextView);
        TextView selectedParticipantsTextView = (TextView) view.findViewById(R.id.selectedParticipantsTextView);

        Button countryButton = (Button) view.findViewById(R.id.countryButton);
        Button cityButton = (Button) view.findViewById(R.id.cityButton);
        Button locationButton = (Button) view.findViewById(R.id.locationButton);
        Button travellingModeButton = (Button) view.findViewById(R.id.travellingModeButton);
        Button dateButton = (Button) view.findViewById(R.id.dateButton);
        Button necessitiesButton = (Button) view.findViewById(R.id.necessitiesButton);
        Button participantsButton = (Button) view.findViewById(R.id.participantsButton);
        Button saveTripButton = (Button) view.findViewById(R.id.saveTripButton);

        saveTripButton.setEnabled(false);

        TextWatcher saveTripEnableWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                saveTripButton.setEnabled(selectedCountryTextView.getText().length() > 0 &&
                        selectedCityTextView.getText().length() > 0 &&
                        selectedLocationTextView.getText().length() > 0 &&
                        selectedTravellingModeTextView.getText().length() > 0 &&
                        selectedDateTextView.getText().length() > 0 &&
                        selectedNecessitiesTextView.getText().length() > 0 &&
                        selectedParticipantsTextView.getText().length() > 0);
            }
        };

        selectedCountryTextView.addTextChangedListener(saveTripEnableWatcher);
        selectedCityTextView.addTextChangedListener(saveTripEnableWatcher);
        selectedLocationTextView.addTextChangedListener(saveTripEnableWatcher);
        selectedTravellingModeTextView.addTextChangedListener(saveTripEnableWatcher);
        selectedDateTextView.addTextChangedListener(saveTripEnableWatcher);
        selectedNecessitiesTextView.addTextChangedListener(saveTripEnableWatcher);
        selectedParticipantsTextView.addTextChangedListener(saveTripEnableWatcher);

        selectedCountryTextView.setText((TripPlanning.getCountry() == null) ? ("No country selected.") : (TripPlanning.getCountry()));
        selectedCityTextView.setText((TripPlanning.getCity() == null) ? ("No city selected.") : (TripPlanning.getCity()));
        selectedLocationTextView.setText((TripPlanning.getLocation() == null) ? ("No location selected.") : (TripPlanning.getLocation()));
        selectedTravellingModeTextView.setText((TripPlanning.getTravellingMode() == null) ? ("No travelling mode selected.") : (TripPlanning.getTravellingMode()));
        selectedDateTextView.setText((TripPlanning.getDateOfDeparture() == null) ? ("No date selected.") : (TripPlanning.getDateOfDeparture().toString()));
        selectedNecessitiesTextView.setText((TripPlanning.getNecessities() == null) ? ("No necessities selected.") : (TripPlanning.getNecessities()));
        selectedParticipantsTextView.setText((TripPlanning.getParticipantsDescription() == null) ? ("No participants selected.") : (TripPlanning.getParticipantsDescription()));

        countryButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new CountryFragment());
            fragmentTransaction.commit();
        });

        cityButton.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("key", TripPlanning.getCountry());
            CityFragment fragmentCity = new CityFragment();
            fragmentCity.setArguments(args);
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragmentCity);
            fragmentTransaction.commit();
        });

        locationButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AccommodationFragment());
            fragmentTransaction.commit();
        });

        travellingModeButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new TravellingModeFragment());
            fragmentTransaction.commit();
        });

        dateButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new DateFragment());
            fragmentTransaction.commit();
        });

        necessitiesButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new TravellingNecessitiesFragment());
            fragmentTransaction.commit();
        });

        participantsButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new ParticipantsFragment());
            fragmentTransaction.commit();
        });

        saveTripButton.setOnClickListener(view1 -> {
            if (informationValid(selectedCountryTextView.getText().toString(),
                    selectedCityTextView.getText().toString(),
                    selectedLocationTextView.getText().toString(),
                    selectedTravellingModeTextView.getText().toString(),
                    selectedDateTextView.getText().toString(),
                    selectedNecessitiesTextView.getText().toString(),
                    selectedParticipantsTextView.getText().toString())
                    && (TripPlanning.getCountry() != null)
                    && (TripPlanning.getCity() != null)
                    && (TripPlanning.getLocation() != null)
                    && (TripPlanning.getTravellingMode() != null)
                    && (TripPlanning.getDateOfDeparture() != null)
                    && (TripPlanning.getNecessities() != null)
                    && (TripPlanning.getParticipantsDescription() != null)) {
                postTrip(view1,
                        TripPlanning.getCountry(),
                        TripPlanning.getCity(),
                        TripPlanning.getLocation(),
                        TripPlanning.getTravellingMode(),
                        TripPlanning.getDateOfDeparture(),
                        TripPlanning.getNecessities(),
                        TripPlanning.getParticipantsDescription());
            }});

        return view;
    }

    private boolean informationValid(String country, String city, String location, String travellingMode, String date, String necessities, String participants) {
        // TODO CHECK INFO
        // Setting error example
        //firstName.setError("You need to enter a name");

        return true;
    }

    private void postTrip(View view, String country, String city, String location, String travellingMode, LocalDate date, String necessities, String participants) {

        TripPost tripPost = new TripPost(country, city, location, travellingMode, date, necessities, participants, LoggedUser.getUsername());

        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);

        Call<TripPost> call = herokuAPI.postTrip(tripPost);

        call.enqueue(new Callback<TripPost>() {
            @Override
            public void onResponse(@NotNull Call<TripPost> call, @NotNull Response<TripPost> response) {
                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/insertTrip", "notSuccessful: Something went wrong. " + response.code());
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                    return;
                }

                TripPost postResponse = response.body();
                //Toast.makeText(view.getContext(), response.body().toString(), Toast.LENGTH_LONG).show();

                assert postResponse != null;
                switch (postResponse.getFeedback()) {
                    case "database_error":
                        // Database error server-side
                        Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                        break;
                    case "trip_inserted":
                        // Trip is inserted
                        Toast.makeText(view.getContext(), "Congratulations, you successfully saved a trip!", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(@NotNull Call<TripPost> call, @NotNull Throwable t) {
                Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                Log.e("/insertTrip", "onFailure: Something went wrong. " + t.getMessage());
            }
        });
    }
}