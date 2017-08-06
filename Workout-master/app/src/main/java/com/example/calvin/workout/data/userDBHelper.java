package com.example.calvin.workout.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by leona on 8/4/2017.
 */

public class userDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "user.db";
    private static final String TAG = "dbhelper";

    public userDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String queryString = "CREATE TABLE " + UserContract.TABLE_USER.TABLE_NAME + " (" +
                UserContract.TABLE_USER.COLUMN_NAME_NAME + " TEXT," +

                UserContract.TABLE_USER.COLUMN_NAME_GENDER + " TEXT," +
                UserContract.TABLE_USER.COLUMN_NAME_FEET + " INTEGER," +
                UserContract.TABLE_USER.COLUMN_NAME_INCHES + " INTEGER," +
                UserContract.TABLE_USER.COLUMN_NAME_WEIGHT + " INTEGER);";


        Log.d(TAG, "create Table sql: " +queryString);
        db.execSQL(queryString);


    }

    //THIS METHOD MUST BE OVERRIDEN
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
