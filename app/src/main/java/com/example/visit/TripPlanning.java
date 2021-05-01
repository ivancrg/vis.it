package com.example.visit;

import com.example.visit.database.Trip;

import java.time.LocalDate;

public class TripPlanning {

    private static int id;
    private static String country;
    private static String city;
    private static String location;
    private static String necessities;
    private static String creator;
    private static String travellingMode;
    private static LocalDate dateOfDeparture;
    private static String participantsDescription;

    public static int getId() { return id; }

    public static String getCountry() { return country; }

    public static String getCity() { return city; }

    public static String getLocation() { return location; }

    public static String getNecessities() { return necessities; }

    public static String getCreator() { return creator; }

    public static String getTravellingMode() { return travellingMode; }

    public static LocalDate getDateOfDeparture() { return dateOfDeparture; }

    public static String getParticipantsDescription() { return participantsDescription; }

    public static void setCountry(String country) { TripPlanning.country = country; }

    public static void setCity(String city) { TripPlanning.city = city; }

    public static void setLocation(String location) { TripPlanning.location = location; }

    public static void setNecessities(String necessities) { TripPlanning.necessities = necessities; }

    public static void setCreator(String creator) { TripPlanning.creator = creator; }

    public static void setTravellingMode(String travellingMode) { TripPlanning.travellingMode = travellingMode; }

    public static void setDateOfDeparture(LocalDate dateOfDeparture) { TripPlanning.dateOfDeparture = dateOfDeparture; }

    public static void setParticipantsDescription(String participantsDescription) { TripPlanning.participantsDescription = participantsDescription; }

    public static void setData(Trip trip) {
        id = trip.getId();
        country = trip.getCountry();
        city = trip.getCity();
        location = trip.getLocation();
        necessities = trip.getNecessities();
        travellingMode = trip.getTravellingMode();
        dateOfDeparture = trip.getDateOfDeparture();
        participantsDescription = trip.getParticipantsDescription();
        creator = trip.getCreator();
    }
}
