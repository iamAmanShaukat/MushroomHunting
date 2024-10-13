package com.example.mushroomhunting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mushroomhunting.R;

public class MainActivity extends AppCompatActivity {

    private Button btnAddTrip;
    private Button btnViewTrips;
    private Button btnAddMushroomDetails;
    private Button btnSettings;
    private Button btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        btnAddTrip = findViewById(R.id.btnAddTrip);
        btnViewTrips = findViewById(R.id.btnViewTrips);
        btnAddMushroomDetails = findViewById(R.id.btnAddMushroomDetails);
        btnSettings = findViewById(R.id.btnSettings);
        btnHelp = findViewById(R.id.btnHelp);

        // Set onClick listeners for each button
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Add New Trip Activity
                Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });

        btnViewTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the View Trips Activity
                Intent intent = new Intent(MainActivity.this, ViewTripsActivity.class);
                startActivity(intent);
            }
        });

        btnAddMushroomDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Add Mushroom Details Activity
                Intent intent = new Intent(MainActivity.this, AddMushroomDetailsActivity.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Settings Activity
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Help Activity
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }
}