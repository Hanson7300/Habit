package com.example.android.habit.data;

import android.provider.BaseColumns;

public final class HabitContract {
    private HabitContract(){}
    public static final class HabitEntry implements BaseColumns{
        public static final String TABLE_NAME = "habit";
        //id自动累加
        public static final String _ID =BaseColumns._ID;
        public static final String EVENT = "event";
        public static final String EXP_TIME_MINUTES = "time";
    }

}
