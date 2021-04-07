package com.example.visit;

public class LoggedUser{
    private static String username = "Korisnik nije ulogiran";
    private static boolean isLoggedIn = false;

    public static String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public static Boolean getIsLoggedIn()
    {
        return isLoggedIn;
    }

    public void setIsLoggedIn(Boolean isLoggedIn)
    {
        this.isLoggedIn = isLoggedIn;
    }

}
