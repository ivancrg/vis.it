package com.example.visit.database;

// Class is used for two purposes:
// 1. Used for generating POST request body (only feedback field unused)
// 2. Used for receiving POST request response (only feedback used)

import com.google.gson.annotations.SerializedName;

public class RegisterPost {
    private String username;
    private String email;
    private String password;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    public RegisterPost(String username, String email, String password, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
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
        return "RegisterPost{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
