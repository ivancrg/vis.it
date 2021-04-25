package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.recyclerView.HorizontalRecyclerViewItem;
import com.example.visit.recyclerView.VerticalRecyclerViewAdapter;
import com.example.visit.recyclerView.VerticalRecyclerViewItem;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {
    private RecyclerView verticalRecyclerView;

    // Adapter is a sort of a bridge between our data (verticalItems) and the RV
    // Adapter always provides as many items as we need at the time which means optimal performance
    private RecyclerView.Adapter verticalRecyclerViewAdapter;

    // Responsible for placing items into our list
    private RecyclerView.LayoutManager verticalRecyclerViewLayoutManager;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);


        // TODO implementation of real data
        // Horizontal items should be placed to ArrayList<ArrayList<HorizontalRecyclerViewItem>>
        // Vertical items should be placed to ArrayList<VerticalRecyclerViewItem>
        // All elements of ArrayList<ArrayList<HorizontalRecyclerViewItem>> should be members of ArrayList<VerticalRecyclerViewItem>

        // horizontalItems is used to represent horizontal recycler view items
        ArrayList<HorizontalRecyclerViewItem> horizontalItems = new ArrayList<>();
        horizontalItems.add(new HorizontalRecyclerViewItem(R.drawable.example1, "Title 1", "Text 1"));
        horizontalItems.add(new HorizontalRecyclerViewItem(R.drawable.example2, "Title 2", "Text 2"));
        horizontalItems.add(new HorizontalRecyclerViewItem(R.drawable.example3, "Title 3", "Text 3"));
        horizontalItems.add(new HorizontalRecyclerViewItem(R.drawable.example4, "Title 4", "Text 4"));
        horizontalItems.add(new HorizontalRecyclerViewItem(R.drawable.example5, "Title 5", "Text 5"));

        // vertical is used to represent horizontal recycler view items
        ArrayList<VerticalRecyclerViewItem> verticalItems = new ArrayList<>();
        verticalItems.add(new VerticalRecyclerViewItem("Category Title 1", "Category text 1", horizontalItems));
        verticalItems.add(new VerticalRecyclerViewItem("Category Title 2", "Category text 2", horizontalItems));
        verticalItems.add(new VerticalRecyclerViewItem("Category Title 3", "Category text 3", horizontalItems));
        verticalItems.add(new VerticalRecyclerViewItem("Category Title 4", "Category text 4", horizontalItems));
        verticalItems.add(new VerticalRecyclerViewItem("Category Title 5", "Category text 5", horizontalItems));


        verticalRecyclerView = view.findViewById(R.id.exploreScreenVerticalRecyclerView);

        // Needs to be set to true if recycler view won't change in size for performance gains
        verticalRecyclerView.setHasFixedSize(true);

        verticalRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        verticalRecyclerViewAdapter = new VerticalRecyclerViewAdapter(getContext(), verticalItems);

        verticalRecyclerView.setLayoutManager(verticalRecyclerViewLayoutManager);
        verticalRecyclerView.setAdapter(verticalRecyclerViewAdapter);

        return view;
    }
}