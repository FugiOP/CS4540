package com.example.calvin.workout.data;

/**
 * Created by FugiBeast on 8/5/2017.
 */

import android.provider.BaseColumns;

public class Contract{

    public static class TABLE_USER_WORKOUT implements BaseColumns {
        public static final String TABLE_NAME = "exercises_user";
        public static final String COLUMN_NAME_EXERCISE = "name_user";;
        public static final String COLUMN_NAME_TIME = "time_user";
    }

    public static class TABLE_DB_WORKOUT implements BaseColumns {
        public static final String TABLE_NAME = "exercises_db";
        public static final String COLUMN_NAME_EXERCISE = "name_db";;
        public static final String COLUMN_NAME_CALORIES = "calories_db";
    }

    public static class TABLE_USER_CALORIES implements BaseColumns{
        public static final String TABLE_NAME = "calories_user";
        public static final String COLUMN_NAME_CALORIES = "calories_burnt";
        public static final String COLUMN_NAME_DATE= "date";
    }
}