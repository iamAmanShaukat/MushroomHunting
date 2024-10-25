package com.example.mushroomhunting.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mushroomhunting.R;
import com.example.mushroomhunting.db.DatabaseOperation;
import com.example.mushroomhunting.dto.TripDto;

public class TripDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        // Get the trip details from the intent
        String tripId = getIntent().getStringExtra("tripId");

        DatabaseOperation repository = new DatabaseOperation();
        TripDto tripDto = repository.getTripDetails(tripId);
        Log.i("TripDetails", tripId);

        // Set the trip details in TextViews

        ((TextView) findViewById(R.id.trip_name)).setText(tripDto.getTripName());
        ((TextView) findViewById(R.id.trip_date)).setText(tripDto.getTripDate());
        ((TextView) findViewById(R.id.trip_time)).setText(tripDto.getTripTime());
        ((TextView) findViewById(R.id.trip_location)).setText(tripDto.getTripLocation());
        ((TextView) findViewById(R.id.trip_duration)).setText(tripDto.getTripDuration());
        ((TextView) findViewById(R.id.trip_description)).setText(tripDto.getTripDescription());
    }
}