package com.example.mushroomhunting.activity;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.mushroomhunting.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.mushroomhunting.db.DatabaseManager;

public class ClearActivity extends AppCompatActivity {
    private DatabaseManager databaseManager;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Trips");
        databaseManager = new DatabaseManager();

        Button buttonClear = findViewById(R.id.buttonClear);

        buttonClear.setOnClickListener(v -> showClearConfirmationDialog());
    }


    private void showClearConfirmationDialog() {
        // Create the alert dialog
        new AlertDialog.Builder(this)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to clear all Data?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Perform the deletion (e.g., delete from database, list, etc.)
                    clearAllData();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Dismiss the dialog without doing anything
                    dialog.dismiss();
                })
                .show();
    }


        private void clearAllData() {
            // Clear all data from the Firebase database reference
            databaseReference.removeValue()
                    .addOnSuccessListener(unused -> {
                        // Notify the user on successful deletion
                        Toast.makeText(ClearActivity.this, "All data cleared successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Notify the user if the deletion failed
                        Toast.makeText(ClearActivity.this, "Failed to clear data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
            // Clear SQLite database
            databaseManager.clearAllData();
            Toast.makeText(ClearActivity.this, "Local database cleared successfully!", Toast.LENGTH_SHORT).show();
        }
    }
