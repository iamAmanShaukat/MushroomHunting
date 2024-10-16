package com.example.mushroomhunting.dto;

public class ViewTripDTO {
    private String name;
    private String date;
    private String location;

    public ViewTripDTO(String name, String date) {
        this.name = name;
        this.date = date;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}