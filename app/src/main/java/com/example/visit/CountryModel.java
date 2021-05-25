package com.example.visit;

public class CountryModel {
    private int country_id;
    private String country_name;
    private String country_code;
    private String country_flag;
    private String country_currency;
    private int country_pop;
    private String country_image;
    private double bigmac_index;
    private String country_desc;
    private String country_hemisphere;
    private String language_top;
    private String capital_city;
    private String country_timezone;
    private String call_code;


    public CountryModel(int country_id, String country_name, String country_code, String country_flag, String country_currency,
                        int country_pop, String country_image,
                        double bigmac_index, String country_desc, String country_hemisphere, String language_top,
                        String capital_city, String country_timezone, String call_code) {
        this.country_id = country_id;
        this.country_name = country_name;
        this.country_code = country_code;
        this.country_flag = country_flag;
        this.country_currency = country_currency;
        this.country_pop = country_pop;
        this.country_image = country_image;
        this.bigmac_index = bigmac_index;
        this.country_desc = country_desc;
        this.country_hemisphere = country_hemisphere;
        this.language_top = language_top;
        this.capital_city = capital_city;
        this.country_timezone = country_timezone;
        this.call_code = call_code;
    }

    public int getCountry_id() {
        return country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getCountry_flag() {
        return country_flag;
    }

    public String getCountry_currency() {
        return country_currency;
    }

    public int getCountry_pop() {
        return country_pop;
    }

    public String getCountry_image() {
        return country_image;
    }

    public double getBigmac_index() {
        return bigmac_index;
    }

    public String getCountry_desc() {
        return country_desc;
    }

    public String getCountry_hemisphere() {
        return country_hemisphere;
    }

    public String getLanguage_top() {
        return language_top;
    }

    public String getCapital_city() {
        return capital_city;
    }

    public String getCountry_timezone() {
        return country_timezone;
    }

    public String getCall_code() {
        return call_code;
    }
}
