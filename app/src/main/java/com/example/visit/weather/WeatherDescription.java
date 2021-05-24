package com.example.visit.weather;

import com.google.gson.annotations.SerializedName;

public class WeatherDescription {
    // Weather condition ID
    @SerializedName("id")
    private String id;

    // Group of weather parameters (Rain, Snow, Extreme etc.)
    @SerializedName("main")
    private String description;

    // Weather condition within the group (full list of weather conditions)
    @SerializedName("description")
    private String detailedDescription;

    // Weather icon ID
    @SerializedName("icon")
    private String iconID;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public String getIconID() {
        return iconID;
    }

    @Override
    public String toString() {
        return "WeatherDescription{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", detailedDescription='" + detailedDescription + '\'' +
                ", iconID='" + iconID + '\'' +
                '}';
    }
}
