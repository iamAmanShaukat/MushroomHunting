package com.example.mushroomhunting.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mushroomhunting.R;

public class TripDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        // Retrieve trip data from the Intent
        String tripName = getIntent().getStringExtra("trip_name");
        String tripDetails = getIntent().getStringExtra("trip_details");

        // Find the TextViews and set the trip data
        TextView tripNameTextView = findViewById(R.id.tripNameTextView);
        TextView tripDetailsTextView = findViewById(R.id.tripDetailsTextView);

        tripNameTextView.setText(tripName);
        tripDetailsTextView.setText(tripDetails);
    }
}