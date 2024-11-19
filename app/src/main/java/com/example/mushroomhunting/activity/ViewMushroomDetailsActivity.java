package com.example.mushroomhunting.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.adapter.MushroomAdapter;
import com.example.mushroomhunting.db.DatabaseManager;
import com.example.mushroomhunting.dto.MushroomDto;
import com.example.mushroomhunting.dto.TripDto;

import java.util.List;

public class ViewMushroomDetailsActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private MushroomAdapter mushroomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mushroom_details);

        databaseManager = new DatabaseManager();

        // Get the trip ID from the intent
        String tripId = getIntent().getStringExtra("tripId");

        if (tripId != null) {
            displayTripDetails(tripId);
        }
    }

    private void displayTripDetails(String tripId) {
        // Fetch trip details
        TripDto trip = databaseManager.getTripDetails(tripId);

        // Display trip details
        if (trip != null) {
            ((TextView) findViewById(R.id.trip_details_text_view)).setText(
                    "Name: " + trip.getTripName() + "\n" +
                            "Date: " + trip.getTripDate() + "\n" +
                            "Time: " + trip.getTripTime() + "\n" +
                            "Location: " + trip.getTripLocation() + "\n" +
                            "Duration: " + trip.getTripDuration() + "\n" +
                            "Description: " + trip.getTripDescription()
            );
        }

        // Fetch and display mushroom details
        List<MushroomDto> mushrooms = databaseManager.getMushroomsForTrip(tripId);
        setupRecyclerView(mushrooms);
    }

    private void setupRecyclerView(List<MushroomDto> mushrooms) {
        RecyclerView recyclerView = findViewById(R.id.mushroom_details_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mushroomAdapter = new MushroomAdapter(this, mushrooms, databaseManager);
        recyclerView.setAdapter(mushroomAdapter);
    }
}
