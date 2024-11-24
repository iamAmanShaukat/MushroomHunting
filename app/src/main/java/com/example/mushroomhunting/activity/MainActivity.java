package com.example.mushroomhunting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import com.example.mushroomhunting.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_MushroomHunting);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize layout containers
        ConstraintLayout addTripLayout = findViewById(R.id.addTripLayout);
        ConstraintLayout viewTripsLayout = findViewById(R.id.viewTripsLayout);
        ConstraintLayout addMushroomDetailsLayout = findViewById(R.id.addMushroomDetailsLayout);

        // Set up the Recent Activity Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recentFragmentContainer, new RecentActivityFragment())
                .commit();

        // Set onClick listeners for each layout
        addTripLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Add New Trip Activity
                Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });

        viewTripsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens the View Trips Activity
                Intent intent = new Intent(MainActivity.this, ViewTripsActivity.class);
                startActivity(intent);
            }
        });

        addMushroomDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Add Mushroom Details Activity
                Intent intent = new Intent(MainActivity.this, AddMushroomDetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the toolbar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle toolbar menu item clicks
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // Open the Settings Activity
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

}
}

