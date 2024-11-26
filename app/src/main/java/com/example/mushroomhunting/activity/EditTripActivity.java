package com.example.mushroomhunting.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.db.DatabaseManager;
import com.example.mushroomhunting.dto.TripDto;

public class EditTripActivity extends AppCompatActivity {

    private EditText tripNameInput, tripDateInput, tripTimeInput, tripLocationInput, tripDurationInput, tripDescriptionInput;
    private DatabaseManager databaseManager;
    private String tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        // Initialise DatabaseManager
        databaseManager = new DatabaseManager();

        // Get tripId from Intent
        tripId = getIntent().getStringExtra("tripId");

        // Initialize input fields
        tripNameInput = findViewById(R.id.edit_trip_name);
        tripDateInput = findViewById(R.id.edit_trip_date);
        tripTimeInput = findViewById(R.id.edit_trip_time);
        tripLocationInput = findViewById(R.id.edit_trip_location);
        tripDurationInput = findViewById(R.id.edit_trip_duration);
        tripDescriptionInput = findViewById(R.id.edit_trip_description);

        // Loads trip details
        loadTripDetails();

        // Saves changes
        findViewById(R.id.save_trip_button).setOnClickListener(v -> saveTripDetails());
    }

    private void loadTripDetails() {
        TripDto trip = databaseManager.getTripDetails(tripId);
        if (trip != null) {
            tripNameInput.setText(trip.getTripName());
            tripDateInput.setText(trip.getTripDate());
            tripTimeInput.setText(trip.getTripTime());
            tripLocationInput.setText(trip.getTripLocation());
            tripDurationInput.setText(trip.getTripDuration());
            tripDescriptionInput.setText(trip.getTripDescription());
        } else {
            Toast.makeText(this, "Trip not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveTripDetails() {
        TripDto updatedTrip = new TripDto();
        updatedTrip.setTripId(tripId);
        updatedTrip.setTripName(tripNameInput.getText().toString().trim());
        updatedTrip.setTripDate(tripDateInput.getText().toString().trim());
        updatedTrip.setTripTime(tripTimeInput.getText().toString().trim());
        updatedTrip.setTripLocation(tripLocationInput.getText().toString().trim());
        updatedTrip.setTripDuration(tripDurationInput.getText().toString().trim());
        updatedTrip.setTripDescription(tripDescriptionInput.getText().toString().trim());

        if (databaseManager.saveTrip(updatedTrip)) {
            Toast.makeText(this, "Trip updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update trip", Toast.LENGTH_SHORT).show();
        }
    }
}
