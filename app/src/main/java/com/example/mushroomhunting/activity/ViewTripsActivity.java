    package com.example.mushroomhunting.activity;

    import android.os.Bundle;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.SearchView;
    import androidx.recyclerview.widget.DiffUtil;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import com.example.mushroomhunting.R;
    import com.example.mushroomhunting.adapter.TripAdapter;
    import com.example.mushroomhunting.db.DatabaseOperation;
    import com.example.mushroomhunting.dto.TripDto;
    import com.example.mushroomhunting.util.TripDiffCallback;

    import java.util.ArrayList;
    import java.util.List;

    public class ViewTripsActivity extends AppCompatActivity {

        private RecyclerView tripsRecyclerView;
        private TripAdapter tripAdapter;
        private List<TripDto> tripList;  // Original list of trips
        private List<TripDto> filteredTrips;  // List for filtered trips
        DatabaseOperation repository = new DatabaseOperation();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_trips);

            tripsRecyclerView = findViewById(R.id.tripsRecyclerView);
            tripsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            tripList = repository.getAllTrips();
            filteredTrips = new ArrayList<>(tripList);

            tripAdapter = new TripAdapter(this,filteredTrips);
            tripsRecyclerView.setAdapter(tripAdapter);

            // Setup SearchView
            SearchView searchView = findViewById(R.id.search_view);
            // Expands the search bar on click, to make the whole bar clickable
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

        // Method to filter trips
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

//        To get dummy data
//        private List<TripDto> getTrips() {
//            List<TripDto> trips = new ArrayList<>();
//
//            for (int i = 1; i <= 50; i++) {
//                TripDto trip = new TripDto();
//                trip.setTripName("Trip " + i);
//                trip.setTripDate("2024-01-" + (i < 10 ? "0" + i : i)); // Generates dates like 2024-01-01, 2024-01-02, etc.
//                trip.setTripTime(i % 2 == 0 ? "10:00 AM" : "9:00 AM"); // Alternates between two different times
//                trip.setTripLocation("Location " + i); // Unique location for each trip
//                trip.setTripDuration((2 + i % 5) + " hours"); // Generates durations like "3 hours", "4 hours", etc.
//                trip.setTripDescription(i % 2 == 0 ? "Exploring mountains" : "Hiking through the woods"); // Alternates descriptions
//
//                trips.add(trip);
//            }
//            return trips;
//        }
    }