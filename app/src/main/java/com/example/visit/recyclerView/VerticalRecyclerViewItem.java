package com.example.visit.recyclerView;

// Class that represents vertical recycler view items

import java.util.ArrayList;

public class VerticalRecyclerViewItem {
    private String title;
    private String text;
    private ArrayList<HorizontalRecyclerViewItem> horizontalRecyclerViewItems;

    public VerticalRecyclerViewItem(String title, String text, ArrayList<HorizontalRecyclerViewItem> horizontalRecyclerViewItems){
        // Each vertical recycler view item will consist of an horizontal recycler view
        // Each horizontal recycler view will consist of instances of HorizontalRecyclerViewItem

        this.title = title;
        this.text = text;
        this.horizontalRecyclerViewItems = horizontalRecyclerViewItems;
    }


    public ArrayList<HorizontalRecyclerViewItem> getHorizontalRecyclerViewItems() {
        return horizontalRecyclerViewItems;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    /*private int imageResource; // R.drawable.image is an integer
    private String title;
    private String text;
    // When we are creating vertical items, we will have to forward
    // image resource integer as well as title and text to the constructor
    public VerticalRecyclerViewItem(int imageResource, String title, String text){
        this.imageResource = imageResource;
        this.title = title;
        this.text = text;
    }
    public int getImageResource() {
        return imageResource;
    }
    public String getTitle() {
        return title;
    }
    public String getText() {
        return text;
    }*/
}