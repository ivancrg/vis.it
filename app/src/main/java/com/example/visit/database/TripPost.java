package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.time.LocalDate;

public class TripPost {

    private String country;
    private String city;
    private String location;
    private String necessities;
    private String creator;

    @SerializedName("travelling_mode")
    private String travellingMode;

    @SerializedName("date_of_departure")
    private String dateOfDeparture;

    @SerializedName("participants_description")
    private String participantsDescription;
    private String feedback;

    public String getFeedback() {
        return feedback;
    }

    public TripPost(String country, String city, String location, String travellingMode, LocalDate dateOfDeparture, String necessities,
             String participantsDescription, String creator) {
        this.country = country;
        this.city = city;
        this.location = location;
        this.travellingMode = travellingMode;
        this.dateOfDeparture = Date.valueOf(dateOfDeparture.toString()).toString();
        this.necessities = necessities;
        this.participantsDescription = participantsDescription;
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "TripPost{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", travelling_mode='" + travellingMode + '\'' +
                ", date_of_departure='" + dateOfDeparture + '\'' +
                ", necessities='" + necessities + '\'' +
                 ", participants='" + participantsDescription + '\'' +
                ", creator='" + creator + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
