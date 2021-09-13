package com.example.funolympictrackerapp.Helper;

public class AthleteLocations {

    String user_id, departure, arrival;

    public AthleteLocations(String user_id, String departure, String arrival) {
        this.user_id = user_id;
        this.departure = departure;
        this.arrival = arrival;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }
}
