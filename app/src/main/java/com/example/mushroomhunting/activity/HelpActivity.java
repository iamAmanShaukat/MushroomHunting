package com.example.mushroomhunting.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mushroomhunting.R;

public class HelpActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_help); // Ensure this matches your XML layout file name

            TextView question1 = findViewById(R.id.helpQuestion1);
            TextView answer1 = findViewById(R.id.helpAnswer1);
            TextView question2 = findViewById(R.id.helpQuestion2);
            TextView answer2 = findViewById(R.id.helpAnswer2);
            TextView question3 = findViewById(R.id.helpQuestion3);
            TextView question4 = findViewById(R.id.helpQuestion4);
            TextView question5 = findViewById(R.id.helpQuestion5);
            TextView answer5 = findViewById(R.id.helpAnswer5);

            // Initially hide answers
            answer1.setVisibility(View.GONE);
            answer2.setVisibility(View.GONE);
            answer5.setVisibility(View.GONE);

            // Toggle visibility for Question 1
            question1.setOnClickListener(v -> {
                if (answer1.getVisibility() == View.VISIBLE) {
                    answer1.setVisibility(View.GONE);
                } else {
                    answer1.setVisibility(View.VISIBLE);
                }
            });

            // Toggle visibility for Question 2
            question2.setOnClickListener(v -> {
                if (answer2.getVisibility() == View.VISIBLE) {
                    answer2.setVisibility(View.GONE);
                } else {
                    answer2.setVisibility(View.VISIBLE);
                }
            });

            // Toggle visibility for Question 5
            question5.setOnClickListener(v -> {
                if (answer5.getVisibility() == View.VISIBLE) {
                    answer5.setVisibility(View.GONE);
                } else {
                    answer5.setVisibility(View.VISIBLE);
                }
            });

            question3.setOnClickListener(v -> {
            });

            question4.setOnClickListener(v -> {
            });
        }
    }
