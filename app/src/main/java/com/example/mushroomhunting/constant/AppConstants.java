package com.example.mushroomhunting.constant;

public class AppConstants {

    public static final String DATABASE_NAME = "mushroom_hunting.db";

    // Trip table and column names
    public static final String TABLE_TRIP = "trips";
    public static final String COLUMN_TRIP_ID = "trip_id";
    public static final String COLUMN_TRIP_NAME = "trip_name";
    public static final String COLUMN_TRIP_DATE = "trip_date";
    public static final String COLUMN_TRIP_TIME = "trip_time";
    public static final String COLUMN_TRIP_LOCATION = "trip_location";
    public static final String COLUMN_TRIP_DURATION = "trip_duration";
    public static final String COLUMN_TRIP_DESCRIPTION = "trip_description";

    // Mushroom table and column names
    public static final String TABLE_MUSHROOM = "mushrooms";
    public static final String COLUMN_MUSHROOM_ID = "mushroom_id";
    public static final String COLUMN_MUSHROOM_TYPE = "mushroom_type";
    public static final String COLUMN_MUSHROOM_LOCATION = "mushroom_location";
    public static final String COLUMN_MUSHROOM_QUANTITY = "mushroom_quantity";
    public static final String COLUMN_MUSHROOM_COMMENTS = "comments";
    public static final String COLUMN_MUSHROOM_TRIP_ID = "trip_id";

}