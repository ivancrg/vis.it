package com.example.visit;

import com.google.gson.annotations.SerializedName;

public class RecyclerViewItemMyTrips {

    private int id;
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

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }
}
