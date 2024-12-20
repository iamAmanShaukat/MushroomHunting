package com.example.mushroomhunting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.db.CacheHelper;
import com.example.mushroomhunting.dto.TripDto;

import java.util.List;

public class RecentActivityFragment extends Fragment {

    private TextView recentActivity1Name;
    private TextView recentActivity1Date;
    private TextView recentActivity1Location;
    private TextView recentActivity2Name;
    private TextView recentActivity2Date;
    private TextView recentActivity2Location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_activity, container, false);

        // Initialize TextViews
        recentActivity1Name = view.findViewById(R.id.trip1Name);
        recentActivity1Date = view.findViewById(R.id.trip1Date);
        recentActivity1Location = view.findViewById(R.id.trip1Location);
        recentActivity2Name = view.findViewById(R.id.trip2Name);
        recentActivity2Date = view.findViewById(R.id.trip2Date);
        recentActivity2Location = view.findViewById(R.id.trip2Location);

        // Set up click listeners
        ConstraintLayout recentTrip1 = view.findViewById(R.id.recentTrip1);
        ConstraintLayout recentTrip2 = view.findViewById(R.id.recentTrip2);

        recentTrip1.setOnClickListener(v -> openTripDetail(
                "1"
        ));

        recentTrip2.setOnClickListener(v -> openTripDetail(
                "2"
        ));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload recent trips
        loadRecentTrips();
    }

    private void loadRecentTrips() {
        List<TripDto> lastTwoTrips = CacheHelper.getLastTwoTrips(requireContext());
        if (lastTwoTrips.size() > 0) {
            TripDto trip1 = lastTwoTrips.get(0);
            recentActivity1Name.setText(trip1.getTripName());
            recentActivity1Date.setText(trip1.getTripDate());
            recentActivity1Location.setText(trimLocation(trip1.getTripLocation()));

            if (lastTwoTrips.size() > 1) {
                TripDto trip2 = lastTwoTrips.get(1);
                recentActivity2Name.setText(trip2.getTripName());
                recentActivity2Date.setText(trip2.getTripDate());
                recentActivity2Location.setText(trimLocation(trip2.getTripLocation()));
            }
        }
    }

    private void openTripDetail(String tripName) {
        Intent intent = new Intent(getActivity(), TripDetailsActivity.class);
        String tripId;
        List<TripDto> lastTwoTrips = CacheHelper.getLastTwoTrips(requireContext());
        if (lastTwoTrips.size() > 0 && tripName.equals("1")) {
            TripDto trip1 = lastTwoTrips.get(0);
            tripId = trip1.getTripId();
        } else if (lastTwoTrips.size() > 1 && tripName.equals("2")) {
            TripDto trip2 = lastTwoTrips.get(1);
            tripId = trip2.getTripId();
        } else {
            return;
        }
        intent.putExtra("tripId", tripId);
        startActivity(intent);
    }

    private String trimLocation(String location) {
        return location.length() > 20 ? location.substring(0, 20) + "..." : location;
    }
}