package com.example.mushroomhunting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.db.CloudHelper;
import com.example.mushroomhunting.db.DatabaseManager;
import com.example.mushroomhunting.dto.TripDto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    CloudHelper cloudHelper = new CloudHelper();
    DatabaseManager databaseManager = new DatabaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
        String apiResponse = cloudHelper.publishToCloud(allTrips);
        showResponseDialog(this, "Upload Response", apiResponse);
        Toast.makeText(this, "Data synced successfully", Toast.LENGTH_SHORT).show();


    }

    private void openClearAccountActivity() {
        // Navigate to ClearAccountActivity (to be created)
        Intent intent = new Intent(SettingsActivity.this, ClearActivity.class);
        startActivity(intent);
    }


    private void showResponseDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(formatJsonResponse(message))
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    public String formatJsonResponse(String jsonResponse) {
        try {
            // Convert the JSON string into a JSONObject
            JSONObject responseJson = new JSONObject(jsonResponse);

            // StringBuilder to store the formatted response
            StringBuilder formattedResponse = new StringBuilder();

            // Iterate through all the keys in the JSON object and format them
            Iterator<String> keys = responseJson.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = responseJson.getString(key);

                // Append the key-value pair to the StringBuilder
                formattedResponse.append(key).append(" : ").append(value).append("\n");
            }

            // Return the formatted response as a String
            return formattedResponse.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "Error formatting response";
        }
    }


}


