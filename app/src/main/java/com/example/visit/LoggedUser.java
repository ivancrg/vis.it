package com.example.visit;

public class LoggedUser{
    private static String username = "Korisnik nije ulogiran";
    private static boolean isLoggedIn = false;

    public static String getUsername()
    {
        return username;
    }

    public static void setUsername(String loggedUsername)
    {
        username = loggedUsername;
    }

    public static Boolean getIsLoggedIn()
    {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(Boolean logged){
        isLoggedIn = logged;
    }

}
