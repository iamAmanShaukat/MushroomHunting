package com.example.mushroomhunting.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import com.example.mushroomhunting.R;

public class AddMushroomDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mushroom_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            return insets;
        });
    }
}