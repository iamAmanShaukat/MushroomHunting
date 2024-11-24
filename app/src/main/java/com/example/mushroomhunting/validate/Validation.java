package com.example.mushroomhunting.validate;


import android.text.TextUtils;

import com.example.mushroomhunting.dto.MushroomDto;
import com.example.mushroomhunting.dto.TripDto;

public class Validation {

    public String validateTripDetails(TripDto tripDto){

        if (TextUtils.isEmpty(tripDto.getTripName())) {
            return "Please enter the trip name";
        }

        if (TextUtils.isEmpty(tripDto.getTripDate())) {
            return "Please enter trip date";
        }

        if (TextUtils.isEmpty(tripDto.getTripTime())) {
            return "Please enter the trip time";
        }

        if (TextUtils.isEmpty(tripDto.getTripLocation())) {
            return "Please enter trip location";
        }

        if (TextUtils.isEmpty(tripDto.getTripDuration())) {
            return "Please enter expected trip duration";
        }

        if (!TextUtils.isEmpty(tripDto.getTripDescription()) && tripDto.getTripDescription().length() > 250) {
            return "Description too long";
        }
        return "";
    }

    public String validateMushroomDetails(MushroomDto mushroomDto){

        if (TextUtils.isEmpty(mushroomDto.getMushroomType())) {
            return "Please enter the type of mushroom";
        }

        if (TextUtils.isEmpty(mushroomDto.getMushroomLocation())) {
            return "Please enter the location where mushrooms were found";
        }

        if (TextUtils.isEmpty(mushroomDto.getMushroomLocation())) {
            return "Please enter the quantity of mushrooms found";
        }

        if (TextUtils.isEmpty(mushroomDto.getMushroomQuantity())) {
            return "Please enter the quantity of mushrooms found";
        }
        if (!TextUtils.isEmpty(mushroomDto.getComments()) && mushroomDto.getComments().length() > 250) {
            return "Comment too long";
        }

        return "";
    }
}