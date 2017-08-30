package com.example.android.habit;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.habit.data.HabitContract;
import com.example.android.habit.data.HabitDbHelper;

public class EditorActivity extends AppCompatActivity{

    private HabitDbHelper mDbHelper;
    private EditText mEventEditText;
    private EditText mTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_editor);

        mEventEditText = (EditText) findViewById(R.id.event_text_view);
        mTimeEditText = (EditText) findViewById(R.id.time_text_view);

        mDbHelper = new HabitDbHelper(this);
    }
    //渲染菜单栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        return true;
    }

    //插入习惯
    private void insertHabit(){
        String event = mEventEditText.getText().toString().trim();
        int time = Integer.parseInt(mTimeEditText.getText().toString().trim());
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.EVENT,event);
        values.put(HabitContract.HabitEntry.EXP_TIME_MINUTES,time);

        //写入数据库
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME,null,values);
        if (newRowId !=-1){
            Toast.makeText(this,"Habit saved with id : "+String.valueOf(newRowId),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Error with saving habit",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                insertHabit();
                //返回MainActivity
                finish();
                return true;
            case R.id.action_quit:
                //返回MainActivity
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
