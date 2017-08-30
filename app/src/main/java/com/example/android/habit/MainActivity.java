package com.example.android.habit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.habit.data.HabitContract;
import com.example.android.habit.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击加号进入编辑页面
                Intent intent = new Intent(MainActivity.this,EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new HabitDbHelper(this);
        displayDataBaseInfo();
    }

    private void displayDataBaseInfo(){
        HabitDbHelper mDbHelper = new HabitDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        //cursor选择返回整个表格
        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.EVENT,
                HabitContract.HabitEntry.EXP_TIME_MINUTES
        };
        Cursor cursor = db.query(HabitContract.HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
        null);

        TextView displayTextView = (TextView)findViewById(R.id.displayView);

        try{
            //用TextView显示表头
            displayTextView.setText(getString(R.string.table_contains)+ cursor.getCount()+getString(R.string.habits));
            displayTextView.append(HabitContract.HabitEntry.TABLE_NAME+" - "
                    + HabitContract.HabitEntry.EVENT+" - "
                    + HabitContract.HabitEntry.EXP_TIME_MINUTES+getString(R.string.minutes)+"\n");

            //取得表格所有列的index
            int idIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int eventIndex = cursor.getColumnIndex(HabitContract.HabitEntry.EVENT);
            int timeIndex = cursor.getColumnIndex(HabitContract.HabitEntry.EXP_TIME_MINUTES);

            //从上到下循环读取每一行的内容,显示在TextView中
            while (cursor.moveToNext()){
                int currentId = cursor.getInt(idIndex);
                String currentEvent = cursor.getString(eventIndex);
                int currentTime = cursor.getInt(timeIndex);
                displayTextView.append("\n"+
                        currentId+" - "+
                        currentEvent+" - "+
                        currentTime);
            }
        }finally {
            //关闭cursor
            cursor.close();
        }
    }

    private void deleteAllHabits(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(HabitContract.HabitEntry.TABLE_NAME,null,null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_delete_all_habits:
                deleteAllHabits();
                displayDataBaseInfo();
                return true;
            case R.id.action_update_data:
                updateHabit();
                displayDataBaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateHabit() {
        ContentValues newValues = new ContentValues();
        newValues.put(HabitContract.HabitEntry.EVENT,"Sport");
        newValues.put(HabitContract.HabitEntry.EXP_TIME_MINUTES, 100);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.update(HabitContract.HabitEntry.TABLE_NAME, newValues,"_id="+1, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDataBaseInfo();
    }
}
