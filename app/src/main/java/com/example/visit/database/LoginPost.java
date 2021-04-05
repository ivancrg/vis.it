package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

// Class is used for two purposes:
// 1. Used for generating POST request body (only username field used)
// 2. Used for receiving POST request response (username field null)

public class LoginPost {
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("feedback")
    private String feedback;

    public LoginPost(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "LoginPost{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
