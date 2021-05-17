package com.example.visit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterMyTrips extends RecyclerView.Adapter<RecyclerViewAdapterMyTrips.RecyclerViewHolderMyTrips> {
    private ArrayList<RecyclerViewItemMyTrips> tripsList;
    private static Fragment context;

    public RecyclerViewAdapterMyTrips(Fragment context, ArrayList<RecyclerViewItemMyTrips> tripsList) {
        this.context = context;
        this.tripsList = tripsList;
    }

    @NonNull
    @Override
    public RecyclerViewHolderMyTrips onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_trips_recycler_view_row, parent, false);
        RecyclerViewHolderMyTrips recyclerViewHolderMyTrips = new RecyclerViewHolderMyTrips(view);

        return recyclerViewHolderMyTrips;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderMyTrips holder, int position) {

        RecyclerViewItemMyTrips currentItem = tripsList.get(position);

        holder.countryTextView.setText(currentItem.getCountry());
        holder.cityTextView.setText(currentItem.getCity());
        holder.dateTextView.setText(currentItem.getDateOfDeparture().toString());

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

            detailsButton.setOnClickListener(v ->  {
                    ChosenTrip.setCountry(countryTextView.getText().toString());
                    ChosenTrip.setCity(cityTextView.getText().toString());
                    ChosenTrip.setDate(dateTextView.getText().toString());

                    if (LoggedUser.getIsLoggedIn()) {
                        FragmentTransaction fragmentTransaction = context.getActivity()
                                .getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new TripDetailsFragment());
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(itemView.getContext(), "You are currently not logged in.", Toast.LENGTH_LONG).show();
                    }
            });
        }
    }
}
