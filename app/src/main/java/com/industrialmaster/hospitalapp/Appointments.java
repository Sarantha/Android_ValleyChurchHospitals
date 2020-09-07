package com.industrialmaster.hospitalapp;

public class Appointments {
    private String bookDate;
    private String doctor;
    private String name;
    private String userId;

    public Appointments() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Appointments(String bookDate, String doctor, String name, String userId) {
        this.bookDate = bookDate;
        this.doctor = doctor;
        this.name = name;
        this.userId = userId;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
