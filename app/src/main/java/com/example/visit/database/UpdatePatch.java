package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

public class UpdatePatch {

    private String username;
    private String email;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    public UpdatePatch(String username, String email, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @SerializedName("feedback")
    private String feedback;

    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "UpdatePatch{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
