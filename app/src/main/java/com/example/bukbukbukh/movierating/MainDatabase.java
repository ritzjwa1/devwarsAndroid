package com.example.bukbukbukh.movierating;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bukbukbukh on 1/24/16.
 */
public class MainDatabase extends SQLiteOpenHelper {

    private static MainDatabase sInstance;
    private int numOfRecords = 2;
    private static final int DATABASE_VERSION = 2;

    private static final String USER_PASSWORD = "user_password";

    private static final String ID = "_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    private static final String LOGIN_STATUS = "login_status";

    private static final String USER_TABLE = "CREATE TABLE "
            + USER_PASSWORD
            + " (" + ID
            + " INTEGER, "
            + FIRST_NAME
            + " TEXT, "
            + LAST_NAME
            + " TEXT, "
            + USER_NAME
            + " TEXT, "
            + PASSWORD + " TEXT, "
            + LOGIN_STATUS + " BOOLEAN"
            + ");";

    private static final String INSERT_USER1 = "INSERT INTO " + USER_PASSWORD + " (" + ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + USER_NAME + ", " + PASSWORD + ", " + LOGIN_STATUS + ") "
            + "VALUES (1, 'Jagan', 'Srini', 'jagsr8', 'password', 0)";

    private static final String INSERT_USER2 = "INSERT INTO " + USER_PASSWORD + " (" + ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + USER_NAME + ", " + PASSWORD + ", " + LOGIN_STATUS + ") "
            + "VALUES (2, 'Hari', 'Narayan', 'ganhari123', 'password', 0)";

    private static final String INSERT_USER3 = "INSERT INTO " + USER_PASSWORD + " (" + ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + USER_NAME + ", " + PASSWORD + ", " + LOGIN_STATUS + ") "
            + "VALUES (2, 'Hari', 'Narayan', 'ganhari123', 'password', 0)";

    MainDatabase(Context context) {
        super(context, "UserMovieSelections", null, DATABASE_VERSION);
    }

    public static synchronized MainDatabase getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MainDatabase(context.getApplicationContext());
        }
        return sInstance;
    }


    /* Creates all tables in the database*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE);
        db.execSQL(INSERT_USER1);
        db.execSQL(INSERT_USER2);
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean checkUserAndPassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + USER_PASSWORD + " WHERE " + USER_NAME + " = '" + username + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String pass = cursor.getString(cursor.getColumnIndex(PASSWORD));
                if (pass.equals(password)) {
                    changeLoginStatusTrue(username);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void changeLoginStatusTrue(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + USER_PASSWORD + " SET " + LOGIN_STATUS + " = 1 WHERE " + USER_NAME + " = '" + username + "'";
        db.execSQL(sql);
    }

    public void changeLoginStatusFalse(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + USER_PASSWORD + " SET " + LOGIN_STATUS + " = 0 WHERE " + USER_NAME + " = '" + username + "'";
        db.execSQL(sql);
    }

    public boolean getLoginStatusTrue() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + USER_PASSWORD + " WHERE " + LOGIN_STATUS + " = 1";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}