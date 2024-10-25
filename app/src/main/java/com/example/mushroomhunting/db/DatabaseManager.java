package com.example.mushroomhunting.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mushroomhunting.constant.AppConstants;
import com.example.mushroomhunting.dto.MushroomDto;
import com.example.mushroomhunting.dto.TripDto;
import com.example.mushroomhunting.util.MushroomHunting;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private DatabaseHelper dbHelper;

    public DatabaseManager() {
        dbHelper = new DatabaseHelper(MushroomHunting.getAppContext());
    }

    // Method to save a Trip
    public void saveTrip(TripDto tripDto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AppConstants.COLUMN_TRIP_ID, tripDto.getTripId());
        values.put(AppConstants.COLUMN_TRIP_NAME, tripDto.getTripName());
        values.put(AppConstants.COLUMN_TRIP_DATE, tripDto.getTripDate());
        values.put(AppConstants.COLUMN_TRIP_TIME, tripDto.getTripTime());
        values.put(AppConstants.COLUMN_TRIP_LOCATION, tripDto.getTripLocation());
        values.put(AppConstants.COLUMN_TRIP_DURATION, tripDto.getTripDuration());
        values.put(AppConstants.COLUMN_TRIP_DESCRIPTION, tripDto.getTripDescription());

        long result = db.insert(AppConstants.TABLE_TRIP, null, values);
        if (result == -1) {
            Log.e("Database", "Failed to insert trip");
        } else {
            Log.i("Database", "When saving - Trip inserted with ID: " + tripDto.getTripId()+ " \n"+tripDto);
        }
        db.close();
    }

    // Method to save a Mushroom
    public void saveMushroom(MushroomDto mushroomDto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AppConstants.COLUMN_MUSHROOM_ID, mushroomDto.getMushroomId());
        values.put(AppConstants.COLUMN_MUSHROOM_TYPE, mushroomDto.getMushroomType());
        values.put(AppConstants.COLUMN_MUSHROOM_LOCATION, mushroomDto.getMushroomLocation());
        values.put(AppConstants.COLUMN_MUSHROOM_QUANTITY, mushroomDto.getMushroomQuantity());
        values.put(AppConstants.COLUMN_MUSHROOM_COMMENTS, mushroomDto.getComments());
        values.put(AppConstants.COLUMN_MUSHROOM_TRIP_ID, mushroomDto.getTripId());

        db.insert(AppConstants.TABLE_MUSHROOM, null, values);
        db.close();
    }


    // Method to get trip details along with associated mushrooms
    public TripDto getTripDetails(String tripId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        TripDto tripDto = null;

        // First, get the trip details
        Cursor tripCursor = db.query(AppConstants.TABLE_TRIP, null,
                AppConstants.COLUMN_TRIP_ID + "=?", new String[]{tripId},
                null, null, null);

        if (tripCursor != null && tripCursor.moveToFirst()) {
            tripDto = new TripDto();
            tripDto.setTripId(tripCursor.getString(tripCursor.getColumnIndex(AppConstants.COLUMN_TRIP_ID)));
            tripDto.setTripName(tripCursor.getString(tripCursor.getColumnIndex(AppConstants.COLUMN_TRIP_NAME)));
            tripDto.setTripDate(tripCursor.getString(tripCursor.getColumnIndex(AppConstants.COLUMN_TRIP_DATE)));
            tripDto.setTripTime(tripCursor.getString(tripCursor.getColumnIndex(AppConstants.COLUMN_TRIP_TIME)));
            tripDto.setTripLocation(tripCursor.getString(tripCursor.getColumnIndex(AppConstants.COLUMN_TRIP_LOCATION)));
            tripDto.setTripDuration(tripCursor.getString(tripCursor.getColumnIndex(AppConstants.COLUMN_TRIP_DURATION)));
            tripDto.setTripDescription(tripCursor.getString(tripCursor.getColumnIndex(AppConstants.COLUMN_TRIP_DESCRIPTION)));
        }

        // Then, get the associated mushrooms
        if (tripDto != null) {
            List<MushroomDto> mushroomList = new ArrayList<>();
            Cursor mushroomCursor = db.query(AppConstants.TABLE_MUSHROOM, null,
                    AppConstants.COLUMN_MUSHROOM_TRIP_ID + "=?", new String[]{tripId},
                    null, null, null);

            if (mushroomCursor != null && mushroomCursor.moveToFirst()) {
                do {
                    MushroomDto mushroomDto = new MushroomDto();
                    mushroomDto.setMushroomId(mushroomCursor.getString(mushroomCursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_ID)));
                    mushroomDto.setMushroomType(mushroomCursor.getString(mushroomCursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_TYPE)));
                    mushroomDto.setMushroomLocation(mushroomCursor.getString(mushroomCursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_LOCATION)));
                    mushroomDto.setMushroomQuantity(mushroomCursor.getString(mushroomCursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_QUANTITY)));
                    mushroomDto.setComments(mushroomCursor.getString(mushroomCursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_COMMENTS)));
                    mushroomDto.setTripId(mushroomCursor.getString(mushroomCursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_TRIP_ID)));

                    mushroomList.add(mushroomDto);
                } while (mushroomCursor.moveToNext());
            }

            tripDto.setMushroomList(mushroomList); // Assuming you have a setter for mushroomList in TripDto
        }

        if (tripCursor != null) {
            tripCursor.close();
        }
        db.close();
        return tripDto;
    }

    // Method to get List of all the trips
    public List<TripDto> getAllTrips() {
        List<TripDto> trips = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(AppConstants.TABLE_TRIP, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                TripDto tripDto = new TripDto();
                tripDto.setTripId(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_ID)));
                tripDto.setTripName(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_NAME)));
                tripDto.setTripDate(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_DATE)));
                tripDto.setTripTime(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_TIME)));
                tripDto.setTripLocation(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_LOCATION)));
                tripDto.setTripDuration(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_DURATION)));
                tripDto.setTripDescription(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_DESCRIPTION)));

                trips.add(tripDto);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return trips;
    }
}