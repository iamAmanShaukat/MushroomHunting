package com.example.mushroomhunting.db;

import com.example.mushroomhunting.dto.TripDto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CloudHelper {

    // Firebase Database reference
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
        // Iterate through the list of TripDto objects
        for (TripDto tripDto : tripDtoList) {
            // Push each trip to Firebase under a unique key
            databaseReference.push().setValue(tripDto)
                    .addOnSuccessListener(unused -> {
                        // Log or notify success

                        System.out.println("Trip pushed successfully: " + tripDto.getTripName());
                    })
                    .addOnFailureListener(e -> {
                        // Log or handle failure
                        System.err.println("Failed to push trip: " + tripDto.getTripName() + " - " + e.getMessage());
                    });


        }
    }
}
