package com.example.bukbukbukh.movierating;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ganapathy Hari Narayan on 1/24/16.
 * This is the backup Database sqlite class which is used if the heroku server goes down
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

    public String getLoginStatusTrue() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + USER_PASSWORD + " WHERE " + LOGIN_STATUS + " = 1";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String returnString = cursor.getString(cursor.getColumnIndex(USER_NAME));
                return returnString;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int num = user.getNumOfUsers();
        String firstName = user.getFirst_name();
        String lastName = user.getLast_name();
        String userName = user.getUser_name();
        String password = user.getPassword();
        int loginStatus = 0;

        String sql = "SELECT * FROM " + USER_PASSWORD + " WHERE " + USER_NAME + " = '" + userName + "';";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return false;
            } else {
                String sql1 = "INSERT INTO " + USER_PASSWORD + " (" + ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + USER_NAME + ", " + PASSWORD + ", " + LOGIN_STATUS + ") "
                        + "VALUES (" + num + ", '" + firstName + "', '" + lastName + "', '" + userName + "', '" + password + "', " + loginStatus + ");";
                Log.d("sql", sql1);
                db.execSQL(sql1);
                return true;
            }
        } else {
            return false;
        }
    }


    //Register_screen.java temp code
    /*User newUser = new User(ed1.getText().toString(), ed2.getText().toString(), ed3.getText().toString(), ed4.getText().toString());
            boolean addUserTrue = mDb.addUser(newUser);
            if (!addUserTrue) {
                LoginStatus checkRegisterStatus = LoginStatus.newInstance(R.string.register_status2);
                checkRegisterStatus.show(getFragmentManager(), "dialog");
            } else {
                LoginStatus checkRegisterStatus = LoginStatus.newInstance(R.string.register_status3);
                checkRegisterStatus.show(getFragmentManager(), "dialog");
                ed1.setText("");
                ed2.setText("");
                ed3.setText("");
                ed4.setText("");
                ed5.setText("");
            }*/

    //Login_screen.java temp code
    /*Boolean isLogintrue = mDB.checkUserAndPassword(ed.getText().toString(), ed2.getText().toString());

            if (isLogintrue) {
                mDB.changeLoginStatusTrue(ed.getText().toString());
                LoginStatus login = LoginStatus.newInstance(R.string.loginSuccess);
                login.show(getFragmentManager(), "dialog");
                username = ed.getText().toString();

                new CountDownTimer(1500, 1000) {
                    Intent intent;
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        intent = new Intent(login_screen.this, MainMenu.class);
                        intent.putExtra("USERNAME", login_screen.this.username);
                        startActivity(intent);
                    }
                }.start();

            } else {
                LoginStatus login = LoginStatus.newInstance(R.string.loginFailure);
                login.show(getFragmentManager(), "dialog");
                loginAttempt--;
            }
        } else {
            LoginStatus login = LoginStatus.newInstance(R.string.StopLogin);
            login.show(getFragmentManager(), "dialog");
        }*/
}
