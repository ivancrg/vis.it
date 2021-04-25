package com.example.visit.recyclerView;

// Class that represents horizontal recycler view items

public class HorizontalRecyclerViewItem {
    private int imageResource; // R.drawable.image is an integer
    private String title;
    private String text;

    // When we are creating horizontal items, we will have to forward
    // image resource integer as well as title and text to the constructor
    public HorizontalRecyclerViewItem(int imageResource, String title, String text){
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
    }
}
