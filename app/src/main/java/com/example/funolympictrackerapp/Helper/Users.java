package com.example.funolympictrackerapp.Helper;

public class Users {

    String athlete_name, athlete_surname, athlete_email, athlete_password, athlete_confirm_password;

    public Users(String athlete_name, String athlete_surname, String athlete_email, String athlete_password, String athlete_confirm_password) {
        this.athlete_name = athlete_name;
        this.athlete_surname = athlete_surname;
        this.athlete_email = athlete_email;
        this.athlete_password = athlete_password;
        this.athlete_confirm_password = athlete_confirm_password;
    }

    public String getAthlete_name() {
        return athlete_name;
    }

    public void setAthlete_name(String athlete_name) {
        this.athlete_name = athlete_name;
    }

    public String getAthlete_surname() {
        return athlete_surname;
    }

    public void setAthlete_surname(String athlete_surname) {
        this.athlete_surname = athlete_surname;
    }

    public String getAthlete_email() {
        return athlete_email;
    }

    public void setAthlete_email(String athlete_email) {
        this.athlete_email = athlete_email;
    }

    public String getAthlete_password() {
        return athlete_password;
    }

    public void setAthlete_password(String athlete_password) {
        this.athlete_password = athlete_password;
    }

    public String getAthlete_confirm_password() {
        return athlete_confirm_password;
    }

    public void setAthlete_confirm_password(String athlete_confirm_password) {
        this.athlete_confirm_password = athlete_confirm_password;
    }
}
