package com.example.mushroomhunting.dto;

import android.text.TextUtils;

import androidx.annotation.NonNull;

public class MushroomDto {

    String mushroomType;
    String mushroomLocation;
    String mushroomQuantity;
    String comments;
    String mushroomId;
    String tripId;


    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getMushroomId() {
        return mushroomId;
    }

    public void setMushroomId(String mushroomId) {
        this.mushroomId = mushroomId;
    }
    public String getMushroomType() {
        return mushroomType;
    }

    public void setMushroomType(String mushroomType) {
        this.mushroomType = mushroomType;
    }

    public String getMushroomLocation() {
        return mushroomLocation;
    }

    public void setMushroomLocation(String mushroomLocation) {
        this.mushroomLocation = mushroomLocation;
    }

    public String getMushroomQuantity() {
        return mushroomQuantity;
    }

    public void setMushroomQuantity(String mushroomQuantity) {
        this.mushroomQuantity = mushroomQuantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public String toString() {
        return "Mushroom Type: " + mushroomType +
                "\nLocation: " + mushroomLocation +
                "\nQuantity: " + mushroomQuantity +
                "\nComments: " + (TextUtils.isEmpty(comments) ? "None" : comments);
    }
}
