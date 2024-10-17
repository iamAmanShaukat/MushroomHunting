package com.example.mushroomhunting.dto;

import android.text.TextUtils;

import androidx.annotation.NonNull;

public class TripDto {

    String tripName;
    String tripDate;
    String tripTime;
    String tripLocation;
    String tripDuration;
    String tripDescription;


    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public String getTripLocation() {
        return tripLocation;
    }

    public void setTripLocation(String tripLocation) {
        this.tripLocation = tripLocation;
    }

    public String getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(String tripDuration) {
        this.tripDuration = tripDuration;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    @NonNull
    @Override
    public String toString(){
        return "Name: " + tripName +
                "\nDate: " + tripDate +
                "\nTime: " + tripTime +
                "\nLocation: " + tripLocation +
                "\nDuration: " + tripDuration +
                "\nDescription: " + (TextUtils.isEmpty(tripDescription) ? "None" : tripDescription);
    }
}
