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

public class RecentActivityFragment extends Fragment {

    private TextView recentActivity1Name;
    private TextView recentActivity1Date;
    private TextView recentActivity2Name;
    private TextView recentActivity2Date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_activity, container, false);

//        // Initialize TextViews
//        recentActivity1Name = view.findViewById(R.id.trip1Name);
//        recentActivity1Date = view.findViewById(R.id.trip1Location);
//        recentActivity2Name = view.findViewById(R.id.trip2Name);
//        recentActivity2Date = view.findViewById(R.id.trip2Location);


        // Example recent activity 1
        ConstraintLayout recentTrip1 = view.findViewById(R.id.recentTrip1);
        recentTrip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTripDetail("Trip 1", "This is full detail for trip 1.");
            }
        });

        // Example recent activity 2
        ConstraintLayout recentTrip2 = view.findViewById(R.id.recentTrip2);
        recentTrip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTripDetail("Trip 2", "This is full detail for trip 2.");
            }
        });

        return view;
    }

    // Method to open the TripDetailActivity and pass data
    private void openTripDetail(String tripName, String tripDetails) {
        Intent intent = new Intent(getActivity(), TripDetailsActivity.class);
        intent.putExtra("trip_name", tripName);
        intent.putExtra("trip_details", tripDetails);
        startActivity(intent);
    }
}