package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class TripPost {
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

    private String feedback;

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

    public String getCreator() {
        return creator;
    }

    public String getTravellingMode() {
        return travellingMode;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public String getParticipantsDescription() {
        return participantsDescription;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNecessities(String necessities) {
        this.necessities = necessities;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setTravellingMode(String travellingMode) {
        this.travellingMode = travellingMode;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public void setParticipantsDescription(String participantsDescription) {
        this.participantsDescription = participantsDescription;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDateInSQLFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");

        java.util.Date parsed = null;

        try {
            parsed = format.parse(dateOfDeparture);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return "";
        }

        assert parsed != null;
        Timestamp sqlTimestamp = new Timestamp((parsed.getTime()));

        return formatter.format(sqlTimestamp);
    }

    public boolean contentEquals(TripPost tripPost) throws ParseException {


        System.out.println(country + " " + tripPost.getCountry() + " " + country.equals(tripPost.getCountry()));
        System.out.println(city + " " + tripPost.getCity() + " " + city.equals(tripPost.getCity()));
        System.out.println(location + " " + tripPost.getLocation() + " " + location.equals(tripPost.getLocation()));
        System.out.println(travellingMode + " " + tripPost.getTravellingMode() + " " + travellingMode.equals(tripPost.getTravellingMode()));
        System.out.println(dateOfDeparture + " " + tripPost.getDateOfDeparture() + " " + dateOfDeparture.equals(tripPost.getDateOfDeparture()));
        System.out.println(dateOfDeparture + " " + tripPost.getDateInSQLFormat() + " " + dateOfDeparture.equals(tripPost.getDateInSQLFormat()));
        System.out.println(necessities + " " + tripPost.getNecessities() + " " + necessities.equals(tripPost.getNecessities()));
        System.out.println(participantsDescription + " " + tripPost.getParticipantsDescription() + " " + participantsDescription.equals(tripPost.getParticipantsDescription()));
        System.out.println(creator + " " + tripPost.getCreator() + " " + creator.equals(tripPost.getCreator()));

        if (country.equals(tripPost.getCountry()) &&
                city.equals(tripPost.getCity()) &&
                location.equals(tripPost.getLocation()) &&
                travellingMode.equals(tripPost.getTravellingMode()) &&
                (dateOfDeparture.equals(tripPost.getDateOfDeparture()) || dateOfDeparture.equals(tripPost.getDateInSQLFormat())) &&
                necessities.equals(tripPost.getNecessities()) &&
                participantsDescription.equals(tripPost.getParticipantsDescription()) &&
                creator.equals(tripPost.getCreator()))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return "TripPost{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", necessities='" + necessities + '\'' +
                ", creator='" + creator + '\'' +
                ", travellingMode='" + travellingMode + '\'' +
                ", dateOfDeparture='" + dateOfDeparture + '\'' +
                ", participantsDescription='" + participantsDescription + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
