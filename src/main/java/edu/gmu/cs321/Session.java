package edu.gmu.cs321;

public class Session {
    private static Immigrant currentImmigrant;

    public static void setCurrentImmigrant(Immigrant immigrant) {
        currentImmigrant = immigrant;
    }

    public static Immigrant getCurrentImmigrant() {
        return currentImmigrant;
    }

    public static void clearSession() {
        currentImmigrant = null;
    }
}
