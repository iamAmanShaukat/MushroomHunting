package com.example.mushroomhunting.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.db.DatabaseManager;
import com.example.mushroomhunting.dto.MushroomDto;
import com.example.mushroomhunting.dto.TripDto;

import java.util.List;

public class TripDetailsActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        // Initialize DatabaseManager
        databaseManager = new DatabaseManager();

        // Get the trip ID from the intent
        String tripId = getIntent().getStringExtra("tripId");

        // Load and display trip and mushroom details
        if (tripId != null) {
            displayTripDetails(tripId);
        }
    }

    private void displayTripDetails(String tripId) {
        // Fetch trip details
        TripDto trip = databaseManager.getTripDetails(tripId);

        if (trip != null) {
            ((TextView) findViewById(R.id.trip_name)).setText(trip.getTripName());
            ((TextView) findViewById(R.id.trip_date)).setText(trip.getTripDate());
            ((TextView) findViewById(R.id.trip_time)).setText(trip.getTripTime());
            ((TextView) findViewById(R.id.trip_location)).setText(trip.getTripLocation());
            ((TextView) findViewById(R.id.trip_duration)).setText(trip.getTripDuration());
            ((TextView) findViewById(R.id.trip_description)).setText(trip.getTripDescription());
        } else {
            ((TextView) findViewById(R.id.trip_name)).setText("Trip not found");
        }

        // Fetch and display mushrooms for the trip
        List<MushroomDto> mushrooms = databaseManager.getMushroomsForTrip(tripId);
        displayMushroomDetails(mushrooms);
    }

    private void displayMushroomDetails(List<MushroomDto> mushrooms) {
        LinearLayout mushroomDetailsContainer = findViewById(R.id.mushroom_details_container);
        mushroomDetailsContainer.removeAllViews(); // Clear existing views to avoid duplication

        if (mushrooms.isEmpty()) {
            // Display a message if no mushrooms are associated with the trip
            TextView noMushroomsTextView = new TextView(this);
            noMushroomsTextView.setText("No mushrooms associated with this trip.");
            noMushroomsTextView.setTextSize(16);
            noMushroomsTextView.setTextColor(getResources().getColor(android.R.color.black));
            noMushroomsTextView.setTypeface(getResources().getFont(R.font.mushroom_font)); // Apply font
            mushroomDetailsContainer.addView(noMushroomsTextView);
        } else {
            // Add details for each mushroom dynamically
            int count = 1;
            for (MushroomDto mushroom : mushrooms) {
                TextView mushroomDetailsTextView = new TextView(this);
                mushroomDetailsTextView.setText(
                        "Mushroom " + count++ + ":\n" +
                                "Type: " + mushroom.getMushroomType() + "\n" +
                                "Quantity: " + mushroom.getMushroomQuantity() + "\n" +
                                "Location: " + mushroom.getMushroomLocation() + "\n" +
                                "Comments: " + mushroom.getComments() + "\n"
                );
                mushroomDetailsTextView.setTextSize(16);
                mushroomDetailsTextView.setTextColor(getResources().getColor(android.R.color.holo_purple));
                mushroomDetailsTextView.setTypeface(getResources().getFont(R.font.mushroom_font)); // Apply font
                mushroomDetailsTextView.setPadding(0, 16, 0, 16);

                mushroomDetailsContainer.addView(mushroomDetailsTextView);
            }
        }
    }
}

