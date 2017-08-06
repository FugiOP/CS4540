package com.example.calvin.workout.data;

/**
 * Created by FugiBeast on 8/5/2017.
 */
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static com.example.calvin.workout.data.Contract.TABLE_USER_WORKOUT.TABLE_NAME;


public class DatabaseUtils {
    public static Cursor getAll(SQLiteDatabase db){
        Cursor cursor = db.query(
                TABLE_NAME,null,null,null,null,null,null
        );
        return cursor;
    }

    public static void addExercise(SQLiteDatabase db, String name, String time) {
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_USER_WORKOUT.COLUMN_NAME_EXERCISE, name);
        cv.put(Contract.TABLE_USER_WORKOUT.COLUMN_NAME_TIME, time);

        db.insert(Contract.TABLE_USER_WORKOUT.TABLE_NAME, null, cv);
    }
}