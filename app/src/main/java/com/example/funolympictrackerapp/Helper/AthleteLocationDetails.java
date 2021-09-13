package com.example.funolympictrackerapp.Helper;

public class AthleteLocationDetails {

    String user_id, venue, time, date;

    public AthleteLocationDetails(String user_id, String venue, String time, String date) {
        this.user_id = user_id;
        this.venue = venue;
        this.time = time;
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
