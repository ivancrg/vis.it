package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

public class UpdatePatch {

    private String username;
    private String email;
    private String first_name;
    private String last_name;

    public UpdatePatch(String username, String email, String first_name, String last_name) {
        this.username = username;
        this.email = email;
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
        return "UpdatePatch{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
