package com.example.mushroomhunting.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mushroomhunting.R;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        // Initialize WebView to display the Privacy Policy
        WebView privacyWebView = findViewById(R.id.privacyWebView);

        // Load the Privacy Policy from a local HTML file
        privacyWebView.loadUrl("file:///android_res/raw/privacy_policy.html");

        // Initialize the checkbox and button
        CheckBox privacyCheckbox = findViewById(R.id.privacyCheckbox);
        Button acceptButton = findViewById(R.id.acceptButton);

        // Set click listener for the "Accept" button
        acceptButton.setOnClickListener(v -> {
            if (privacyCheckbox.isChecked()) {
                // Show a toast message if the policy is accepted
                Toast.makeText(this, "Privacy Policy accepted", Toast.LENGTH_SHORT).show();
            } else {
                // Show a toast message if the checkbox is not checked
                Toast.makeText(this, "Please accept the Privacy Policy", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
