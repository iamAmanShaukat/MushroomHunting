package com.example.mushroomhunting.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.db.DatabaseManager;
import com.example.mushroomhunting.dto.TripDto;
import com.example.mushroomhunting.validate.Validation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private EditText nameInput, dateInput, timeInput, locationInput, durationInput, descriptionInput;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        // Initialize UI components
        nameInput = findViewById(R.id.trip_name_input);
        dateInput = findViewById(R.id.date_input);
        timeInput = findViewById(R.id.time_input);
        locationInput = findViewById(R.id.location_input);
        durationInput = findViewById(R.id.duration_input);
        descriptionInput = findViewById(R.id.description_input);
        Button saveTripDetailsButton = findViewById(R.id.saveTripButton);
        Button useCurrentLocationButton = findViewById(R.id.useCurrentLocationButton);

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Set onClickListener for Date Picker
        dateInput.setOnClickListener(v -> showDatePicker());

        // Set onClickListener for Time Picker
        timeInput.setOnClickListener(v -> showTimePicker());

        // Set onClickListener for Save button
        saveTripDetailsButton.setOnClickListener(v -> saveTripDetails());

        // Set onClickListener for Current Location button
        useCurrentLocationButton.setOnClickListener(v -> fetchCurrentLocation());
    }

    private void saveTripDetails() {
        TripDto tripDto = new TripDto();

        tripDto.setTripName(nameInput.getText().toString());
        tripDto.setTripDate(dateInput.getText().toString());
        tripDto.setTripTime(timeInput.getText().toString());
        tripDto.setTripLocation(locationInput.getText().toString());
        tripDto.setTripDuration(durationInput.getText().toString());
        tripDto.setTripDescription(descriptionInput.getText().toString());

        // Validate required fields
        Validation validation = new Validation();
        String toastMessage = validation.validateTripDetails(tripDto);
        if (!TextUtils.isEmpty(toastMessage)) {
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        // Show the confirmation dialog
        showConfirmationDialog(tripDto);
    }

    private void showConfirmationDialog(TripDto tripDto) {
        DatabaseManager repository = new DatabaseManager();
        // Prepare the dialog message
        String message = tripDto.toString();

        // Create the dialog
        new AlertDialog.Builder(this)
                .setTitle("Confirm Trip Details")
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Save", (dialog, which) -> {
                    // Save the details and show a success message
                    repository.saveTrip(tripDto);
                    Toast.makeText(AddTripActivity.this, "Trip details saved successfully!", Toast.LENGTH_SHORT).show();
                    clearForm();
                })
                .setNegativeButton("Edit", (dialog, which) -> {
                    // Close the dialog and allow user to edit
                    dialog.dismiss();
                })
                .show();
    }

    private void clearForm() {
        nameInput.setText("");
        dateInput.setText("");
        timeInput.setText("");
        locationInput.setText("");
        durationInput.setText("");
        descriptionInput.setText("");
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    dateInput.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, selectedHour, selectedMinute) -> {
                    String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                    timeInput.setText(selectedTime);
                },
                hour, minute, true
        );

        timePickerDialog.show();
    }

    private void fetchCurrentLocation() {
        // Check for location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permissions if not already granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Request fresh location updates
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(1000); // 1 second
            locationRequest.setFastestInterval(500); // 0.5 seconds

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    fusedLocationProviderClient.removeLocationUpdates(this); // Stop updates once location is received

                    if (locationResult != null && locationResult.getLocations().size() > 0) {
                        Location location = locationResult.getLastLocation();
                        getAddressFromLocation(location); // Use reverse geocoding to fetch the address
                    } else {
                        Toast.makeText(AddTripActivity.this, "Unable to fetch location. Try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, getMainLooper());
        }
    }

    private void getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressString = address.getAddressLine(0);
                locationInput.setText(addressString);
            } else {
                Toast.makeText(this, "Unable to fetch address. Try again.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Geocoder failed. Check internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
