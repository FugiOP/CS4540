package com.example.calvin.workout.data;

import android.provider.BaseColumns;

/**
 * Created by leona on 8/4/2017.
 */

public class UserContract {
    public static class TABLE_USER implements BaseColumns {

        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_FEET= "feet";
        public static final String COLUMN_NAME_INCHES= "inches";
        public static final String COLUMN_NAME_WEIGHT = "weight";
    }
}
