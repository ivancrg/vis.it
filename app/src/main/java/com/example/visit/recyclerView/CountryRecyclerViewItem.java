package com.example.visit.recyclerView;

// Class that represents the horizontal recycler view items that represent countries

public class CountryRecyclerViewItem {
    private String imageResource;
    private String country_flag;
    private String title;
    private String text;
    private int id;

    // When we are creating horizontal items, we will have to forward
    // the country data which it will contain to the constructor

    public CountryRecyclerViewItem(String imageResource, String country_flag, String title, String text, int id) {
        this.imageResource = imageResource;
        this.country_flag = country_flag;
        this.title = title;
        this.text = text;
        this.id = id;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getCountry_flag() {
        return country_flag;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}
