package com.example.mushroomhunting.activity;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.mushroomhunting.R;

public class ClearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        // Initialise button and set an OnClickListener
        Button buttonClear = findViewById(R.id.buttonClear);

        buttonClear.setOnClickListener(v -> {
            showClearConfirmationDialog();
        });
    }

    private void showClearConfirmationDialog() {
        // Creates the alert dialog
        new AlertDialog.Builder(this)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to clear this item?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Perform the deletion (e.g., delete from database, list, etc.)
                    clearItem();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Dismiss the dialog without doing anything
                    dialog.dismiss();
                })
                .show();
    }

    private void clearItem() {
        Toast.makeText(ClearActivity.this, "Item cleared successfully", Toast.LENGTH_SHORT).show();

    }
}
