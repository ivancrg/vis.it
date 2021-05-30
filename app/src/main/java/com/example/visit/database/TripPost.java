package com.example.visit.database;

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
    private String travelling_mode;
    private String date_of_departure;
    private String participants_description;
    private String feedback;

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

    public String getTravelling_mode() {
        return travelling_mode;
    }

    public String getDate_of_departure() {
        return date_of_departure;
    }

    public String getParticipants_description() {
        return participants_description;
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

    public void setTravelling_mode(String travelling_mode) {
        this.travelling_mode = travelling_mode;
    }

    public void setDate_of_departure(String date_of_departure) {
        this.date_of_departure = date_of_departure;
    }

    public void setParticipants_description(String participants_description) {
        this.participants_description = participants_description;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDateInSQLFormat(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");

        java.util.Date parsed = null;

        try {
            parsed = format.parse(date_of_departure);
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
        System.out.println(travelling_mode + " " + tripPost.getTravelling_mode() + " " + travelling_mode.equals(tripPost.getTravelling_mode()));
        System.out.println(date_of_departure + " " + tripPost.getDate_of_departure() + " " + date_of_departure.equals(tripPost.getDate_of_departure()));
        System.out.println(date_of_departure + " " + tripPost.getDateInSQLFormat() + " " + date_of_departure.equals(tripPost.getDateInSQLFormat()));
        System.out.println(necessities + " " + tripPost.getNecessities() + " " + necessities.equals(tripPost.getNecessities()));
        System.out.println(participants_description + " " + tripPost.getParticipants_description() + " " + participants_description.equals(tripPost.getParticipants_description()));
        System.out.println(creator + " " + tripPost.getCreator() + " " + creator.equals(tripPost.getCreator()));

        if (country.equals(tripPost.getCountry()) &&
                city.equals(tripPost.getCity()) &&
                location.equals(tripPost.getLocation()) &&
                travelling_mode.equals(tripPost.getTravelling_mode()) &&
                (date_of_departure.equals(tripPost.getDate_of_departure()) ||  date_of_departure.equals(tripPost.getDateInSQLFormat())) &&
                necessities.equals(tripPost.getNecessities()) &&
                participants_description.equals(tripPost.getParticipants_description()) &&
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
                ", travelling_mode='" + travelling_mode + '\'' +
                ", date_of_departure='" + date_of_departure + '\'' +
                ", participants_description='" + participants_description + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
