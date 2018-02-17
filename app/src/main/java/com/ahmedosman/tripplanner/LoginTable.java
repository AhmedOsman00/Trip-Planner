package com.ahmedosman.tripplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginTable {
    private static ContentValues contentValues;
    private static Context context;
    private static final String[] COLUMNS = {HelperDB.USER_NAME, HelperDB.PASSWORD};

    public LoginTable(Context context) {
        this.context = context;
    }

    public static void insert(User user) {
        TripsTable.HelperDB helperDB = new TripsTable.HelperDB(context);
        SQLiteDatabase db = helperDB.getWritableDatabase();
        contentValues.put(COLUMNS[0], user.getEmail());
        contentValues.put(COLUMNS[1], user.getPassword());
        db.insert(HelperDB.LOGIN_TABLE, null, contentValues);
        db.close();
    }

    public static Boolean select(User user) {
        TripsTable.HelperDB helperDB = new TripsTable.HelperDB(context);
        SQLiteDatabase database = helperDB.getReadableDatabase();
        String[] whereParam = {user.getEmail(),user.getPassword()};
        Cursor cursor = database.query(HelperDB.LOGIN_TABLE, COLUMNS, "user_name = ? AND password = ?", whereParam, null, null, null);
        if (cursor.moveToNext()) {
            return true;
        }
        return false;
    }

    static public class HelperDB extends SQLiteOpenHelper {
        private static final String USER_NAME = "user_name";
        private static final String PASSWORD = "password";
        private static final String LOGIN_TABLE = "login";
        private static final String CREATE_LOGIN_TABLE = "CREATE TABLE " + LOGIN_TABLE + "(" + USER_NAME + " VARCHAR(255), "
                + PASSWORD + " VARCHAR(255));";

        public HelperDB(Context context) {
            super(context, TripsTable.HelperDB.DB_NAME, null, TripsTable.HelperDB.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_LOGIN_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
