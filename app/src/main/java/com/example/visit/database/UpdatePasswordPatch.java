package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

public class UpdatePasswordPatch {
    private String username;
    private String password;

    public UpdatePasswordPatch(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @SerializedName("feedback")
    private String feedback;

    public String getFeedback() {
        return feedback;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String toString() {
        return "UpdatePasswordPatch{" +
                "username='" + username + '\'' +
                ", last_name='" + password + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
