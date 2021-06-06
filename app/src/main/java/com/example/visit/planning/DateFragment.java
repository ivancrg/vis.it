package com.example.visit.planning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.visit.MainActivity;
import com.example.visit.R;

import java.time.LocalDate;


public class DateFragment extends Fragment {

    public DateFragment() {
        // Required empty public constructor
    }

    public static DateFragment newInstance() {
        return new DateFragment();
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_date, container, false);

        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        Button next = (Button) view.findViewById(R.id.next_date);
        Button cancel = (Button) view.findViewById(R.id.cancel_date);
        TextView continueExploring = (TextView) view.findViewById(R.id.continue_text);

        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        next.setOnClickListener(view -> {
            int day = datePicker.getDayOfMonth();
            // datePicker indexes months from 0
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();

            LocalDate date = LocalDate.of(year, month, day);
            TripPlanning.setDateOfDeparture(date);

            MainActivity.changeFragment(requireActivity().getSupportFragmentManager(), new TravellingNecessitiesFragment(), true);
        });

        cancel.setOnClickListener(view -> {
            MainActivity.changeFragment(requireActivity().getSupportFragmentManager(), new TripPlannerFragment(), true);
        });

        continueExploring.setOnClickListener(view -> {
            // TODO
            //needs to be forwarded to Explore fragment
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}