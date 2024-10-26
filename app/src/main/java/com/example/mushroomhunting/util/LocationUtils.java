package com.example.mushroomhunting.util;

import static com.example.mushroomhunting.util.AppContextUtil.getAppContext;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Address;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtils {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    public interface LocationCallback {
        void onLocationFetched(String locationName);
    }

    public static void fetchCurrentLocation(Activity activity, LocationCallback callback) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        // Check if location permissions are granted
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Request location permissions
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // Get the last known location
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, location -> {
                    if (location != null) {
                        // Use Geocoder to get the city name
                        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (addresses != null && !addresses.isEmpty()) {
                                String cityName = addresses.get(0).getLocality();
                                callback.onLocationFetched(cityName);
                            } else {
                                callback.onLocationFetched("Location not available");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, "Unable to fetch city name", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity, "Unable to retrieve location. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void handlePermissionResult(int requestCode, @NonNull int[] grantResults, Runnable onPermissionGranted) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, run the callback
                onPermissionGranted.run();
            } else {
                Toast.makeText(getAppContext(), "Location permission denied.", Toast.LENGTH_SHORT).show(); // Use activity here
//                Toast.makeText(onPermissionGranted, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}