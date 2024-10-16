package com.example.mushroomhunting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import com.example.mushroomhunting.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize layout containers instead of buttons
        ConstraintLayout addTripLayout = findViewById(R.id.addTripLayout);
        ConstraintLayout viewTripsLayout = findViewById(R.id.viewTripsLayout);
        ConstraintLayout addMushroomDetailsLayout = findViewById(R.id.addMushroomDetailsLayout);
        TextView settingsButton = findViewById(R.id.settingsButton);
        TextView helpButton = findViewById(R.id.helpButton);

        // Set up the Recent Activity Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recentFragmentContainer, new RecentActivityFragment())
                .commit();

        // Set onClick listeners for each layout
        addTripLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Add New Trip Activity
                Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });

        viewTripsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the View Trips Activity
                Intent intent = new Intent(MainActivity.this, ViewTripsActivity.class);
                startActivity(intent);
            }
        });

        addMushroomDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Add Mushroom Details Activity
                Intent intent = new Intent(MainActivity.this, AddMushroomDetailsActivity.class);
                startActivity(intent);
            }
        });

        // Set onClick listeners for Settings and Help buttons
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Settings Activity
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Help Activity
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }
}