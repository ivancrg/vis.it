package com.example.visit.explore;

public class SummerAndWinterModel {
    private int place_id;
    private int country_id;
    private String place_name;
    private String geo_thermal_zone;
    private String place_image;
    private String intended_temp;

    public SummerAndWinterModel(int place_id, int country_id, String place_name, String geo_thermal_zone, String place_image, String intended_temp) {
        this.place_id = place_id;
        this.country_id = country_id;
        this.place_name = place_name;
        this.geo_thermal_zone = geo_thermal_zone;
        this.place_image = place_image;
        this.intended_temp = intended_temp;
    }

    public int getPlace_id() {
        return place_id;
    }

    public int getCountry_id() {
        return country_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getGeo_thermal_zone() {
        return geo_thermal_zone;
    }

    public String getPlace_image() {
        return place_image;
    }

    public String getIntended_temp() {
        return intended_temp;
    }
}
