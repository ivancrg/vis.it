package com.example.visit.recyclerView;

// Class that represents vertical recycler view items

import java.util.ArrayList;

public class CountryVerticalRecyclerViewItem {
    private String title;
    private String text;
    private ArrayList<CountryRecyclerViewItem> horizontalRecyclerViewItems;

    public CountryVerticalRecyclerViewItem(String title, String text, ArrayList<CountryRecyclerViewItem> horizontalRecyclerViewItems){
        // Each vertical recycler view item will consist of an horizontal recycler view
        // Each horizontal recycler view will consist of instances of HorizontalRecyclerViewItem

        this.title = title;
        this.text = text;
        this.horizontalRecyclerViewItems = horizontalRecyclerViewItems;
    }


    public ArrayList<CountryRecyclerViewItem> getCountryHorizontalRecyclerViewItems() {
        return horizontalRecyclerViewItems;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

}
