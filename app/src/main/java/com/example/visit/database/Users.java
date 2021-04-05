package com.example.visit.database;

import com.google.gson.annotations.SerializedName;

public class Users {
    // We can remove the following line as variable's
    // name is identical to JSON field
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

    @Override
    public String toString(){
        return "User{"
                + "firstName = " + firstName
                + "lastName = " + lastName
                + "username = " + username
                + "password = " + password
                + "email = " + email
                + "createTime = " + createTime
                + "}";
    }
}
