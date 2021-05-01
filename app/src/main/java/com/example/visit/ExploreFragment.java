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
    private RecyclerView countryAndCityView;
    private RecyclerView summerAndWinterView;

    // Adapter is a sort of a bridge between our data (verticalItems) and the RV
    // Adapter always provides as many items as we need at the time which means optimal performance
    private RecyclerView.Adapter countryAndCityAdapter;
    private RecyclerView.Adapter summerAndWinterAdapter;


    // Responsible for placing items into our list
    private RecyclerView.LayoutManager countryAndCityLayoutManager;
    private RecyclerView.LayoutManager summerAndWinterLayoutManager;

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
        ArrayList<VerticalRecyclerViewItem> countryAndCityCategory = new ArrayList<>();
        countryAndCityCategory.add(new VerticalRecyclerViewItem("Popular countries", "Text", horizontalItems));
        countryAndCityCategory.add(new VerticalRecyclerViewItem("Explore vibrant new places", "Text", horizontalItems));

        ArrayList<VerticalRecyclerViewItem> summerAndWinterCategory = new ArrayList<>();
        summerAndWinterCategory.add(new VerticalRecyclerViewItem("Smell the sea and feel the sky", "Best summer vacation spots", horizontalItems));
        summerAndWinterCategory.add(new VerticalRecyclerViewItem("The joys of winter", "Text", horizontalItems));

        countryAndCityView = view.findViewById(R.id.explore_countries_and_cities_recycler);
        summerAndWinterView = view.findViewById(R.id.explore_summer_and_winter_recycler);

        // Needs to be set to true if recycler view won't change in size for performance gains
        countryAndCityView.setHasFixedSize(true);
        summerAndWinterView.setHasFixedSize(true);

        countryAndCityLayoutManager = new LinearLayoutManager(getContext());
        countryAndCityAdapter = new VerticalRecyclerViewAdapter(getContext(), countryAndCityCategory);

        countryAndCityView.setLayoutManager(countryAndCityLayoutManager);
        countryAndCityView.setAdapter(countryAndCityAdapter);

        summerAndWinterLayoutManager = new LinearLayoutManager(getContext());
        summerAndWinterAdapter = new VerticalRecyclerViewAdapter(getContext(), summerAndWinterCategory);

        summerAndWinterView.setLayoutManager(summerAndWinterLayoutManager);
        summerAndWinterView.setAdapter(summerAndWinterAdapter);

        return view;
    }
}