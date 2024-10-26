package com.example.mushroomhunting.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.dto.MushroomDto;
import com.example.mushroomhunting.util.LocationUtils;
import com.example.mushroomhunting.validate.Validation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class AddMushroomDetailsActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private EditText mushroomTypeInput;
    private EditText mushroomLocationInput;
    private EditText mushroomQuantityInput;
    private EditText additionalCommentsInput;
    private Button saveMushroomDetails;
    private FusedLocationProviderClient fusedLocationClient;
    private Button currLocationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mushroom_details);


        // Initialize the input fields and button
        mushroomTypeInput = findViewById(R.id.mushroom_type_input);
        mushroomLocationInput = findViewById(R.id.mushroom_location_input);
        currLocationBtn = findViewById(R.id.curr_location_btn);
        mushroomQuantityInput = findViewById(R.id.mushroom_quantity_input);
        additionalCommentsInput = findViewById(R.id.additional_comments_input);
        saveMushroomDetails = findViewById(R.id.save_mushroom_details);

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Set up the button to get current location
        currLocationBtn.setOnClickListener(v ->
                LocationUtils.fetchCurrentLocation(this, new LocationUtils.LocationCallback() {
                    @Override
                    public void onLocationFetched(String locationName) {
                        mushroomLocationInput.setText(locationName);
                    }
                })
        );

        // Set onClickListener for the save button
        saveMushroomDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMushroomDetails();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocationUtils.handlePermissionResult(requestCode, grantResults, () ->
                LocationUtils.fetchCurrentLocation(this, locationName ->
                        mushroomLocationInput.setText(locationName))
        );
    }



    private void saveMushroomDetails() {
        MushroomDto mushroomDto = new MushroomDto();

        mushroomDto.setMushroomType(mushroomTypeInput.getText().toString().trim());
        mushroomDto.setMushroomLocation(mushroomLocationInput.getText().toString().trim());
        mushroomDto.setMushroomQuantity(mushroomQuantityInput.getText().toString().trim());
        mushroomDto.setComments(additionalCommentsInput.getText().toString().trim());

        // Validate required fields
        Validation validation = new Validation();
        String toastMessage = validation.validateMushroomDetails(mushroomDto);
        if (!TextUtils.isEmpty(toastMessage)) {
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        // Show the confirmation dialog
        showConfirmationDialog(mushroomDto);
    }

    private void showConfirmationDialog(MushroomDto mushroomDto) {
        // Prepare the dialog message
        String message = mushroomDto.toString();

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