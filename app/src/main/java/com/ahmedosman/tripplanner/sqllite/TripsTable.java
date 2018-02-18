package com.ahmedosman.tripplanner.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ahmedosman.tripplanner.models.Trip;

public class TripsTable {
    private static String userName;
    private static final String[] COLUMNS = {HelperDB.TRIP_ID,HelperDB.TRIP_NAME, HelperDB.START_POINT, HelperDB.END_POINT, HelperDB.DAY
            , HelperDB.HOURS, HelperDB.MINUTES, HelperDB.YEAR, HelperDB.MONTH, HelperDB.NOTES, HelperDB.STATUS
            , HelperDB.ROUND_TRIP, HelperDB.REMINDER, HelperDB.TIME_FORMATE, HelperDB.TRIP_IMAGE,HelperDB.USER_NAME};

    public static int insert(Trip trip,Context contxet) {
        HelperDB helperDB = new HelperDB(contxet);
        SQLiteDatabase db = helperDB.getWritableDatabase();
        db.insert(HelperDB.TRIP_TABLE, null, fillData(trip));
        db = helperDB.getReadableDatabase();
        String[] whereParam = {trip.getHours().toString(),trip.getMinutes().toString()};
        Cursor cursor = db.query(HelperDB.TRIP_TABLE, COLUMNS, HelperDB.HOURS + " = ? AND " + HelperDB.MINUTES + " = ?", whereParam, null, null, null);
        if (cursor.moveToNext()) {
           return cursor.getInt(0);
        }
        db.close();
        return 0;
    }

    public static void update(Trip trip,Context context) {
        HelperDB helperDB = new HelperDB(context);
        SQLiteDatabase db = helperDB.getWritableDatabase();
        String[] whereParam = {trip.getTripId().toString()};
        db.update(HelperDB.TRIP_TABLE,fillData(trip),HelperDB.TRIP_ID + " = ?",whereParam);
        db.close();
    }

    public static void delete(Integer tripId,Context context) {
        HelperDB helperDB = new HelperDB(context);
        SQLiteDatabase db = helperDB.getWritableDatabase();
        String[] whereParam = {tripId.toString()};
        db.delete(HelperDB.TRIP_TABLE,"trip_id = ?",whereParam);
        db.close();
    }

    public static Trip[] select(String status,Context context) {
        Trip[] trips;
        HelperDB helperDB = new HelperDB(context);
        SQLiteDatabase database = helperDB.getReadableDatabase();
        String[] whereParam = {status,userName};
        Cursor cursor = database.query(HelperDB.TRIP_TABLE, COLUMNS, HelperDB.STATUS + " = ? AND " + HelperDB.USER_NAME + " = ?", whereParam, null, null, null);
        trips = new Trip[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            Trip trip = new Trip();
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(1));
            trip.setStartPoint(cursor.getString(2));
            trip.setEndPoint(cursor.getString(3));
            trip.setDay(cursor.getInt(4));
            trip.setHours(cursor.getInt(5));
            trip.setMinutes(cursor.getInt(6));
            trip.setYear(cursor.getInt(7));
            trip.setMonth(cursor.getInt(8));
            String notesString = cursor.getString(9);
            String[] notes = notesString.split("\\*\\?");
            trip.setNotes(notes);
            trip.setStatus(cursor.getString(10));
            trip.setRoundTrip(Boolean.parseBoolean(cursor.getString(11)));
            trip.setReminder(Integer.parseInt(cursor.getString(12)));
            trip.setTimeFormate(cursor.getString(13));
            trip.setTripImage(cursor.getString(14));
            trips[i++] = trip;
            i++;
        }
        database.close();
        return trips;
    }

    public static Trip selectTrip(Integer tripId,Context context) {
        HelperDB helperDB = new HelperDB(context);
        SQLiteDatabase database = helperDB.getReadableDatabase();
        String[] whereParam = {tripId.toString()};
        Cursor cursor = database.query(HelperDB.TRIP_TABLE, COLUMNS, HelperDB.TRIP_ID + " = ?", whereParam, null, null, null);
        Trip trip = new Trip();
        while (cursor.moveToNext()) {
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(1));
            trip.setStartPoint(cursor.getString(2));
            trip.setEndPoint(cursor.getString(3));
            trip.setDay(cursor.getInt(4));
            trip.setHours(cursor.getInt(5));
            trip.setMinutes(cursor.getInt(6));
            trip.setYear(cursor.getInt(7));
            trip.setMonth(cursor.getInt(8));
            String notesString = cursor.getString(9);
            String[] notes = notesString.split("\\*\\?");
            trip.setNotes(notes);
            trip.setStatus(cursor.getString(10));
            trip.setRoundTrip(Boolean.parseBoolean(cursor.getString(11)));
            trip.setReminder(Integer.parseInt(cursor.getString(12)));
            trip.setTimeFormate(cursor.getString(13));
            trip.setTripImage(cursor.getString(14));
        }
        database.close();
        return trip;
    }

    public static void setUserName(String username){
            userName = username;
    }

    public static String getUserName(){
        return userName;
    }

    public static ContentValues fillData(Trip trip){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNS[1], trip.getTripName());
        contentValues.put(COLUMNS[2], trip.getStartPoint());
        contentValues.put(COLUMNS[3], trip.getEndPoint());
        contentValues.put(COLUMNS[4], trip.getDay());
        contentValues.put(COLUMNS[5], trip.getHours());
        contentValues.put(COLUMNS[6], trip.getMinutes());
        contentValues.put(COLUMNS[7], trip.getYear());
        contentValues.put(COLUMNS[8], trip.getMonth());
        contentValues.put(COLUMNS[9], trip.getNotes());
        contentValues.put(COLUMNS[10], trip.getStatus());
        contentValues.put(COLUMNS[11], trip.getRoundTrip());
        contentValues.put(COLUMNS[12], trip.getReminder());
        contentValues.put(COLUMNS[13], trip.getTimeFormate());
        contentValues.put(COLUMNS[14], trip.getTripImage());
        contentValues.put(COLUMNS[15], userName);
        return contentValues;
    }

    static public class HelperDB extends SQLiteOpenHelper {
        public static final String DB_NAME = "tripsdb";
        public static final Integer DB_VERSION = 1;
        public static final String TRIP_NAME = "trip_name";
        public static final String START_POINT = "start_point";
        public static final String END_POINT = "end_point";
        public static final String REMINDER = "reminder";
        public static final String ROUND_TRIP = "round_trip";
        public static final String TRIP_IMAGE = "trip_image";
        public static final String NOTES = "notes";
        public static final String HOURS = "hour";
        public static final String STATUS = "status";
        public static final String MINUTES = "minutes";
        public static final String YEAR = "year";
        public static final String MONTH = "month";
        public static final String DAY = "day";
        public static final String TIME_FORMATE = "time_formate";
        public static final String USER_NAME = "user_name";
        public static final String TRIP_ID = "trip_id";

        public static final String TRIP_TABLE = "trips";
        public static final String CREATE_TRIP_TABLE = "CREATE TABLE " + TRIP_TABLE + "(" + TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+ TRIP_NAME + " VARCHAR(255), "+ START_POINT + " VARCHAR(255), "
                + END_POINT + " VARCHAR(255), "+ DAY + " int, "+ HOURS + " int, "+ MINUTES + " int, "
                + YEAR + " int, "+ MONTH + " int, "+ NOTES + " VARCHAR(1000), "+ STATUS + " VARCHAR(255), "
                + ROUND_TRIP + " VARCHAR(255), "+ REMINDER + " VARCHAR(255), "+ TIME_FORMATE + " VARCHAR(255), "
                + TRIP_IMAGE + " VARCHAR(255),"+ USER_NAME + " VARCHAR(255)"+");";

        public HelperDB(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TRIP_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
