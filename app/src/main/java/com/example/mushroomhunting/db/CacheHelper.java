package com.example.mushroomhunting.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mushroomhunting.dto.TripDto;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CacheHelper {
    private static final String SHARED_PREFS_NAME = "TripCachePrefs";
    private static final String KEY_LAST_TRIPS = "LastTwoTrips";

    public static void cacheTrip(Context context, TripDto newTrip) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        // Retrieve existing trips
        String json = prefs.getString(KEY_LAST_TRIPS, null);
        Type type = new TypeToken<List<TripDto>>() {}.getType();
        List<TripDto> tripList = json == null ? new ArrayList<>() : gson.fromJson(json, type);

        // Add new trip and keep only the last two
        if (tripList.size() == 2) {
            tripList.remove(0);
        }
        tripList.add(newTrip);

        // Save updated list
        String updatedJson = gson.toJson(tripList);
        prefs.edit().putString(KEY_LAST_TRIPS, updatedJson).apply();
    }

    public static List<TripDto> getLastTwoTrips(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_LAST_TRIPS, null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<TripDto>>() {}.getType();
            return gson.fromJson(json, type);
        }
        return new ArrayList<>();
    }
}