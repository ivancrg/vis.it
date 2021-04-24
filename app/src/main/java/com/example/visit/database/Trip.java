package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Trip {

    private int id;
    private String country;
    private String city;
    private String location;
    private String necessities;
    private String creator;

    @SerializedName("travelling_mode")
    private String travellingMode;

    @SerializedName("date_of_departure")
    private LocalDate dateOfDeparture;

    @SerializedName("participants_description")
    private String participantsDescription;

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }

    public String getNecessities() {
        return necessities;
    }

    public String getParticipantsDescription() {
        return participantsDescription;
    }

    public String getCreator() {
        return creator;
    }

    public String getTravellingMode() {
        return travellingMode;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }
}
