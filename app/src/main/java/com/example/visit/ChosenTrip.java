package com.example.visit;

public class ChosenTrip {

    private static String country;
    private static String city;
    private static String date;

    public static String getCountry() {
        return country;
    }

    public static void setCountry(String country) {
        ChosenTrip.country = country;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        ChosenTrip.city = city;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        ChosenTrip.date = date;
    }
}
