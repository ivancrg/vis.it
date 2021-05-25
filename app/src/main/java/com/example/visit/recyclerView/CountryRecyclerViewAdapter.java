package com.example.visit.recyclerView;


// Adapter is a sort of a bridge between our data (horizontalItems) and the RV
// Adapter always provides as many items as we need at the time which means optimal performance

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.visit.MainActivity;
import com.example.visit.R;

import java.util.ArrayList;



public class CountryRecyclerViewAdapter extends RecyclerView.Adapter<CountryRecyclerViewAdapter.HorizontalRecyclerViewHolder> {
    // Contains data of all HRV items
    private ArrayList<CountryRecyclerViewItem> horizontalItemsList;
    private Context context;

    public CountryRecyclerViewAdapter(ArrayList<CountryRecyclerViewItem> horizontalItemsList, Context context) {
        this.horizontalItemsList = horizontalItemsList;
        this.context = context;
    }

    // A ViewHolder describes an item view and metadata about its place within the RecyclerView
    public static class HorizontalRecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView flag;
        public TextView title;

        public HorizontalRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            // References to our item's views
            this.imageView = itemView.findViewById(R.id.exploreCountryImage);
            this.title = itemView.findViewById(R.id.exploreCountryName);
            this.flag = itemView.findViewById(R.id.exploreCountryFlag);
        }
    }

    @NonNull
    @Override
    public HorizontalRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_explore_country_recycler, parent, false);
        return new HorizontalRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalRecyclerViewHolder holder, int position) {
        // Passes values to our item's views using references (holder.view)
        // Position tells us at which array list item we are looking at
        CountryRecyclerViewItem currentItem = horizontalItemsList.get(position);

        //TODO: Make image resource an url and load it with glide
        holder.title.setText(currentItem.getTitle());
        Glide.with(context).asBitmap().load(currentItem.getImageResource()).into(holder.imageView);
        Glide.with(context).asBitmap().load(currentItem.getCountry_flag()).into(holder.flag);


        //TODO: write OnClick Listener
    }

    @Override
    public int getItemCount() {
        // Used for defining how many items there are in our list
        return horizontalItemsList.size();
    }
}
