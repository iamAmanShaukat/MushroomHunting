package com.example.mushroomhunting.activity;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.Button;

import com.example.mushroomhunting.R;

public class PrivacyActivity extends AppCompatActivity {

    private CheckBox checkboxLocationSharing;
    private CheckBox checkboxDataCollection;
    private CheckBox checkboxPhotoSharing;
    private CheckBox checkboxNotifications;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        // Initialize the views
        checkboxLocationSharing = findViewById(R.id.checkboxLocationSharing);
        checkboxDataCollection = findViewById(R.id.checkboxDataCollection);
        checkboxPhotoSharing = findViewById(R.id.checkboxPhotoSharing);
        checkboxNotifications = findViewById(R.id.checkboxNotifications);
        Button buttonSaveSettings = findViewById(R.id.buttonSaveSettings);

        // Initialize SharedPreferences to store the privacy settings
        sharedPreferences = getSharedPreferences("PrivacySettings", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Load the existing settings (if any)
        loadSettings();

        // Set up the button click listener to save settings
        buttonSaveSettings.setOnClickListener(v -> saveSettings());
    }

    private void loadSettings() {
        // Retrieve saved settings from SharedPreferences
        boolean locationSharing = sharedPreferences.getBoolean("LocationSharing", false);
        boolean dataCollection = sharedPreferences.getBoolean("DataCollection", false);
        boolean photoSharing = sharedPreferences.getBoolean("PhotoSharing", false);
        boolean notifications = sharedPreferences.getBoolean("Notifications", false);

        // Set the checkboxes based on saved settings
        checkboxLocationSharing.setChecked(locationSharing);
        checkboxDataCollection.setChecked(dataCollection);
        checkboxPhotoSharing.setChecked(photoSharing);
        checkboxNotifications.setChecked(notifications);
    }

    private void saveSettings() {
        // Save the current state of each checkbox to SharedPreferences
        editor.putBoolean("LocationSharing", checkboxLocationSharing.isChecked());
        editor.putBoolean("DataCollection", checkboxDataCollection.isChecked());
        editor.putBoolean("PhotoSharing", checkboxPhotoSharing.isChecked());
        editor.putBoolean("Notifications", checkboxNotifications.isChecked());

        // Commit changes
        editor.apply();

        // Show a confirmation message
        Toast.makeText(this, "Privacy settings saved!", Toast.LENGTH_SHORT).show();
    }
}


