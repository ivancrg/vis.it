package com.example.visit.travelling;

import com.google.gson.annotations.SerializedName;

public class RecyclerViewItemMyTrips {

    public RecyclerViewItemMyTrips() {

    }

    public RecyclerViewItemMyTrips(int id, String country, String city, String location, String necessities, String creator, String travellingMode, String dateOfDeparture, String participantsDescription) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.location = location;
        this.necessities = necessities;
        this.creator = creator;
        this.travellingMode = travellingMode;
        this.dateOfDeparture = dateOfDeparture;
        this.participantsDescription = participantsDescription;
    }

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

    @Override
    public String toString() {
        return "RecyclerViewItemMyTrips{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", necessities='" + necessities + '\'' +
                ", creator='" + creator + '\'' +
                ", travellingMode='" + travellingMode + '\'' +
                ", dateOfDeparture='" + dateOfDeparture + '\'' +
                ", participantsDescription='" + participantsDescription + '\'' +
                '}';
    }
}
