package com.example.mushroomhunting.activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mushroomhunting.R;
import java.util.Calendar;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {

    private EditText nameInput, dateInput, timeInput, locationInput, durationInput, descriptionInput, speciesInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        nameInput = findViewById(R.id.trip_name_input);
        dateInput = findViewById(R.id.date_input);
        timeInput = findViewById(R.id.time_input);
        locationInput = findViewById(R.id.location_input);
        durationInput = findViewById(R.id.duration_input);
        descriptionInput = findViewById(R.id.description_input);
        speciesInput = findViewById(R.id.mushroom_species_input);

        // Set onClickListener for name input
        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripName = nameInput.getText().toString();
            }
        });

        // Set onClickListener for Date Picker
        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Set onClickListener for Time Picker
        timeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    dateInput.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        // Handle the selected time here
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);

                        // update an EditText with the selected time
                        EditText timeInput = findViewById(R.id.time_input);  // Replace with your actual input field
                        timeInput.setText(selectedTime);
                    }
                },
                hour, minute, true // Set to true for 24-hour format, false for AM/PM format
        );

        // Show the time picker dialog
        timePickerDialog.show();
    }}