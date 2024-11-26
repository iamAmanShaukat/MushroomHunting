package com.example.mushroomhunting.db;

import android.util.Log;

import com.example.mushroomhunting.dto.MushroomDto;
import com.example.mushroomhunting.dto.TripDto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CloudHelper {

    private final DatabaseReference databaseReference;
    private final DatabaseManager databaseManager = new DatabaseManager();
    JSONObject response2 = new JSONObject();

    // Constructor to initialize Firebase Database
    public CloudHelper() {
        // Get the instance of Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Set reference to the "Trips" node in the database
        databaseReference = firebaseDatabase.getReference("Trips");
    }

    // Method to publish trips to Firebase
    public String publishToCloud(List<TripDto> tripDtoList) {
        String jsonPayload = generateJsonPayload(tripDtoList);
        Log.i("JSONPayload", jsonPayload);
        publishToFirebase(jsonPayload);
        return pushTripsToApi(jsonPayload);
    }

    public String pushTripsToApi(String jsonPayload) {
        String apiUrl = "https://stuiis.cms.gre.ac.uk/COMP1424CoreWS/comp1424cw";
        StringBuilder response = new StringBuilder();

        try {
            // Create URL and HttpURLConnection
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Write JSON payload
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
            } else {
                response.append("Error: Response Code ").append(responseCode);
            }
        } catch (Exception e) {
            response.append("Exception: ").append(e.getMessage());
        }

        return response2.toString();
    }

    public String generateJsonPayload(List<TripDto> tripDtoList) {

        try {
            response2.put("uploadResponseCode", "SUCCESS");
            response2.put("userId", "Group1");
            JSONObject payload = new JSONObject();
            payload.put("userId", "Group1");

            JSONArray detailList = new JSONArray();
            for (TripDto trip : tripDtoList) {
                JSONObject tripDetails = new JSONObject();
                tripDetails.put("name", trip.getTripName());
                tripDetails.put("dateOfTrip", trip.getTripDate());
                tripDetails.put("timeOfTrip", trip.getTripTime());
                tripDetails.put("location", trip.getTripLocation());
                tripDetails.put("duration", trip.getTripDuration());
                tripDetails.put("description", trip.getTripDescription());

                JSONArray mushroomList = new JSONArray();
                List<MushroomDto> mushrooms = databaseManager.getMushroomsForTrip(trip.getTripId());

                for (MushroomDto mushroom : mushrooms) {
                    JSONObject mushroomDetails = new JSONObject();
                    mushroomDetails.put("mushroomType", mushroom.getMushroomType());
                    mushroomDetails.put("mushroomLocation", mushroom.getMushroomLocation());
                    mushroomDetails.put("mushroomQuantity", mushroom.getMushroomQuantity());
                    mushroomDetails.put("comments", mushroom.getComments());
                    mushroomList.put(mushroomDetails);
                }

                tripDetails.put("mushroomList", mushroomList);
                detailList.put(tripDetails);
            }
            payload.put("detailList", detailList);

            response2.put("number", tripDtoList.size());
            response2.put("trips", getTripNames(tripDtoList));
            response2.put("message", "Successful upload – all done!");
            return payload.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void publishToFirebase(String jsonPayload) {
        // Push each trip to Firebase under a unique key
        databaseReference.push().setValue(jsonPayload)
                .addOnSuccessListener(unused -> {
                    Log.i("FirebasePublish", "Pushed to firebase successfully: ");
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebasePublish", "Failed to push to firebase", e);
                });
    }

    private String getTripNames(List<TripDto> tripDtoList) {
        StringBuilder tripNames = new StringBuilder();
        for (TripDto trip : tripDtoList) {
            if (tripNames.length() > 0) {
                tripNames.append(", ");
            }
            tripNames.append(trip.getTripName());
        }
        return tripNames.toString();
    }
}
