package com.example.calvin.workout.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by FugiBeast on 8/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Added image column tp create statement
        String query = "CREATE TABLE " + Contract.TABLE_USER_WORKOUT.TABLE_NAME + " (" +
                Contract.TABLE_USER_WORKOUT.COLUMN_NAME + " TEXT NOT NULL, " +
                Contract.TABLE_USER_WORKOUT.COLUMN_CALORIES + " TEXT NOT NULL, " +
                Contract.TABLE_USER_WORKOUT.COLUMN_IMAGE + " TEXT " +
                ");";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + Contract.TABLE_DB_WORKOUT.TABLE_NAME + " (" +
                Contract.TABLE_DB_WORKOUT.COLUMN_NAME + " TEXT NOT NULL, " +
                Contract.TABLE_DB_WORKOUT.COLUMN_CALORIES + " TEXT NOT NULL, " +
                Contract.TABLE_DB_WORKOUT.COLUMN_IMAGE + " TEXT " +
                ");";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //add later
    }
}
