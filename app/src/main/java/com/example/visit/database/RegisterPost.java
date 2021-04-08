package com.example.visit.database;

// Class is used for two purposes:
// 1. Used for generating POST request body (only feedback field unused)
// 2. Used for receiving POST request response (only feedback used)

import com.google.gson.annotations.SerializedName;

public class RegisterPost {
    private String username;
    private String email;
    private String password;
    private String first_name;
    private String last_name;

    public RegisterPost(String username, String email, String password, String first_name, String last_name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
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
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
