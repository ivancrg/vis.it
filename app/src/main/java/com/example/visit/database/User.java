package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("create_time")
    private String createTime;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("feedback")
    private String feedback;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString(){
        return "User{"
                + "firstName = " + firstName
                + "lastName = " + lastName
                + "username = " + username
                + "password = " + password
                + "email = " + email
                + "createTime = " + createTime
                + "feedback = " + feedback
                + "}";
    }
}
