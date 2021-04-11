package com.example.visit;

import com.example.visit.database.User;

public class LoggedUser {
    private static String username;
    private static String email;
    private static String password;
    private static String createTime;
    private static String firstName;
    private static String lastName;
    private static boolean isLoggedIn = false;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) { LoggedUser.username = username; }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }

    public static String getCreateTime() {
        return createTime;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static Boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(Boolean logged) {
        isLoggedIn = logged;
    }

    public static void setData(User user) {
        username = user.getUsername();
        email = user.getEmail();
        password = user.getPassword();
        createTime = user.getCreateTime();
        firstName = user.getFirstName();
        lastName = user.getLastName();
    }

    @Override
    public String toString() {
        return "User{"
                + "firstName = " + firstName
                + "lastName = " + lastName
                + "username = " + username
                + "password = " + password
                + "email = " + email
                + "createTime = " + createTime
                + "isLoggedIn = " + isLoggedIn
                + "}";
    }
}
