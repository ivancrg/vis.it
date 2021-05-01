package com.example.visit.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.R;

import java.util.ArrayList;

// Adapter is a sort of a bridge between our data (verticalItems) and the RV
// Adapter always provides as many items as we need at the time which means optimal performance

public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.VerticalRecyclerViewHolder> {
    // Contains data of all VRV items
    private ArrayList<VerticalRecyclerViewItem> verticalItemsList;
    private Context context;

    public VerticalRecyclerViewAdapter(Context context, ArrayList<VerticalRecyclerViewItem> verticalItemsList) {
        this.verticalItemsList = verticalItemsList;
        this.context = context;
    }

    // A ViewHolder describes an item view and metadata about its place within the RecyclerView
    public static class VerticalRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView text;
        public RecyclerView horizontalRecyclerView;

        public VerticalRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            // References to our item's views
            this.title = itemView.findViewById(R.id.verticalRecyclerViewItemTitle);
            this.text = itemView.findViewById(R.id.verticalRecyclerViewItemText);
            this.horizontalRecyclerView = itemView.findViewById(R.id.horizontalRecyclerView);
        }
    }

    @NonNull
    @Override
    public VerticalRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_vertical, parent, false);
        VerticalRecyclerViewHolder verticalRecyclerViewHolder = new VerticalRecyclerViewHolder(view);

        return verticalRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalRecyclerViewHolder holder, int position) {
        // Passes values to our item's views using references (holder.view)
        // Position tells us at which array list item we are looking at

        VerticalRecyclerViewItem currentItem = verticalItemsList.get(position);

        holder.title.setText(currentItem.getTitle());
        holder.text.setText(currentItem.getText());

        // horizontalItems is used to represent horizontal recycler view items
        ArrayList<HorizontalRecyclerViewItem> horizontalItems = currentItem.getHorizontalRecyclerViewItems();

        // Adapter is a sort of a bridge between our data (verticalItems) and the RV
        // Adapter always provides as many items as we need at the time which means optimal performance
        RecyclerView.Adapter horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter(horizontalItems);

        // Responsible for placing items into our list
        RecyclerView.LayoutManager horizontalRecyclerViewLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        // Needs to be set to true if recycler view won't change in size for performance gains
        holder.horizontalRecyclerView.setHasFixedSize(true);
        holder.horizontalRecyclerView.setLayoutManager(horizontalRecyclerViewLayoutManager);
        holder.horizontalRecyclerView.setAdapter(horizontalRecyclerViewAdapter);
    }

    @Override
    public int getItemCount() {
        // Used for defining how many items there are in our list

        return verticalItemsList.size();
    }
}
