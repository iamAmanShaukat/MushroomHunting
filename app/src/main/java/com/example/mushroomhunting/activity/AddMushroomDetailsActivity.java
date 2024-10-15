package com.example.mushroomhunting.activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mushroomhunting.R;

public class AddMushroomDetailsActivity extends AppCompatActivity {

    private EditText mushroomTypeInput;
    private EditText mushroomLocationInput;
    private EditText mushroomQuantityInput;
    private EditText additionalCommentsInput;
    private Button saveMushroomDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mushroom_details);

        // Initialize the input fields and button
        mushroomTypeInput = findViewById(R.id.mushroom_type_input);
        mushroomLocationInput = findViewById(R.id.mushroom_location_input);
        mushroomQuantityInput = findViewById(R.id.mushroom_quantity_input);
        additionalCommentsInput = findViewById(R.id.additional_comments_input);
        saveMushroomDetails = findViewById(R.id.save_mushroom_details);

        // Set onClickListener for the save button
        saveMushroomDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMushroomDetails();
            }
        });
    }

    private void saveMushroomDetails() {
        // Get the input values
        String mushroomType = mushroomTypeInput.getText().toString().trim();
        String mushroomLocation = mushroomLocationInput.getText().toString().trim();
        String mushroomQuantity = mushroomQuantityInput.getText().toString().trim();
        String additionalComments = additionalCommentsInput.getText().toString().trim();

        // Validate required fields
        if (TextUtils.isEmpty(mushroomType)) {
            Toast.makeText(this, "Please enter the type of mushroom", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mushroomLocation)) {
            Toast.makeText(this, "Please enter the location where mushrooms were found", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mushroomQuantity)) {
            Toast.makeText(this, "Please enter the quantity of mushrooms found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert quantity to integer
        int quantity;
        try {
            quantity = Integer.parseInt(mushroomQuantity);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number for the quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show the confirmation dialog
        showConfirmationDialog(mushroomType, mushroomLocation, quantity, additionalComments);
    }

    private void showConfirmationDialog(String mushroomType, String mushroomLocation, int mushroomQuantity, String additionalComments) {
        // Prepare the dialog message
        String message = "Mushroom Type: " + mushroomType +
                "\nLocation: " + mushroomLocation +
                "\nQuantity: " + mushroomQuantity +
                "\nAdditional Comments: " + (TextUtils.isEmpty(additionalComments) ? "None" : additionalComments);

        // Create the dialog
        new AlertDialog.Builder(this)
                .setTitle("Confirm Mushroom Details")
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Save the details and show a success message
                        Toast.makeText(AddMushroomDetailsActivity.this, "Mushroom details saved successfully!", Toast.LENGTH_SHORT).show();
                        clearForm();
                    }
                })
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the dialog and allow user to edit
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //Clear form after submission
    private void clearForm() {
        mushroomTypeInput.setText("");
        mushroomLocationInput.setText("");
        mushroomQuantityInput.setText("");
        additionalCommentsInput.setText("");
    }
}