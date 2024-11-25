package com.example.mushroomhunting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.db.CloudHelper;
import com.example.mushroomhunting.db.DatabaseManager;
import com.example.mushroomhunting.dto.TripDto;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    CloudHelper cloudHelper = new CloudHelper();
    DatabaseManager databaseManager = new DatabaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Find the TextViews by their IDs
        TextView textViewHelp = findViewById(R.id.textViewHelp);
        TextView textViewPrivacy = findViewById(R.id.textViewPrivacy);
        TextView textViewSync = findViewById(R.id.textViewSync);
        TextView textViewDelete = findViewById(R.id.textViewDelete);

        // Set click listeners for each TextView
        textViewHelp.setOnClickListener(v -> openHelpActivity());
        textViewPrivacy.setOnClickListener(v -> openPrivacyActivity());
        textViewSync.setOnClickListener(v -> openSyncSettingsActivity());
        textViewDelete.setOnClickListener(v -> openClearAccountActivity());
    }

    private void openHelpActivity() {
        Intent intent = new Intent(SettingsActivity.this, HelpActivity.class);
        startActivity(intent);
    }

    private void openPrivacyActivity() {
        // Navigate to PrivacyActivity (to be created)
        Intent intent = new Intent(SettingsActivity.this, PrivacyActivity.class);
        startActivity(intent);
    }



    private void openSyncSettingsActivity() {
        // Navigate to SyncSettingsActivity (to be created)
        List<TripDto> allTrips = databaseManager.getAllTrips();
        cloudHelper.publishToFirebase(allTrips);
        Toast.makeText(this, "Please select a trip first.", Toast.LENGTH_SHORT).show();


    }

    private void openClearAccountActivity() {
        // Navigate to ClearAccountActivity (to be created)
        Intent intent = new Intent(SettingsActivity.this, ClearActivity.class);
        startActivity(intent);
    }


}


