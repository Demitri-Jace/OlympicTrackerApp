package com.example.funolympictrackerapp.Helper;

public class CurrentLocation {

    String user_id, loc_one, loc_two, loc_three, loc_four, loc_five;

    public CurrentLocation(String user_id, String loc_one, String loc_two, String loc_three, String loc_four, String loc_five) {
        this.user_id = user_id;
        this.loc_one = loc_one;
        this.loc_two = loc_two;
        this.loc_three = loc_three;
        this.loc_four = loc_four;
        this.loc_five = loc_five;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLoc_one() {
        return loc_one;
    }

    public void setLoc_one(String loc_one) {
        this.loc_one = loc_one;
    }

    public String getLoc_two() {
        return loc_two;
    }

    public void setLoc_two(String loc_two) {
        this.loc_two = loc_two;
    }

    public String getLoc_three() {
        return loc_three;
    }

    public void setLoc_three(String loc_three) {
        this.loc_three = loc_three;
    }

    public String getLoc_four() {
        return loc_four;
    }

    public void setLoc_four(String loc_four) {
        this.loc_four = loc_four;
    }

    public String getLoc_five() {
        return loc_five;
    }

    public void setLoc_five(String loc_five) {
        this.loc_five = loc_five;
    }
}
