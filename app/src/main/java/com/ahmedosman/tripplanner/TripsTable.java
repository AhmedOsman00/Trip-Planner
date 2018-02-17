package com.ahmedosman.tripplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TripsTable {
    private static ContentValues contentValues;
    private static Context context;
    private static final String[] COLUMNS = {HelperDB.TRIP_NAME, HelperDB.START_POINT, HelperDB.END_POINT, HelperDB.DAY
            , HelperDB.HOURS, HelperDB.MINUTES, HelperDB.YEAR, HelperDB.MONTH, HelperDB.NOTES, HelperDB.STATUS
            , HelperDB.ROUND_TRIP, HelperDB.REMINDER, HelperDB.TIME_FORMATE, HelperDB.TRIP_IMAGE};

    public TripsTable(Context context) {
        this.context = context;
    }

    public static void insert(Trip trip) {
        HelperDB helperDB = new HelperDB(context);
        SQLiteDatabase db = helperDB.getWritableDatabase();
        contentValues.put(COLUMNS[0], trip.getTripName());
        contentValues.put(COLUMNS[1], trip.getStartPoint());
        contentValues.put(COLUMNS[0], trip.getEndPoint());
        contentValues.put(COLUMNS[1], trip.getDay());
        contentValues.put(COLUMNS[0], trip.getHours());
        contentValues.put(COLUMNS[1], trip.getMinutes());
        contentValues.put(COLUMNS[0], trip.getYear());
        contentValues.put(COLUMNS[1], trip.getMonth());
        contentValues.put(COLUMNS[0], trip.getNotes());
        contentValues.put(COLUMNS[1], trip.getStatus());
        contentValues.put(COLUMNS[0], trip.getRoundTrip());
        contentValues.put(COLUMNS[1], trip.getReminder());
        contentValues.put(COLUMNS[0], trip.getTimeFormate());
        contentValues.put(COLUMNS[1], trip.getTripImage());
        db.insert(HelperDB.TRIP_TABLE, null, contentValues);
        db.close();
    }

    public static void update(Trip trip) {
        HelperDB helperDB = new HelperDB(context);
        SQLiteDatabase db = helperDB.getWritableDatabase();
        contentValues.put(COLUMNS[0], trip.getTripName());
        contentValues.put(COLUMNS[1], trip.getStartPoint());
        contentValues.put(COLUMNS[0], trip.getEndPoint());
        contentValues.put(COLUMNS[1], trip.getDay());
        contentValues.put(COLUMNS[0], trip.getHours());
        contentValues.put(COLUMNS[1], trip.getMinutes());
        contentValues.put(COLUMNS[0], trip.getYear());
        contentValues.put(COLUMNS[1], trip.getMonth());
        contentValues.put(COLUMNS[0], trip.getNotes());
        contentValues.put(COLUMNS[1], trip.getStatus());
        contentValues.put(COLUMNS[0], trip.getRoundTrip());
        contentValues.put(COLUMNS[1], trip.getReminder());
        contentValues.put(COLUMNS[0], trip.getTimeFormate());
        contentValues.put(COLUMNS[1], trip.getTripImage());
        String[] whereParam = {trip.getTripId().toString()};
        db.update(HelperDB.TRIP_TABLE,contentValues,"trip_id = ?",whereParam);
        db.close();
    }

    public static void delete(Integer tripId) {
        HelperDB helperDB = new HelperDB(context);
        SQLiteDatabase db = helperDB.getWritableDatabase();
        String[] whereParam = {tripId.toString()};
        db.delete(HelperDB.TRIP_TABLE,"trip_id = ?",whereParam);
        db.close();
    }

    public static Trip[] select(String status) {
        Trip[] trips;
        HelperDB helperDB = new HelperDB(context);
        SQLiteDatabase database = helperDB.getReadableDatabase();
        String[] whereParam = {status};
        Cursor cursor = database.query(HelperDB.TRIP_TABLE, COLUMNS, "trip_status = ?", whereParam, null, null, null);
        trips = new Trip[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            Trip trip = new Trip();
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(0));
            trip.setStartPoint(cursor.getString(1));
            trip.setEndPoint(cursor.getString(1));
            trip.setDay(Integer.parseInt(cursor.getString(1)));
            trip.setHours(Integer.parseInt(cursor.getString(1)));
            trip.setMinutes(Integer.parseInt(cursor.getString(1)));
            trip.setYear(Integer.parseInt(cursor.getString(1)));
            trip.setMonth(Integer.parseInt(cursor.getString(1)));
            String notesString = cursor.getString(1);
            String[] notes = notesString.split("\\*\\?");
            trip.setNotes(notes);
            trip.setStatus(cursor.getString(1));
            trip.setRoundTrip(Boolean.parseBoolean(cursor.getString(1)));
            trip.setReminder(Integer.parseInt(cursor.getString(1)));
            trip.setTimeFormate(cursor.getString(1));
            trip.setTripImage(cursor.getString(1));
            trips[i++] = trip;
            i++;
        }
        database.close();
        return trips;
    }

    public static Trip selectTrip(Integer tripId) {
        HelperDB helperDB = new HelperDB(context);
        SQLiteDatabase database = helperDB.getReadableDatabase();
        String[] whereParam = {tripId.toString()};
        Cursor cursor = database.query(HelperDB.TRIP_TABLE, COLUMNS, "trip_id = ?", whereParam, null, null, null);
        Trip trip = new Trip();
        while (cursor.moveToNext()) {
            trip.setTripId(cursor.getInt(0));
            trip.setTripName(cursor.getString(0));
            trip.setStartPoint(cursor.getString(1));
            trip.setEndPoint(cursor.getString(1));
            trip.setDay(Integer.parseInt(cursor.getString(1)));
            trip.setHours(Integer.parseInt(cursor.getString(1)));
            trip.setMinutes(Integer.parseInt(cursor.getString(1)));
            trip.setYear(Integer.parseInt(cursor.getString(1)));
            trip.setMonth(Integer.parseInt(cursor.getString(1)));
            String notesString = cursor.getString(1);
            String[] notes = notesString.split("\\*\\?");
            trip.setNotes(notes);
            trip.setStatus(cursor.getString(1));
            trip.setRoundTrip(Boolean.parseBoolean(cursor.getString(1)));
            trip.setReminder(Integer.parseInt(cursor.getString(1)));
            trip.setTimeFormate(cursor.getString(1));
            trip.setTripImage(cursor.getString(1));
        }
        database.close();
        return trip;
    }

    public void setUserName(String username){

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

        public static final String TRIP_TABLE = "trips";
        public static final String CREATE_TRIP_TABLE = "CREATE TABLE " + TRIP_TABLE + "(" + TRIP_NAME + " VARCHAR(255), "
                + TRIP_IMAGE + " VARCHAR(255));";

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
