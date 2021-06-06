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

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.HorizontalRecyclerViewHolder> {
    // Contains data of all HRV items
    private ArrayList<HorizontalRecyclerViewItem> horizontalItemsList;
    private Context context;

    public HorizontalRecyclerViewAdapter(ArrayList<HorizontalRecyclerViewItem> horizontalItemsList, Context context) {
        this.horizontalItemsList = horizontalItemsList;
        this.context = context;
    }

    // A ViewHolder describes an item view and metadata about its place within the RecyclerView
    public static class HorizontalRecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView text;

        public HorizontalRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            // References to our item's views
            this.imageView = itemView.findViewById(R.id.horizontalRecyclerViewItemImage);
            this.title = itemView.findViewById(R.id.horizontalRecyclerViewItemTitle);
            this.text = itemView.findViewById(R.id.horizontalRecyclerViewItemText);
        }
    }

    @NonNull
    @Override
    public HorizontalRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_horizontal, parent, false);
        HorizontalRecyclerViewAdapter.HorizontalRecyclerViewHolder horizontalRecyclerViewHolder = new HorizontalRecyclerViewHolder(view);

        return horizontalRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalRecyclerViewHolder holder, int position) {
        // Passes values to our item's views using references (holder.view)
        // Position tells us at which array list item we are looking at

        HorizontalRecyclerViewItem currentItem = horizontalItemsList.get(position);

        Glide.with(context).asBitmap().load(currentItem.getImageResource()).into(holder.imageView);
        holder.title.setText(currentItem.getTitle());
        holder.text.setText(currentItem.getText());
        holder.text.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        // Used for defining how many items there are in our list

        return horizontalItemsList.size();
    }
}
