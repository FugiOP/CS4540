package com.example.calvin.workout.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.calvin.workout.models.userProfile;

import static com.example.calvin.workout.data.UserContract.TABLE_USER.COLUMN_NAME_FEET;
import static com.example.calvin.workout.data.UserContract.TABLE_USER.COLUMN_NAME_GENDER;
import static com.example.calvin.workout.data.UserContract.TABLE_USER.COLUMN_NAME_INCHES;
import static com.example.calvin.workout.data.UserContract.TABLE_USER.COLUMN_NAME_NAME;
import static com.example.calvin.workout.data.UserContract.TABLE_USER.COLUMN_NAME_WEIGHT;
import static com.example.calvin.workout.data.UserContract.TABLE_USER.TABLE_NAME;


/**
 * Created by leona on 8/4/2017.
 */

public class userDbUtils {
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }
    // User is equl to userProfile

    public static long InsertToDb(SQLiteDatabase db, userProfile user){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_NAME, user.getName());
        cv.put(COLUMN_NAME_GENDER, user.getGender());
        cv.put(COLUMN_NAME_FEET, user.getFeet());
        cv.put(COLUMN_NAME_INCHES, user.getInches());
        cv.put(COLUMN_NAME_WEIGHT, user.getWeight());
        return db.insert(TABLE_NAME, null,cv);

    }
}
