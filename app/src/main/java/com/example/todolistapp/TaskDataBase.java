package com.example.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TaskDataBase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME ="ToDoList.db";
    private static  final int DATABASE_VERSION = 2;
    private static  final String TABLE_NAME = "task_table";
    private static  final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "task_title";
    private static final String COLUMN_DESCRIPTION = "task_description";
    private static final String COLUMN_TIME ="due_time";


    public TaskDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE "+TABLE_NAME+
                        " ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        COLUMN_TITLE+" TEXT, "+
                        COLUMN_DESCRIPTION+" TEXT, "+
                        COLUMN_TIME+" TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addTask(String task_title,String task_data,String due_time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,task_title);
        cv.put(COLUMN_DESCRIPTION,task_data);
        cv.put(COLUMN_TIME,due_time);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context,"Couldn't add task",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Added Task",Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor allTask(){
        String query = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return  cursor;
    }

    public void updateTask(String row_id,String task_title,String task_desc,String due_time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,task_title);
        cv.put(COLUMN_DESCRIPTION,task_desc);
        cv.put(COLUMN_TIME,due_time);
        long result = db.update(TABLE_NAME,cv,"id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Couldn't update task",Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(context,"Task Updated",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTask(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Failed to delete task",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Task Deleted",Toast.LENGTH_SHORT).show();

        }
    }

    public void deleteAllTask(){
        SQLiteDatabase db  = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }
}
