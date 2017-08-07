package com.example.calvin.workout.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by FugiBeast on 8/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workout.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Added image column tp create statement
        String query = "CREATE TABLE " + Contract.TABLE_USER_WORKOUT.TABLE_NAME + " (" +
                Contract.TABLE_USER_WORKOUT._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_USER_WORKOUT.COLUMN_NAME_EXERCISE + " TEXT NOT NULL, " +
                Contract.TABLE_USER_WORKOUT.COLUMN_NAME_TIME + " TEXT NOT NULL" +
                ");";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + Contract.TABLE_DB_WORKOUT.TABLE_NAME + " (" +
                Contract.TABLE_DB_WORKOUT._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_DB_WORKOUT.COLUMN_NAME_EXERCISE + " TEXT NOT NULL, " +
                Contract.TABLE_DB_WORKOUT.COLUMN_NAME_CALORIES + " DOUBLE NOT NULL " +
                ");";
        db.execSQL(query2);

        String query3 = "INSERT INTO "+Contract.TABLE_DB_WORKOUT.TABLE_NAME+" (name_db,calories_db) VALUES ( " +
                "'Running',0.076);" +
                "INSERT INTO "+Contract.TABLE_DB_WORKOUT.TABLE_NAME+" (name_db,calories_db) VALUES ( " +
                "'Jump Rope',0.098);" +
                "INSERT INTO "+Contract.TABLE_DB_WORKOUT.TABLE_NAME+" (name_db,calories_db) VALUES ( " +
                "'Squats',0.096);" +
                "INSERT INTO "+Contract.TABLE_DB_WORKOUT.TABLE_NAME+" (name_db,calories_db) VALUES ( " +
                "'Push Ups',0.064);" +
                "INSERT INTO "+Contract.TABLE_DB_WORKOUT.TABLE_NAME+" (name_db,calories_db) VALUES ( " +
                "'Sit Ups',0.06);";
        db.execSQL(query3);

        String query4 = "CREATE TABLE " + Contract.TABLE_USER_CALORIES.TABLE_NAME + " (" +
                Contract.TABLE_USER_WORKOUT._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_USER_CALORIES.COLUMN_NAME_CALORIES + " DOUBLE NOT NULL, " +
                Contract.TABLE_USER_CALORIES.COLUMN_NAME_DATE + " TEXT NOT NULL " +
                ");";

        db.execSQL(query4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //add later
    }
}
