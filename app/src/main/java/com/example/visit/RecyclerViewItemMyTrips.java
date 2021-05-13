package com.example.visit;

public class RecyclerViewItemMyTrips {

    private String country;
    private String city;
    private String date;

    public RecyclerViewItemMyTrips(String country, String city, String date) {
        this.country = country;
        this.city = city;
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }
}
