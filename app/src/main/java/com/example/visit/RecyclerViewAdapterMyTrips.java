package com.example.visit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterMyTrips extends RecyclerView.Adapter<RecyclerViewAdapterMyTrips.RecyclerViewHolderMyTrips> {
    private ArrayList<RecyclerViewItemMyTrips> tripsList;
    private static FragmentManager fragmentManager;
    private String dateDisplay;

    public RecyclerViewAdapterMyTrips(FragmentManager fragmentManager, ArrayList<RecyclerViewItemMyTrips> tripsList) {
        RecyclerViewAdapterMyTrips.fragmentManager = fragmentManager;
        this.tripsList = tripsList;
    }

    @NonNull
    @Override
    public RecyclerViewHolderMyTrips onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_trips_recycler_view_row, parent, false);

        return new RecyclerViewHolderMyTrips(view);
    }

    // fills every row of recycler view with data
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderMyTrips holder, int position) {

        RecyclerViewItemMyTrips currentItem = tripsList.get(position);

        holder.countryTextView.setText(currentItem.getCountry());
        holder.cityTextView.setText(currentItem.getCity());

        dateDisplay = currentItem.getDateOfDeparture().split("T")[0];
        holder.dateTextView.setText(dateDisplay);

        holder.position = position;
        holder.trip = currentItem;

    }

    @Override
    public int getItemCount() {
        // Used for defining how many items there are in our list

        return tripsList.size();
    }

    public static class RecyclerViewHolderMyTrips extends RecyclerView.ViewHolder {
        public TextView countryTextView;
        public TextView cityTextView;
        public TextView dateTextView;
        public Button detailsButton;
        int position;
        RecyclerViewItemMyTrips trip;

        public RecyclerViewHolderMyTrips(@NonNull View itemView) {
            super(itemView);

            this.countryTextView = itemView.findViewById(R.id.tripCountryTextView);
            this.cityTextView = itemView.findViewById(R.id.tripCityTextView);
            this.dateTextView = itemView.findViewById(R.id.tripDateTextView);
            this.detailsButton = itemView.findViewById(R.id.detailsButton);

            detailsButton.setOnClickListener(v -> {
                ChosenTrip.setData(trip);

                if (LoggedUser.getIsLoggedIn()) {
                    MainActivity.changeFragment(fragmentManager, new TripDetailsFragment(), true);
                } else {
                    Toast.makeText(itemView.getContext(), "You are currently not logged in.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
