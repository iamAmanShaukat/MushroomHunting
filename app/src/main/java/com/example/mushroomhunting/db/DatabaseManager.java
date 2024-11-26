package com.example.mushroomhunting.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.mushroomhunting.constant.AppConstants;
import com.example.mushroomhunting.dto.MushroomDto;
import com.example.mushroomhunting.dto.TripDto;
import com.example.mushroomhunting.util.AppContextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {
    private static final String TAG = "DatabaseManager";
    private final DatabaseHelper dbHelper;

    public DatabaseManager() {
        dbHelper = new DatabaseHelper(AppContextUtil.getAppContext());
    }

    /**
     * Saves a trip to the database.
     *
     * @param tripDto The trip to save.
     * @return true if save was successful, false otherwise.
     */
    public boolean saveTrip(TripDto tripDto) {
        if (tripDto == null) {
            Log.e(TAG, "Cannot save null trip");
            return false;
        }

        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            // Generate UUID if tripId is null
            if (tripDto.getTripId() == null || tripDto.getTripId().trim().isEmpty()) {
                tripDto.setTripId(UUID.randomUUID().toString());
            }

            values.put(AppConstants.COLUMN_TRIP_ID, tripDto.getTripId());
            values.put(AppConstants.COLUMN_TRIP_NAME, tripDto.getTripName());
            values.put(AppConstants.COLUMN_TRIP_DATE, tripDto.getTripDate());
            values.put(AppConstants.COLUMN_TRIP_TIME, tripDto.getTripTime());
            values.put(AppConstants.COLUMN_TRIP_LOCATION, tripDto.getTripLocation());
            values.put(AppConstants.COLUMN_TRIP_DURATION, tripDto.getTripDuration());
            values.put(AppConstants.COLUMN_TRIP_DESCRIPTION, tripDto.getTripDescription());

            long result = db.insertWithOnConflict(
                    AppConstants.TABLE_TRIP,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
            );

            if (result == -1) {
                Log.e(TAG, "Failed to insert trip: " + tripDto.getTripId());
                return false;
            }

            Log.i(TAG, "Successfully saved trip: " + tripDto.getTripId());
            return true;

        } catch (SQLiteException e) {
            Log.e(TAG, "Database error when saving trip: " + e.getMessage());
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    /**
     * Adds a mushroom to the database and links it to a trip.
     *
     * @param mushroomDto The mushroom to add.
     * @return true if addition was successful, false otherwise.
     */
    public boolean addMushroom(MushroomDto mushroomDto) {
        if (mushroomDto == null) {
            Log.e(TAG, "Cannot add null mushroom");
            return false;
        }

        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            // Generate UUID if mushroomId is null
            if (mushroomDto.getMushroomId() == null || mushroomDto.getMushroomId().trim().isEmpty()) {
                mushroomDto.setMushroomId(UUID.randomUUID().toString());
            }

            values.put(AppConstants.COLUMN_MUSHROOM_ID, mushroomDto.getMushroomId());
            values.put(AppConstants.COLUMN_MUSHROOM_TYPE, mushroomDto.getMushroomType());
            values.put(AppConstants.COLUMN_MUSHROOM_LOCATION, mushroomDto.getMushroomLocation());
            values.put(AppConstants.COLUMN_MUSHROOM_QUANTITY, mushroomDto.getMushroomQuantity());
            values.put(AppConstants.COLUMN_MUSHROOM_COMMENTS, mushroomDto.getComments());
            values.put(AppConstants.COLUMN_MUSHROOM_TRIP_ID, mushroomDto.getTripId());

            long result = db.insertWithOnConflict(
                    AppConstants.TABLE_MUSHROOM,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
            );

            if (result == -1) {
                Log.e(TAG, "Failed to add mushroom: " + mushroomDto.getMushroomId());
                return false;
            }

            Log.i(TAG, "Successfully added mushroom: " + mushroomDto.getMushroomId());
            return true;

        } catch (SQLiteException e) {
            Log.e(TAG, "Database error when adding mushroom: " + e.getMessage());
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    /**
     * Deletes a mushroom by its ID.
     *
     * @param mushroomId The ID of the mushroom to delete.
     */
    public void deleteMushroom(String mushroomId) {
        if (mushroomId == null || mushroomId.trim().isEmpty()) {
            Log.e(TAG, "Invalid mushroom ID provided for deletion");
            return;
        }

        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            int rowsDeleted = db.delete(
                    AppConstants.TABLE_MUSHROOM,
                    AppConstants.COLUMN_MUSHROOM_ID + "=?",
                    new String[]{mushroomId}
            );

            if (rowsDeleted > 0) {
                Log.i(TAG, "Deleted mushroom with ID: " + mushroomId);
            } else {
                Log.w(TAG, "No mushroom found with ID: " + mushroomId);
            }

        } catch (SQLiteException e) {
            Log.e(TAG, "Database error when deleting mushroom: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) db.close();
        }
    }

    /**
     * Deletes a trip and its associated mushrooms.
     *
     * @param tripId The ID of the trip to delete.
     */
    public void deleteTrip(String tripId) {
        if (tripId == null || tripId.trim().isEmpty()) {
            Log.e(TAG, "Invalid trip ID provided for deletion");
            return;
        }

        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();

            // Delete associated mushrooms first
            db.delete(
                    AppConstants.TABLE_MUSHROOM,
                    AppConstants.COLUMN_MUSHROOM_TRIP_ID + "=?",
                    new String[]{tripId}
            );

            // Delete the trip itself
            int rowsDeleted = db.delete(
                    AppConstants.TABLE_TRIP,
                    AppConstants.COLUMN_TRIP_ID + "=?",
                    new String[]{tripId}
            );

            if (rowsDeleted > 0) {
                Log.i(TAG, "Deleted trip with ID: " + tripId);
            } else {
                Log.w(TAG, "No trip found with ID: " + tripId);
            }

        } catch (SQLiteException e) {
            Log.e(TAG, "Database error when deleting trip: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) db.close();
        }
    }

    /**
     * Retrieves all trips from the database.
     *
     * @return List of all trips, empty list if none found.
     */
    public List<TripDto> getAllTrips() {
        List<TripDto> trips = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(
                    AppConstants.TABLE_TRIP,
                    null,
                    null,
                    null,
                    null,
                    null,
                    AppConstants.COLUMN_TRIP_DATE + " DESC"  // Sort by date descending
            );

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
            }

            return trips;

        } catch (SQLiteException e) {
            Log.e(TAG, "Database error when retrieving all trips: " + e.getMessage());
            return trips;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    /**
     * Retrieves mushrooms associated with a specific trip.
     *
     * @param tripId The ID of the trip.
     * @return List of mushrooms, empty list if none found.
     */
    public List<MushroomDto> getMushroomsForTrip(String tripId) {
        List<MushroomDto> mushrooms = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(
                    AppConstants.TABLE_MUSHROOM,
                    null,
                    AppConstants.COLUMN_MUSHROOM_TRIP_ID + "=?",
                    new String[]{tripId},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    MushroomDto mushroom = new MushroomDto();
                    mushroom.setMushroomId(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_ID)));
                    mushroom.setMushroomType(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_TYPE)));
                    mushroom.setMushroomLocation(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_LOCATION)));
                    mushroom.setMushroomQuantity(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_QUANTITY)));
                    mushroom.setComments(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_COMMENTS)));
                    mushroom.setTripId(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_MUSHROOM_TRIP_ID)));
                    mushrooms.add(mushroom);
                } while (cursor.moveToNext());
            }

            return mushrooms;

        } catch (SQLiteException e) {
            Log.e(TAG, "Database error when retrieving mushrooms: " + e.getMessage());
            return mushrooms;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }
    }

    /**
     * Retrieves trip details by ID.
     *
     * @param tripId The ID of the trip.
     * @return TripDto object or null if not found.
     */
    public TripDto getTripDetails(String tripId) {
        if (tripId == null || tripId.trim().isEmpty()) {
            Log.e(TAG, "Invalid trip ID provided");
            return null;
        }

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();

            cursor = db.query(
                    AppConstants.TABLE_TRIP,
                    null,
                    AppConstants.COLUMN_TRIP_ID + "=?",
                    new String[]{tripId},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                TripDto tripDto = new TripDto();
                tripDto.setTripId(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_ID)));
                tripDto.setTripName(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_NAME)));
                tripDto.setTripDate(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_DATE)));
                tripDto.setTripTime(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_TIME)));
                tripDto.setTripLocation(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_LOCATION)));
                tripDto.setTripDuration(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_DURATION)));
                tripDto.setTripDescription(cursor.getString(cursor.getColumnIndex(AppConstants.COLUMN_TRIP_DESCRIPTION)));

                return tripDto;
            }

            return null;

        } catch (SQLiteException e) {
            Log.e(TAG, "Database error when retrieving trip details: " + e.getMessage());
            return null;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }
    }

    public void clearAllData() {
        SQLiteDatabase db = null;

        try {
            db = dbHelper.getWritableDatabase();

            // Start a transaction for data consistency
            db.beginTransaction();

            // Delete all rows from the mushrooms table
            int mushroomsDeleted = db.delete(AppConstants.TABLE_MUSHROOM, null, null);

            // Delete all rows from the trips table
            int tripsDeleted = db.delete(AppConstants.TABLE_TRIP, null, null);

            // Mark the transaction as successful
            db.setTransactionSuccessful();

            Log.i(TAG, "Cleared all data. Trips deleted: " + tripsDeleted + ", Mushrooms deleted: " + mushroomsDeleted);

        } catch (SQLiteException e) {
            Log.e(TAG, "Database error when clearing all data: " + e.getMessage());
        } finally {
            if (db != null) {
                // End the transaction
                db.endTransaction();
                db.close();
            }
        }
    }}