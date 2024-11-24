package com.example.mushroomhunting.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.adapter.TripAdapter;
import com.example.mushroomhunting.db.DatabaseManager;
import com.example.mushroomhunting.dto.TripDto;
import com.example.mushroomhunting.util.TripDiffCallback;

import java.util.ArrayList;
import java.util.List;

public class ViewTripsActivity extends AppCompatActivity {

    private RecyclerView tripsRecyclerView;
    private TripAdapter tripAdapter;
    private List<TripDto> tripList;  // Original list of trips
    private List<TripDto> filteredTrips;  // List for filtered trips
    private DatabaseManager repository = new DatabaseManager();

    private CheckBox selectAllCheckbox;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trips);

        tripsRecyclerView = findViewById(R.id.tripsRecyclerView);
        tripsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        selectAllCheckbox = findViewById(R.id.select_all_checkbox);
        deleteButton = findViewById(R.id.delete_button);

        // Load trips from the repository
        tripList = repository.getAllTrips();
        filteredTrips = new ArrayList<>(tripList);

        // Initialize adapter
        tripAdapter = new TripAdapter(this, filteredTrips, this::updateSelectAllCheckbox);
        tripsRecyclerView.setAdapter(tripAdapter);

        // Set up Select All checkbox
        setupSelectAllCheckbox();

        // Set up Delete button
        setupDeleteButton();

        // Setup SearchView
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnClickListener(v -> searchView.setIconified(false));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterTrips(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTrips(newText);
                return false;
            }
        });
    }

    // Method to filter trips based on search query
    private void filterTrips(String query) {
        List<TripDto> newFilteredTrips = new ArrayList<>();
        if (query.isEmpty()) {
            newFilteredTrips.addAll(tripList);
        } else {
            for (TripDto trip : tripList) {
                if (trip.getTripName().toLowerCase().contains(query.toLowerCase()) ||
                        trip.getTripLocation().toLowerCase().contains(query.toLowerCase())) {
                    newFilteredTrips.add(trip);
                }
            }
        }

        // Use DiffUtil to calculate changes
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TripDiffCallback(filteredTrips, newFilteredTrips));

        filteredTrips.clear();
        filteredTrips.addAll(newFilteredTrips);
        diffResult.dispatchUpdatesTo(tripAdapter);
    }

    // Set up Select All checkbox functionality
    private void setupSelectAllCheckbox() {
        selectAllCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Pass the select all state to the adapter
            tripAdapter.selectAllTrips(isChecked);
        });
    }

    // Set up Delete button functionality
    private void setupDeleteButton() {
        deleteButton.setOnClickListener(v -> {
            List<TripDto> tripsToDelete = new ArrayList<>();

            // Collect all selected trips
            for (TripDto trip : filteredTrips) {
                if (trip.isSelected()) {
                    tripsToDelete.add(trip);
                }
            }

            // Remove selected trips
            filteredTrips.removeAll(tripsToDelete);
            tripList.removeAll(tripsToDelete);
            tripAdapter.notifyDataSetChanged();

            // Update repository (or database)
            for (TripDto trip : tripsToDelete) {
                repository.deleteTrip(trip.getTripId());
            }

            // Uncheck Select All checkbox after deletion
            selectAllCheckbox.setChecked(false);
        });
    }

    // Update Select All checkbox state based on individual trip selections
    private void updateSelectAllCheckbox() {
        boolean allSelected = true;
        for (TripDto trip : filteredTrips) {
            if (!trip.isSelected()) {
                allSelected = false;
                break;
            }
        }
        selectAllCheckbox.setChecked(allSelected);
    }
}
