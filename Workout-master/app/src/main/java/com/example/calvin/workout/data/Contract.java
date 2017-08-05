package com.example.calvin.workout.data;

/**
 * Created by FugiBeast on 8/5/2017.
 */

import android.provider.BaseColumns;

public class Contract{
    public static class TABLE_USER_WORKOUT implements BaseColumns {
        public static final String TABLE_NAME = "exercises";
        public static final String COLUMN_NAME = "name";;
        public static final String COLUMN_CALORIES = "calories";
        public static final String COLUMN_IMAGE = "img";
    }

    public static class TABLE_DB_WORKOUT implements BaseColumns {
        public static final String TABLE_NAME = "exercises";
        public static final String COLUMN_NAME = "name";;
        public static final String COLUMN_CALORIES = "calories";
        public static final String COLUMN_IMAGE = "img";
    }
}