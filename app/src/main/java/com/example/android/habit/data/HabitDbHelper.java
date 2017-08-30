package com.example.android.habit.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HabitDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERISON = 1;
    public static final String DATABASE_NAME = "habits";

    //构造函数
    public HabitDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERISON);
    }
    public void onCreate(SQLiteDatabase db){
        String SQL_CREATE_HABIT_TABLE = "CREATE TABLE "+ HabitContract.HabitEntry.TABLE_NAME+" ("+
                HabitContract.HabitEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                HabitContract.HabitEntry.EVENT+" TEXT NOT NULL, "+
                HabitContract.HabitEntry.EXP_TIME_MINUTES+" INTEGER NOT NULL DEFAULT 5);";
        db.execSQL(SQL_CREATE_HABIT_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
