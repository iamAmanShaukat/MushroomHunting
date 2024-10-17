package com.example.mushroomhunting.activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mushroomhunting.R;
import com.example.mushroomhunting.dto.TripDto;
import com.example.mushroomhunting.validate.Validation;

import java.util.Calendar;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {

    private EditText nameInput, dateInput, timeInput, locationInput, durationInput, descriptionInput;


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
        Button addMushroomDetailsButton = (Button)findViewById(R.id.addMushroomButton);
        Button saveTripDetailsButton = (Button)findViewById(R.id.saveTripButton);



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

        // set onClickListener for addMushroom button
        addMushroomDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Add Mushroom Details Activity
                Intent intent = new Intent(AddTripActivity.this, AddMushroomDetailsActivity.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for the save button
        saveTripDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTripDetails();
            }
        });
    }

    private void saveTripDetails(){
        TripDto tripDto = new TripDto();

        tripDto.setTripName(nameInput.getText().toString());
        tripDto.setTripDate(dateInput.getText().toString());
        tripDto.setTripTime(timeInput.getText().toString());
        tripDto.setTripLocation(locationInput.getText().toString());
        tripDto.setTripDuration(durationInput.getText().toString());
        tripDto.setTripDescription(descriptionInput.getText().toString());

        // Validate required fields
        Validation validation = new Validation();
        String toastMessage = validation.validateTripDetails(tripDto);
        if(!TextUtils.isEmpty(toastMessage)){
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        // Show the confirmation dialog
        showConfirmationDialog(tripDto);
    }

    private void showConfirmationDialog(TripDto tripDto) {
        // Prepare the dialog message
        String message = tripDto.toString();

        // Create the dialog
        new AlertDialog.Builder(this)
                .setTitle("Confirm Trip Details")
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Save the details and show a success message
                        Toast.makeText(AddTripActivity.this, "Trip details saved successfully!", Toast.LENGTH_SHORT).show();
                        clearForm();
                    }
                })
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the dialog and allow user to edit
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //Clear form after submission
    private void clearForm() {
        nameInput.setText("");
        dateInput.setText("");
        timeInput.setText("");
        locationInput.setText("");
        durationInput.setText("");
        descriptionInput.setText("");
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