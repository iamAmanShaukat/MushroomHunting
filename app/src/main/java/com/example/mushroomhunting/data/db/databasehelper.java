// CODE FOR STORING DATA USING SQLite

package com.example.mushroomhuntingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mushroom_hunting.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TRIPS = "trips";
    private static final String TABLE_MUSHROOMS = "mushrooms";

    // Trip column names
    private static final String COLUMN_TRIP_ID = "id";
    private static final String COLUMN_TRIP_NAME = "name";
    private static final String COLUMN_TRIP_DATE = "date";
    private static final String COLUMN_TRIP_TIME = "time";
    private static final String COLUMN_TRIP_LOCATION = "location";
    private static final String COLUMN_TRIP_DURATION = "duration";
    private static final String COLUMN_TRIP_DESCRIPTION = "description";

    // Mushroom column names
    private static final String COLUMN_MUSHROOM_ID = "id";
    private static final String COLUMN_MUSHROOM_TRIP_ID = "tripId";
    private static final String COLUMN_MUSHROOM_TYPE = "type";
    private static final String COLUMN_MUSHROOM_LOCATION = "location";
    private static final String COLUMN_MUSHROOM_QUANTITY = "quantity";
    private static final String COLUMN_MUSHROOM_COMMENTS = "comments";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTripsTable = "CREATE TABLE " + TABLE_TRIPS + " (" +
                COLUMN_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRIP_NAME + " TEXT NOT NULL, " +
                COLUMN_TRIP_DATE + " TEXT NOT NULL, " +
                COLUMN_TRIP_TIME + " TEXT NOT NULL, " +
                COLUMN_TRIP_LOCATION + " TEXT NOT NULL, " +
                COLUMN_TRIP_DURATION + " TEXT NOT NULL, " +
                COLUMN_TRIP_DESCRIPTION + " TEXT)";
        String createMushroomsTable = "CREATE TABLE " + TABLE_MUSHROOMS + " (" +
                COLUMN_MUSHROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MUSHROOM_TRIP_ID + " INTEGER NOT NULL, " +
                COLUMN_MUSHROOM_TYPE + " TEXT NOT NULL, " +
                COLUMN_MUSHROOM_LOCATION + " TEXT NOT NULL, " +
                COLUMN_MUSHROOM_QUANTITY + " INTEGER NOT NULL, " +
                COLUMN_MUSHROOM_COMMENTS + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_MUSHROOM_TRIP_ID + ") REFERENCES " + TABLE_TRIPS + "(" + COLUMN_TRIP_ID + ") ON DELETE CASCADE)";
        db.execSQL(createTripsTable);
        db.execSQL(createMushroomsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSHROOMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        onCreate(db);
    }

    // Add a new trip
    public long addTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIP_NAME, trip.getName());
        values.put(COLUMN_TRIP_DATE, trip.getDate());
        values.put(COLUMN_TRIP_TIME, trip.getTime());
        values.put(COLUMN_TRIP_LOCATION, trip.getLocation());
        values.put(COLUMN_TRIP_DURATION, trip.getDuration());
        values.put(COLUMN_TRIP_DESCRIPTION, trip.getDescription());
        long tripId = db.insert(TABLE_TRIPS, null, values);
        db.close();
        return tripId;
    }

    // Add a new mushroom
    public void addMushroom(long tripId, String type, String location, int quantity, String comments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MUSHROOM_TRIP_ID, tripId);
        values.put(COLUMN_MUSHROOM_TYPE, type);
        values.put(COLUMN_MUSHROOM_LOCATION, location);
        values.put(COLUMN_MUSHROOM_QUANTITY, quantity);
        values.put(COLUMN_MUSHROOM_COMMENTS, comments);
        db.insert(TABLE_MUSHROOMS, null, values);
        db.close();
    }

    //CODE FOR EXPORTING DATA AS JSON


    // Export data to JSON file
    public boolean exportDataToJson() {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean success = false;

        try {
            JSONArray tripsArray = new JSONArray();
            Cursor tripsCursor = db.rawQuery("SELECT * FROM " + TABLE_TRIPS, null);
            if (tripsCursor.moveToFirst()) {
                do {
                    long tripId = tripsCursor.getLong(tripsCursor.getColumnIndex(COLUMN_TRIP_ID));
                    JSONObject tripObject = new JSONObject();
                    tripObject.put(COLUMN_TRIP_ID, tripId);
                    tripObject.put(COLUMN_TRIP_NAME, tripsCursor.getString(tripsCursor.getColumnIndex(COLUMN_TRIP_NAME)));
                    tripObject.put(COLUMN_TRIP_DATE, tripsCursor.getString(tripsCursor.getColumnIndex(COLUMN_TRIP_DATE)));
                    tripObject.put(COLUMN_TRIP_TIME, tripsCursor.getString(tripsCursor.getColumnIndex(COLUMN_TRIP_TIME)));
                    tripObject.put(COLUMN_TRIP_LOCATION, tripsCursor.getString(tripsCursor.getColumnIndex(COLUMN_TRIP_LOCATION)));
                    tripObject.put(COLUMN_TRIP_DURATION, tripsCursor.getString(tripsCursor.getColumnIndex(COLUMN_TRIP_DURATION)));
                    tripObject.put(COLUMN_TRIP_DESCRIPTION, tripsCursor.getString(tripsCursor.getColumnIndex(COLUMN_TRIP_DESCRIPTION)));

                    JSONArray mushroomsArray = new JSONArray();
                    Cursor mushroomsCursor = db.rawQuery("SELECT * FROM " + TABLE_MUSHROOMS + " WHERE " + COLUMN_MUSHROOM_TRIP_ID + " = ?", new String[]{String.valueOf(tripId)});
                    if (mushroomsCursor.moveToFirst()) {
                        do {
                            JSONObject mushroomObject = new JSONObject();
                            mushroomObject.put(COLUMN_MUSHROOM_ID, mushroomsCursor.getLong(mushroomsCursor.getColumnIndex(COLUMN_MUSHROOM_ID)));
                            mushroomObject.put(COLUMN_MUSHROOM_TYPE, mushroomsCursor.getString(mushroomsCursor.getColumnIndex(COLUMN_MUSHROOM_TYPE)));
                            mushroomObject.put(COLUMN_MUSHROOM_LOCATION, mushroomsCursor.getString(mushroomsCursor.getColumnIndex(COLUMN_MUSHROOM_LOCATION)));
                            mushroomObject.put(COLUMN_MUSHROOM_QUANTITY, mushroomsCursor.getInt(mushroomsCursor.getColumnIndex(COLUMN_MUSHROOM_QUANTITY)));
                            mushroomObject.put(COLUMN_MUSHROOM_COMMENTS, mushroomsCursor.getString(mushroomsCursor.getColumnIndex(COLUMN_MUSHROOM_COMMENTS)));
                            mushroomsArray.put(mushroomObject);
                        } while (mushroomsCursor.moveToNext());
                    }
                    mushroomsCursor.close();

                    tripObject.put("mushrooms", mushroomsArray);
                    tripsArray.put(tripObject);
                } while (tripsCursor.moveToNext());
            }
            tripsCursor.close();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("trips", tripsArray);

            File jsonFile = new File(Environment.getExternalStorageDirectory(), "mushroom_hunting_data.json");
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
            success = true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return success;
    }

    // Import data from JSON file
    public List<Trip> readDataFromJson() {
        List<Trip> trips = new ArrayList<>();
        File jsonFile = new File(Environment.getExternalStorageDirectory(), "mushroom_hunting_data.json");

        try {
            FileInputStream inputStream = new FileInputStream(jsonFile);
            byte[] jsonData = new byte[(int) jsonFile.length()];
            inputStream.read(jsonData);
            inputStream.close();
            String jsonString = new String(jsonData, Charset.defaultCharset());

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray tripsArray = jsonObject.getJSONArray("trips");

            for (int i = 0; i < tripsArray.length(); i++) {
                JSONObject tripObject = tripsArray.getJSONObject(i);
                Trip trip = new Trip(
                        tripObject.getLong(COLUMN_TRIP_ID),
                        tripObject.getString(COLUMN_TRIP_NAME),
                        tripObject.getString(COLUMN_TRIP_DATE),
                        tripObject.getString(COLUMN_TRIP_TIME),
                        tripObject.getString(COLUMN_TRIP_LOCATION),
                        tripObject.getString(COLUMN_TRIP_DURATION),
                        tripObject.optString(COLUMN_TRIP_DESCRIPTION, null)
                );

                JSONArray mushroomsArray = tripObject.getJSONArray("mushrooms");
                List<Mushroom> mushrooms = new ArrayList<>();
                for (int j = 0; j < mushroomsArray.length(); j++) {
                    JSONObject mushroomObject = mushroomsArray.getJSONObject(j);
                    Mushroom mushroom = new Mushroom(
                            mushroomObject.getLong(COLUMN_MUSHROOM_ID),
                            trip.getId(),
                            mushroomObject.getString(COLUMN_MUSHROOM_TYPE),
                            mushroomObject.getString(COLUMN_MUSHROOM_LOCATION),
                            mushroomObject.getInt(COLUMN_MUSHROOM_QUANTITY),
                            mushroomObject.optString(COLUMN_MUSHROOM_COMMENTS, null)
                    );
                    mushrooms.add(mushroom);
                }
                trip.setMushrooms(mushrooms);
                trips.add(trip);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trips;
    }
}
