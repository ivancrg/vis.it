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
import com.example.visit.R;

import java.util.ArrayList;



public class CountryRecyclerViewAdapter extends RecyclerView.Adapter<CountryRecyclerViewAdapter.HorizontalRecyclerViewHolder> {
    // Contains data of all HRV items
    private ArrayList<CountryRecyclerViewItem> horizontalItemsList;
    private Context context;
    private RecyclerViewClickInterface mRecyclerViewClickInterface;


    public CountryRecyclerViewAdapter(ArrayList<CountryRecyclerViewItem> horizontalItemsList, Context context, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.horizontalItemsList = horizontalItemsList;
        this.context = context;
        this.mRecyclerViewClickInterface = recyclerViewClickInterface;
    }

    // A ViewHolder describes an item view and metadata about its place within the RecyclerView
    public static class HorizontalRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public ImageView flag;
        public TextView title;
        RecyclerViewClickInterface recyclerViewClickInterface;

        public HorizontalRecyclerViewHolder(@NonNull View itemView, RecyclerViewClickInterface recyclerViewClickInterface) {
            super(itemView);
            // References to our item's views
            this.imageView = itemView.findViewById(R.id.exploreCountryImage);
            this.title = itemView.findViewById(R.id.exploreCountryName);
            this.flag = itemView.findViewById(R.id.exploreCountryFlag);
            this.recyclerViewClickInterface = recyclerViewClickInterface;

            itemView.setOnClickListener(this);
        }
        // OnClick method that sends the position of our click
        @Override
        public void onClick(View v) {
            recyclerViewClickInterface.onItemClick(getAbsoluteAdapterPosition());
        }
    }

    @NonNull
    @Override
    public HorizontalRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_explore_country_recycler, parent, false);
        return new HorizontalRecyclerViewHolder(view, mRecyclerViewClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalRecyclerViewHolder holder, int position) {
        // Passes values to our item's views using references (holder.view)
        // Position tells us at which array list item we are looking at
        CountryRecyclerViewItem currentItem = horizontalItemsList.get(position);

        //Loading country flag and image into their itemViews
        holder.title.setText(currentItem.getTitle());
        Glide.with(context).asBitmap().load(currentItem.getImageResource()).into(holder.imageView);
        Glide.with(context).asBitmap().load(currentItem.getCountry_flag()).into(holder.flag);
    }

    @Override
    public int getItemCount() {
        // Used for defining how many items there are in our list
        return horizontalItemsList.size();
    }
}
