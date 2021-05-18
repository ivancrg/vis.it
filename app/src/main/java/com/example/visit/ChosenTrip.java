package com.example.visit;

public class ChosenTrip {

    private static String country;
    private static String city;
    private static String date;
    private static String location;
    private static String necessities;
    private static String travellingMode;
    private static String participantsDescription;

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

    public static String getLocation() { return location; }

    public static void setLocation(String location) { ChosenTrip.location = location; }

    public static String getNecessities() { return necessities; }

    public static void setNecessities(String necessities) { ChosenTrip.necessities = necessities; }

    public static String getTravellingMode() { return travellingMode; }

    public static void setTravellingMode(String travellingMode) { ChosenTrip.travellingMode = travellingMode; }

    public static String getParticipantsDescription() { return participantsDescription; }

    public static void setParticipantsDescription(String participantsDescription) { ChosenTrip.participantsDescription = participantsDescription; }

    public static void setData(RecyclerViewItemMyTrips trip) {
        country = trip.getCountry();
        city = trip.getCity();
        date = trip.getDateOfDeparture();
        location = trip.getLocation();
        necessities = trip.getNecessities();
        travellingMode = trip.getTravellingMode();
        participantsDescription = trip.getParticipantsDescription();
    }
}

