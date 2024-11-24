package com.example.mushroomhunting.db;

import android.util.Log;

import com.example.mushroomhunting.dto.TripDto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CloudHelper {

    private final DatabaseReference databaseReference;

    // Constructor to initialize Firebase Database
    public CloudHelper() {
        // Get the instance of Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Set reference to the "Trips" node in the database
        databaseReference = firebaseDatabase.getReference("Trips");
    }

    // Method to publish trips to Firebase
    public void publishToFirebase(List<TripDto> tripDtoList) {
        for (TripDto tripDto : tripDtoList) {
            // Push each trip to Firebase under a unique key
            databaseReference.push().setValue(tripDto)
                    .addOnSuccessListener(unused -> {
                        Log.i("FirebasePublish", "Trip pushed successfully: " + tripDto.getTripName());
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FirebasePublish", "Failed to push trip: " + tripDto.getTripName(), e);
                    });


        }
    }
}
