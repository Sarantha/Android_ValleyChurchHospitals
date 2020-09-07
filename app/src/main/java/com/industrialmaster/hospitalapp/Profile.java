package com.industrialmaster.hospitalapp;

public class Profile {
    private String Date;
    private String Name;
    private String Special;

    public Profile() {
    }

    public Profile(String date, String name, String special) {
        Date = date;
        Name = name;
        Special = special;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSpecial() {
        return Special;
    }

    public void setSpecial(String special) {
        Special = special;
    }
}
