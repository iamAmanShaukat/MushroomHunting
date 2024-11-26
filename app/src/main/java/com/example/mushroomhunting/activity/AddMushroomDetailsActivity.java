package com.example.mushroomhunting.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.db.DatabaseManager;
import com.example.mushroomhunting.dto.MushroomDto;
import com.example.mushroomhunting.dto.TripDto;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddMushroomDetailsActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private Spinner tripSpinner;
    private EditText mushroomTypeInput, mushroomLocationInput, mushroomQuantityInput, mushroomCommentsInput;
    private Button saveMushroomDetailsButton, currentLocationButton;
    private DatabaseManager databaseManager;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mushroom_details);

        // Initialize DatabaseManager and FusedLocationProviderClient
        databaseManager = new DatabaseManager();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize UI elements
        tripSpinner = findViewById(R.id.trip_spinner);
        mushroomTypeInput = findViewById(R.id.mushroom_type_input);
        mushroomLocationInput = findViewById(R.id.mushroom_location_input);
        mushroomQuantityInput = findViewById(R.id.mushroom_quantity_input);
        mushroomCommentsInput = findViewById(R.id.additional_comments_input);
        saveMushroomDetailsButton = findViewById(R.id.save_mushroom_details);
        currentLocationButton = findViewById(R.id.curr_location_btn);

        // Load trips into Spinner
        loadTripsIntoSpinner();

        // Set up button listeners
        saveMushroomDetailsButton.setOnClickListener(v -> saveMushroomDetails());
        currentLocationButton.setOnClickListener(v -> fetchCurrentLocation());
    }

    private void loadTripsIntoSpinner() {
        List<TripDto> tripsList = databaseManager.getAllTrips();

        if (tripsList.isEmpty()) {
            Toast.makeText(this, "No trips available. Please create a trip first.", Toast.LENGTH_LONG).show();
            finish(); // Close the activity if no trips are available
            return;
        }

        List<String> tripNames = new ArrayList<>();
        for (TripDto trip : tripsList) {
            tripNames.add(trip.getTripName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tripNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tripSpinner.setAdapter(adapter);
    }

    private void fetchCurrentLocation() {
        // Check location permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // Create location request
        LocationRequest locationRequest = LocationRequest.create();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationRequest.setInterval(10000); // Update every 10 seconds

        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null || locationResult.getLastLocation() == null) {
                    Toast.makeText(AddMushroomDetailsActivity.this, "Failed to get location. Try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get location and reverse-geocode it to get an address
                double latitude = locationResult.getLastLocation().getLatitude();
                double longitude = locationResult.getLastLocation().getLongitude();

                try {
                    Geocoder geocoder = new Geocoder(AddMushroomDetailsActivity.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        mushroomLocationInput.setText(addresses.get(0).getAddressLine(0));
                    } else {
                        Toast.makeText(AddMushroomDetailsActivity.this, "Unable to fetch address. Try again.", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(AddMushroomDetailsActivity.this, "Geocoder error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied. Cannot fetch location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveMushroomDetails() {
        int selectedPosition = tripSpinner.getSelectedItemPosition();
        if (selectedPosition == Spinner.INVALID_POSITION) {
            Toast.makeText(this, "Please select a trip first.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<TripDto> tripsList = databaseManager.getAllTrips();
        TripDto selectedTrip = tripsList.get(selectedPosition);

        String mushroomType = mushroomTypeInput.getText().toString().trim();
        String mushroomLocation = mushroomLocationInput.getText().toString().trim();
        String mushroomQuantity = mushroomQuantityInput.getText().toString().trim();
        String mushroomComments = mushroomCommentsInput.getText().toString().trim();

        if (mushroomType.isEmpty() || mushroomLocation.isEmpty() || mushroomQuantity.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        MushroomDto mushroom = new MushroomDto();
        mushroom.setMushroomType(mushroomType);
        mushroom.setMushroomLocation(mushroomLocation);
        mushroom.setMushroomQuantity(mushroomQuantity);
        mushroom.setComments(mushroomComments);
        mushroom.setTripId(selectedTrip.getTripId());

        boolean isSaved = databaseManager.addMushroom(mushroom);
        if (isSaved) {
            Toast.makeText(this, "Mushroom details saved successfully.", Toast.LENGTH_SHORT).show();
            clearInputFields();
        } else {
            Toast.makeText(this, "Failed to save mushroom details.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputFields() {
        mushroomTypeInput.setText("");
        mushroomLocationInput.setText("");
        mushroomQuantityInput.setText("");
        mushroomCommentsInput.setText("");
        tripSpinner.setSelection(0);
    }
}
