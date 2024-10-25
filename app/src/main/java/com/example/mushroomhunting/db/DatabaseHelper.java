package com.example.mushroomhunting.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mushroomhunting.constant.AppConstants;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, AppConstants.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTripTable = "CREATE TABLE " + AppConstants.TABLE_TRIP + " (" +
                AppConstants.COLUMN_TRIP_ID + " TEXT PRIMARY KEY, " +
                AppConstants.COLUMN_TRIP_NAME + " TEXT, " +
                AppConstants.COLUMN_TRIP_DATE + " TEXT, " +
                AppConstants.COLUMN_TRIP_TIME + " TEXT, " +
                AppConstants.COLUMN_TRIP_LOCATION + " TEXT, " +
                AppConstants.COLUMN_TRIP_DURATION + " TEXT, " +
                AppConstants.COLUMN_TRIP_DESCRIPTION + " TEXT)";

        String createMushroomTable = "CREATE TABLE " + AppConstants.TABLE_MUSHROOM + " (" +
                AppConstants.COLUMN_MUSHROOM_ID + " TEXT PRIMARY KEY, " +
                AppConstants.COLUMN_MUSHROOM_TYPE + " TEXT, " +
                AppConstants.COLUMN_MUSHROOM_LOCATION + " TEXT, " +
                AppConstants.COLUMN_MUSHROOM_QUANTITY + " TEXT, " +
                AppConstants.COLUMN_MUSHROOM_COMMENTS + " TEXT, " +
                AppConstants.COLUMN_MUSHROOM_TRIP_ID + " TEXT, " +
                "FOREIGN KEY (" + AppConstants.COLUMN_MUSHROOM_TRIP_ID + ") REFERENCES " +
                AppConstants.TABLE_TRIP + "(" + AppConstants.COLUMN_TRIP_ID + "))";

        db.execSQL(createTripTable);
        db.execSQL(createMushroomTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AppConstants.TABLE_MUSHROOM);
        db.execSQL("DROP TABLE IF EXISTS " + AppConstants.TABLE_TRIP);
        onCreate(db);
    }
}