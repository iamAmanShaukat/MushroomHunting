package com.example.mushroomhunting.activity;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.mushroomhunting.R;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        // Initialize button and set an OnClickListener
        Button buttonDelete = findViewById(R.id.buttonDelete);

        buttonDelete.setOnClickListener(v -> {
            // Show the delete confirmation dialog
            showDeleteConfirmationDialog();
        });
    }

    private void showDeleteConfirmationDialog() {
        // Create the alert dialog
        new AlertDialog.Builder(this)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this item?")
                .setCancelable(false) // Prevent dismissing by tapping outside the dialog
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Perform the deletion (e.g., delete from database, list, etc.)
                    deleteItem();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Dismiss the dialog without doing anything
                    dialog.dismiss();
                })
                .show();
    }

    private void deleteItem() {
        // Your logic for deletion (e.g., remove item from a list or database)
        // For now, we just show a Toast message
        Toast.makeText(DeleteActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();

        // Optionally, you could return to the previous activity, or close the activity
        // finish();  // If you want to close the current activity after deletion
    }
}
