package com.example.visit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterMyTrips extends RecyclerView.Adapter<RecyclerViewAdapterMyTrips.RecyclerViewHolderMyTrips> {
    private ArrayList<RecyclerViewItemMyTrips> tripsList;
    private Context context;

    public RecyclerViewAdapterMyTrips(Context context, ArrayList<RecyclerViewItemMyTrips> tripsList) {
        this.context = context;
        this.tripsList = tripsList;
    }

    public static class RecyclerViewHolderMyTrips extends RecyclerView.ViewHolder {
        public TextView countryTextView;
        public TextView cityTextView;
        public TextView dateTextView;

        public RecyclerViewHolderMyTrips(@NonNull View itemView) {
            super(itemView);

            this.countryTextView = itemView.findViewById(R.id.tripCountryTextView);
            this.cityTextView = itemView.findViewById(R.id.tripCityTextView);
            this.dateTextView = itemView.findViewById(R.id.tripDateTextView);
        }
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
        holder.dateTextView.setText(currentItem.getDate());

        /* mislim da mi ovo ne treba
        zapravo treba al nez šta
        // koji argumenti?
        RecyclerView.Adapter recyclerViewAdapterMyTrips = new RecyclerViewAdapterMyTrips();

        // Responsible for placing items into our list
        RecyclerView.LayoutManager horizontalRecyclerViewLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        // Needs to be set to true if recycler view won't change in size for performance gains
        holder.horizontalRecyclerView.setHasFixedSize(true);
        holder.horizontalRecyclerView.setLayoutManager(horizontalRecyclerViewLayoutManager);
        holder.horizontalRecyclerView.setAdapter(horizontalRecyclerViewAdapter);*/
    }

    @Override
    public int getItemCount() {
        // Used for defining how many items there are in our list

        return tripsList.size();
    }
}
