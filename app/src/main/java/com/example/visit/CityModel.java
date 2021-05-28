package com.example.visit;

public class CityModel {
    private int country_id;
    private int city_id;
    private String city_name;
    private String city_image;

    public CityModel(int country_id, int city_id, String city_name, String city_image) {
        this.country_id = country_id;
        this.city_id = city_id;
        this.city_name = city_name;
        this.city_image = city_image;
    }

    public int getCountry_id() {
        return country_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getCity_image() {
        return city_image;
    }
}
