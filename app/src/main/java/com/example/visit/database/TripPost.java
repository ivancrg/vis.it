package com.example.visit.database;

import java.sql.Date;
import java.time.LocalDate;

public class TripPost {

    private int id;
    private String country;
    private String city;
    private String location;
    private String necessities;
    private String creator;
    private String travelling_mode;
    private String date_of_departure;
    private String participants_description;
    private String feedback;

    public String getFeedback() {
        return feedback;
    }

    public TripPost(String country, String city, String location, String travelling_mode, LocalDate date_of_departure, String necessities,
             String participants_description, String creator) {
        this.country = country;
        this.city = city;
        this.location = location;
        this.travelling_mode = travelling_mode;
        this.date_of_departure = Date.valueOf(date_of_departure.toString()).toString();
        this.necessities = necessities;
        this.participants_description = participants_description;
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "TripPost{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", travelling_mode='" + travelling_mode + '\'' +
                ", date_of_departure='" + date_of_departure + '\'' +
                ", necessities='" + necessities + '\'' +
                 ", participants='" + participants_description + '\'' +
                ", creator='" + creator + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
